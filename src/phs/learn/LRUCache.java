package phs.learn;

import java.util.LinkedHashMap;

public class LRUCache<K,V> {
	private int cacheSize = 16;
	 private final static int initialCapacity = 16;
	 private final static float loadFactor = 0.75F;
	 private LinkedHashMap<K,V> cache = new LinkedHashMap<K,V>(initialCapacity, loadFactor, true ) {
		private static final long serialVersionUID = 3457854944113268845L;
		protected boolean removeEldestEntry(java.util.Map.Entry<K,V> eldest) {
			return this.size() > LRUCache.this.cacheSize;
		};
	 };
	 
	 public LRUCache( int size) {		 
		this.cacheSize = size;
	 }
	
	public synchronized boolean put(K key, V value) {
		if ( value == null ) return false;
		cache.put(key, value);
		return true;
	}
	
	public synchronized V get(K key) {
		return cache.get(key);
	}
	
	public synchronized void clear() {
		cache.clear();
	}
	
}