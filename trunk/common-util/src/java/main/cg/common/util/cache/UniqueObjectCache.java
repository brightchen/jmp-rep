package cg.common.util.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import cg.common.logging.Logger;

public abstract class UniqueObjectCache< KeyType, ObjectType, CreateExceptionType extends Exception >
{

  protected static Logger _log = Logger.getLogger( UniqueObjectCache.class );

  protected static final int DEFAULT_SIZE = 4;

  protected static final float _loadFactor = 0.75f;

  int _capacity;

  private Map< KeyType, ProxyCachedObject > _cacheMap;

  protected abstract ObjectType createObject( KeyType key ) throws CreateExceptionType;

  public UniqueObjectCache( int maxObjects )
  {
    _capacity = maxObjects;
    _cacheMap = createCacheMap( _capacity, _loadFactor );
  }

  public UniqueObjectCache()
  {
    this( DEFAULT_SIZE );
  }

  public ObjectType getObject( KeyType key ) throws CreateExceptionType
  {
    if ( _log.isDebugEnabled() )
      _log.debug( "Requesting object for key " + key );
    ProxyCachedObject proxy = getProxyObject( key );
    return proxy.getObject();
  }

  public synchronized ObjectType removeObject( KeyType key )
  {
    if ( _log.isDebugEnabled() )
      _log.debug( "Removing object for key " + key.toString() );
    ProxyCachedObject proxy = _cacheMap.remove( key );
    ObjectType cachedObject = null;
    if ( proxy != null )
    {
      cachedObject = proxy.object;
    }
    else if ( _log.isDebugEnabled() )
      _log.debug( "Object not found for key " + key.toString() );

    return cachedObject;
  }

  public synchronized int getCapacity()
  {
    return _capacity;
  }

  public synchronized void setCapacity( int capacity )
  {
    if ( capacity < _capacity )
    {
      // shrinking - remove the least recently accessed objects
      // until we have reduced the entries to the desired number
      if ( _log.isDebugEnabled() )
        _log.debug( "Reducing size of cache from " + _capacity + " to " + capacity + "." );
      removeEntries( _capacity - capacity );
      // TODO: we could allocate a new hash table with the new size and copy the remaining
      // elements to it.
    }
    else if ( capacity > _capacity )
    {
      // nothing to do because the underlying hash table will grow accordingly
      if ( _log.isDebugEnabled() )
        _log.debug( "Increasing size of cache from " + _capacity + " to " + capacity + "." );
    }
    // set the new capacity
    _capacity = capacity;

  }

  public synchronized int getCacheSize()
  {
    return _cacheMap.size();
  }

  public synchronized void clear()
  {
    if ( _log.isDebugEnabled() )
      _log.debug( "Clearing cache." );
    removeEntries( -1 );
  }

  protected void removeEntries( int count )
  {
    Set< Entry< KeyType, ProxyCachedObject >> entries = _cacheMap.entrySet();
    int i = 0;
    for ( Entry< KeyType, ProxyCachedObject > entry : entries )
    {
      entries.remove( entry );
      i++;
      if ( i == count )
        break;
    }
  }

  private Map< KeyType, ProxyCachedObject > createCacheMap( int capacity, float loadFactor )
  {
    if ( _log.isDebugEnabled() )
      _log.debug( "Creating map for cache: capacity=" + capacity + ", loadFActor=" + loadFactor );
    return new CacheMap( (int) Math.ceil( capacity / loadFactor ), loadFactor );
  }

  private synchronized ProxyCachedObject getProxyObject( KeyType key )
  {
    ProxyCachedObject proxy = _cacheMap.get( key );
    if ( proxy == null )
    {
      if ( _log.isDebugEnabled() )
        _log.debug( "Creating proxy object for key " + key.toString() );
      proxy = new ProxyCachedObject( key );
      _cacheMap.put( key, proxy );
    }
    return proxy;
  }

  private class ProxyCachedObject
  {
    private KeyType key;
    private ObjectType object;
    private boolean initialized;;

    public ProxyCachedObject( KeyType key )
    {
      this.key = key;
      this.object = null;
      this.initialized = false;
    }

    protected synchronized ObjectType getObject() throws CreateExceptionType
    {
      if ( !initialized )
      {
        if ( _log.isDebugEnabled() )
          _log.debug( "Creating object for key " + key.toString() );
        object = createObject( key );
        initialized = true;
      }
      return object;
    }
  }

  private class CacheMap extends LinkedHashMap< KeyType, ProxyCachedObject >
  {
    private static final long serialVersionUID = -5553351175748782403L;

    public CacheMap( int initialCapacity, float loadFactor )
    {
      // constructor with parameter to keep the linked list in last accessed order set to true
      super( initialCapacity, loadFactor, true );
    }

    @Override
    protected boolean removeEldestEntry( Entry< KeyType, ProxyCachedObject > eldest )
    {
      return size() > _capacity;
    }

  }
}
