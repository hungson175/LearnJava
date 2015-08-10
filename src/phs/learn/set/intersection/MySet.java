package phs.learn.set.intersection;

import java.util.HashSet;

public class MySet<K> implements ICountingSet {
	HashSet<K> set = new HashSet<K>(128);
	public MySet(K[] a) {
		for(K v : a) set.add(v);
	}
	public MySet(HashSet<K> set) {
		super();
		this.set = set;
	}
	
	public MySet() {
		this.set = new HashSet<K>();
	}

	@Override
	public int cardinality() {
		return set.size();
	}

	
	@Override
	public ICountingSet merge(ICountingSet other) {
		if ( !(other instanceof MySet)) {
			return null;
		}
		MySet<K> b = (MySet<K>) other; 
		MySet<K> union = new MySet<K>();
		union.set.addAll(this.set);
		union.set.addAll(b.set);
		return union;
	}


	@Override
	public ICountingSet merge(ICountingSet[] others) {
		for(ICountingSet s : others) 
			if ( ! (s instanceof MySet)) return null;
		MySet<K> union = new MySet<K>();
		union.set.addAll(this.set);
		for(ICountingSet s : others) {
			union.set.addAll(((MySet<K>)s).set);
		}
		return union ;
	}

}
