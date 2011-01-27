package cg.oam.proxy;

import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import cg.common.logging.Logger;

// import cg.sti.TranscodingJob;
// import cg.sti.TranscodingParams;
// import cg.sti.TranscodingRequest;
// import cg.sti.TranscodingResponse;

// import cg.adaptation.engine.StreamingEngineImpl;

public class AdaptationEngineMethodInterceptor implements MethodInterceptor, AdaptationEngineProxy
{

  private static Logger _log = Logger.getLogger( AdaptationEngineMethodInterceptor.class );
  // private static Logger _logEngine = Logger.getLogger("engine");
  private static Logger _logEngine = Logger.getEngineLogger();

  private long _taskTimeout = 10000L;

  /** The system time at which this object was initialized */
  private final long initedMillis = System.currentTimeMillis();

  /** The number of operations recorded by this object */
  private volatile int _accessCount;

  private volatile int _successCount = 0;

  private volatile int _errorCount = 0;

  private volatile int _activityCount = 0;

  /** The sum of the response times for all operations */
  private volatile int totalResponseTimeMillis = 0;

  /** The best response time this object has recorded */
  private volatile int bestResponseTimeMillis = Integer.MAX_VALUE;

  /** The worst response time this object has recorded */
  private volatile int worstResponseTimeMillis = Integer.MIN_VALUE;

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getLoadDate()
   */
  public Date getLoadDate()
  {
    return new Date( this.initedMillis );
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getAccessCount()
   */
  public int getAccessCount()
  {
    return _accessCount;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getCurrentRequests()
   */
  public int getCurrentRequests()
  {
    return _activityCount;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getUptimeMillis()
   */
  public long getUptimeMillis()
  {
    return System.currentTimeMillis() - this.initedMillis;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getAverageResponseTimeMillis()
   */
  public int getAverageResponseTimeMillis()
  {
    // avoid division by 0
    if ( getAccessCount() == 0 )
    {
      return 0;
    }
    return this.totalResponseTimeMillis / getAccessCount();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getBestResponseTimeMillis()
   */
  public int getBestResponseTimeMillis()
  {
    if ( bestResponseTimeMillis == Integer.MAX_VALUE )
      return 0;
    return bestResponseTimeMillis;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getWorstResponseTimeMillis()
   */
  public int getWorstResponseTimeMillis()
  {
    if ( worstResponseTimeMillis == Integer.MIN_VALUE )
      return 0;
    return worstResponseTimeMillis;
  }

  public synchronized void recordRequest( boolean enter )
  {
    if ( enter )
      _activityCount++;
    else if ( _activityCount > 0 )
      _activityCount--;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getErrorCount()
   */
  public synchronized int getErrorCount()
  {
    return _errorCount;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getSuccessCount()
   */
  public synchronized int getSuccessCount()
  {
    return _successCount;
  }

  /**
   * Utility method to record this response time, updating the best and worst response times if necessary.
   * 
   * @param responseTimeMillis
   *          the response time of this request
   */
  public synchronized void recordResponseTime( long responseTimeMillis, boolean wasError )
  {
    ++this._accessCount;

    if ( wasError )
      _errorCount++;
    else
      _successCount++;

    int iResponseTime = (int) responseTimeMillis;
    this.totalResponseTimeMillis += iResponseTime;
    if ( iResponseTime < this.bestResponseTimeMillis )
    {
      this.bestResponseTimeMillis = iResponseTime;
    }
    if ( iResponseTime > this.worstResponseTimeMillis )
    {
      this.worstResponseTimeMillis = iResponseTime;
    }
  }

  /**
   * Return a human-readable string showing the performance data recorded by this object.
   */
  public synchronized String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append( "hits=[" ).append( getAccessCount() ).append( "]; " );
    sb.append( "error=[" ).append( _errorCount ).append( "]; " );
    sb.append( "ok=[" ).append( _successCount ).append( "]; " );
    sb.append( "average=[" ).append( getAverageResponseTimeMillis() ).append( "ms]; " );
    sb.append( "best=[" ).append( getBestResponseTimeMillis() ).append( "ms]; " );
    sb.append( "worst=[" ).append( getWorstResponseTimeMillis() ).append( "ms]" );
    return sb.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getStatus()
   */
  public String getStatus()
  {
    return toString();
  }

  /**
   * The actual invoke method that intercepts and deals with caching.
   */
  public Object invoke( MethodInvocation invocation ) throws Throwable
  {
    recordRequest( true );
    // _log.info("beforeInvocation...");
    long start = System.currentTimeMillis();
    String error = null;

    // build log string
    // StringBuffer buf = new StringBuffer("Adapatation Request:");
    // buf.append(invocation.getMethod().getName() + "\n <--");
    StringBuffer buf = new StringBuffer( "\n <--" );
    if ( invocation.getArguments() != null && invocation.getArguments().length > 0 )
    {
      // for (Object argument:invocation.getArguments())
      // {
      // if(argument instanceof TranscodingRequest){
      // TranscodingRequest request = (TranscodingRequest)argument;
      // TranscodingParams params = request.getTranscodingParams();
      // TranscodingJob job = request.getTranscodingJobs()[0];
      //
      // buf.append("\n Time = '" + new Date() + "'");
      // buf.append("\n Document = '" + job.getSource().getLocation() + "'");
      // buf.append("\n Request = '" + params.getTypeAsString() + "'");
      // buf.append("\n Query = '" + params.getQueryString() + "'");
      // buf.append("\n User-Agent = '" + params.getUserAgent()+ "'");
      // buf.append("\n Client IP  = '" + params.getClientIP()+ "'");
      // }
      // }
    }

    // _log.debug("Arguments=" + buf.toString());

    Object object = null;
    try
    {
      if ( _taskTimeout < 1000L )
      {
        object = invocation.proceed();
      }
      else
      {
        // start a monitored thread
        object = invokeOtherThread( invocation );
      }

      if ( object == null )
      {
        throw new Exception( "Unable to complete task in time: " + ( System.currentTimeMillis() - start ) );
      }

//      TranscodingResponse response = (TranscodingResponse) object;
//      if ( response.getReturnResult().getReturnCode() != 200 )
//      {
//        error = response.getReturnResult().getReturnString();
//        if ( error == null )
//        {
//          error = "Processing Exception.";
//        }
//      }
//      else
//      {
//        buf.append( "\n Response length= '" + response.getJobResults()[0].getOutput().getData().length + " bytes'" );
//      }
      return object;

    }
    catch ( Throwable e )
    {
      _log.info( "Invocation failure. Throwable: " + e.getMessage() );
      error = e.getMessage();
      if ( error == null )
      {
        error = "Throwable Exception.";
      }
      throw new Exception( e.getMessage(), e );
    }
    finally
    {
      if ( error != null )
      {
        int j = error.indexOf( '\n' );
        if ( j == -1 )
          j = error.indexOf( '\r' );
        if ( j > -1 )
          error = error.substring( 0, j );
      }
      long end = System.currentTimeMillis();
      recordResponseTime( ( end - start ), error != null );
      // _log.info(" Invocation done in " + (end - start) + " millis...");

      if ( error != null )
        buf.append( "\n Error = '" + error + "'" );
      buf.append( "\n Duration = '" + ( end - start ) + " millis'" );
      buf.append( "\n " + getRequestCounts() );
      buf.append( "\n -->" );

      _log.debug( "transcode:" + buf.toString() );

      _logEngine.info( buf.toString() );

      recordRequest( false );
    }
  }

  private Object invokeOtherThread( MethodInvocation invocation ) throws Throwable
  {
    // cancel the task if not executed in GIVEN TIME
    Thread thread = Thread.currentThread();
    long start = System.currentTimeMillis();
    Object object = null;

    // proceed invocation in a monitored thread
    AdaptationTask task = new AdaptationTask( thread, invocation );
    Thread t = new Thread( task );
    t.start();

    while ( true )
    {
      long elapsed = System.currentTimeMillis() - start;
      if ( task.isCompleted() )
      {
        object = task.getResult();
        break;
      }
      else
      {
        if ( elapsed > _taskTimeout )
        {
          _log.debug( "Need to Cancel Thread: " + elapsed );
          try
          {
            // futureTask.cancel(true);
            task.cancel();
            t = null;
            task = null;
          }
          catch ( Exception e )
          {
            _log.debug( "Cancel Exception: " + e.getMessage() );
          }
          catch ( Throwable e )
          {
            _log.debug( "Cancel Throwable: " + e.getMessage() );
          }

          break;
        }
      }
      Thread.sleep( 100L );
    }
    return object;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getTaskTimeout()
   */
  public long getTaskTimeout()
  {
    return _taskTimeout;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#setTaskTimeout(java.lang.String)
   */
  public void setTaskTimeout( long timeout )
  {
    _taskTimeout = timeout;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getTaskTimeout()
   */
  public String getTaskTimeout2()
  {
    return "" + _taskTimeout;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#setTaskTimeout(java.lang.String)
   */
  public void setTaskTimeout2( String timeout )
  {
    try
    {
      _taskTimeout = Long.parseLong( timeout );
    }
    catch ( Exception e )
    {
      _log.debug( "Cannot set new Task Timeout " + timeout + " Reason: " + e.getMessage() );
    }
  }

  public synchronized boolean clearStatistics()
  {

    _accessCount = 0;
    _successCount = 0;
    _errorCount = 0;
    _activityCount = 0;
    totalResponseTimeMillis = 0;
    bestResponseTimeMillis = Integer.MAX_VALUE;
    worstResponseTimeMillis = Integer.MIN_VALUE;

    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getRequestCounts()
   */
  public String getRequestCounts()
  {
    return "[E/S/T] " + _errorCount + "/" + _successCount + "/" + ( _errorCount + _successCount );
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg.oam.proxy.AdaptationEngineProxy#getSummary()
   */
  public synchronized String getSummary()
  {
    StringBuffer sb = new StringBuffer();
    sb.append( "hits=[" ).append( getAccessCount() ).append( "]; " );
    sb.append( "success=[" ).append( _successCount ).append( "]; " );
    sb.append( "error=[" ).append( _errorCount ).append( "]" );
    return sb.toString();
  }

  /**
   * Listener that is notified when the handle is being released. To be implemented by users of this reference monitor.
   */
  public static class AdaptationTask implements Runnable
  {
//    private Thread _owner;
    private volatile Thread _task;
    private String _taskName = "unknown";
    private MethodInvocation _invocation;
    private Object _result = null;
    private volatile boolean _completed = false;

    public AdaptationTask( Thread owner, MethodInvocation invocation )
    {
//      _owner = owner;
      _invocation = invocation;
      _taskName = owner.getName();
    }

    public void run()
    {
      _task = Thread.currentThread();
      long start = System.currentTimeMillis();
      try
      {
        _result = _invocation.proceed();
        _log.debug( "[" + _task.getName() + "]" + " invocation done in "
                    + ( System.currentTimeMillis() - start + " millis." ) );
      }
      catch ( Exception e )
      {
        _log.debug( "[" + _taskName + "]" + " invocation Exception: " + e.getMessage() );
      }
      catch ( Throwable e )
      {
        _log.debug( "[" + _taskName + "]" + " invocation Throwable: " + e.getMessage() );
      }
      _completed = true;
    }

    public boolean isCompleted()
    {
      return _completed;
    }

    public void setCompleted( boolean completed )
    {
      _completed = completed;
    }

    public Object getResult()
    {
      return _result;
    }

    public void cancel()
    {
      if ( _task == null )
        return;
      try
      {
        _task.interrupt();
        _task.stop();
        _log.debug( "[" + _task.getName() + "]" + " have been canceled." );
      }
      catch ( Exception e )
      {
        _log.debug( "[" + _taskName + "]" + " cancel Exception: " + e.getMessage() );
      }
      catch ( Throwable e )
      {
        _log.debug( "[" + _taskName + "]" + " cancel Throwable: " + e.getMessage() );
      }
      _completed = true;
    }

  }

}