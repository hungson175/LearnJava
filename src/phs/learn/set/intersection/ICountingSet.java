package phs.learn.set.intersection;

public interface ICountingSet {
	public int cardinality();
	ICountingSet merge(ICountingSet other);
	ICountingSet merge(ICountingSet[] other);
}
