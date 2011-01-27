package cg.oam.proxy;

import java.lang.Thread.State;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cg.common.logging.Logger;

/**
 * Track references to arbitrary objects using proxy and weak references.
 */
public class ThreadExecutionMonitor
{

  public static long TASK_TIMEOUT = 3000L;

  private static Logger _log = Logger.getLogger( ThreadExecutionMonitor.class );

  // Queue receiving reachability events
  private static final ReferenceQueue handleQueue = new ReferenceQueue();

  // All tracked entries (WeakReference => ReleaseListener)
  private static final Map< Reference, ReleaseListener > trackedEntries = Collections.synchronizedMap( new HashMap() );

  // Thread polling handleQueue, lazy initialized
  private static Thread monitoringThread = null;

  /**
   * Start to monitor given handle object for becoming weakly reachable. When the handle isn't used anymore, the given
   * listener will be called.
   * 
   * @param handle
   *          the object that will be monitored
   * @param listener
   *          the listener that will be called upon release of the handle
   */
  public static void monitor( Thread task )
  {

    ReleaseListener listener = new ReleaseListener( task, System.currentTimeMillis() );

    if ( _log.isDebugEnabled() )
    {
      _log.debug( "Monitoring Task [" + task + "]" );
    }

    // Make weak reference to this handle, so we can say when
    // handle is not used any more by polling on handleQueue.
    WeakReference weakRef = new WeakReference( task, handleQueue );

    // Add monitored entry to internal map of all monitored entries.
    addEntry( weakRef, listener );
  }

  /**
   * Add entry to internal map of tracked entries. Internal monitoring thread is started if not already running.
   * 
   * @param ref
   *          reference to tracked handle
   * @param entry
   *          the associated entry
   */
  private static void addEntry( Reference ref, ReleaseListener entry )
  {
    // Add entry, the key is given reference.
    trackedEntries.put( ref, entry );

    // Start monitoring thread lazily.
    synchronized ( ThreadExecutionMonitor.class )
    {
      if ( !isMonitoringThreadRunning() )
      {
        monitoringThread = new Thread( new MonitoringProcess(), ThreadExecutionMonitor.class.getName() );
        monitoringThread.setDaemon( true );
        monitoringThread.setPriority( Thread.currentThread().getPriority() + 1 );
        monitoringThread.start();
      }
    }
  }

  /**
   * Remove entry from internal map of tracked entries.
   * 
   * @param reference
   *          the reference that should be removed
   * @return entry object associated with given reference
   */
  private static ReleaseListener removeEntry( Reference reference )
  {
    return (ReleaseListener) trackedEntries.remove( reference );
  }

  /**
   * Check if monitoring thread is currently running.
   */
  private static boolean isMonitoringThreadRunning()
  {
    synchronized ( ThreadExecutionMonitor.class )
    {
      return ( monitoringThread != null );
    }
  }

  /**
   * Thread implementation that performs the actual monitoring.
   */
  private static class MonitoringProcess implements Runnable
  {

    public void run()
    {
      _log.debug( "Starting reference monitor thread" );
      try
      {
        // Check if there are any tracked entries left.
        while ( !trackedEntries.isEmpty() )
        {
          try
          {
            Set keys = trackedEntries.keySet();
            List list = new ArrayList( keys );
            Iterator it = list.iterator();
            _log.debug( "Have to check tasks: " + list.size() );
            while ( it.hasNext() )
            {
              Reference ref = (Reference) it.next();
              ReleaseListener task = trackedEntries.get( ref );
              boolean canRemove = true;
              if ( task != null )
              {
                if ( task.isAlive() )
                {
                  _log.debug( "Trying to remove Task...." );
                  // say false if still need time to complete task
                  canRemove = task.canRemove();
                }
                else
                {
                  _log.debug( "Task not alive...." );
                }
              }
              else
              {
                _log.debug( "Task is NULL...." );
              }

              if ( canRemove )
              {
                removeEntry( ref );
                // ???
                ref.enqueue();
                // Reference reference = handleQueue.remove();

              }
            }
            Thread.sleep( 1000L );
          }
          catch ( Exception ex )
          {
            _log.error( "Reference monitor thread interrupted", ex );
            break;
          }
        }
      }
      finally
      {
        _log.debug( "Stopping reference monitor thread" );
        synchronized ( ThreadExecutionMonitor.class )
        {
          monitoringThread = null;
        }
      }
    }
  }

  /**
   * Listener that is notified when the handle is being released. To be implemented by users of this reference monitor.
   */
  public static class ReleaseListener
  {

    private Thread _task;
    private long _startTime;

    public ReleaseListener( Thread task, long startTime )
    {
      _task = task;
      _startTime = startTime;
    }

    void release()
    {

    }

    boolean isAlive()
    {
      _log.debug( "Thread state is '" + _task.getState() + "'" );
      return ( _task != null && !_task.getState().equals( State.WAITING ) );
    }

    boolean canRemove()
    {
      long end = System.currentTimeMillis();
      if ( ( end - _startTime ) > TASK_TIMEOUT )
      {
        try
        {
          _log.debug( "Trying to CANCEL Task...." );
          _task.interrupt();

        }
        catch ( Exception e )
        {

        }
        catch ( Throwable e )
        {
        }
        return true;
      }
      else
      {
        _log.debug( "Too early to remove: " + ( end - _startTime ) );
      }
      return false;
    }

  }

}
