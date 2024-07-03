package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import java.util.NavigableMap;
import java.util.NavigableSet;

/**
 * Navigable Map type that delegates to another implementation.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map value type.
 *
 * @author Matt Coley
 */
public class DelegatingNavigableMap<K, V> extends DelegatingSortedMap<K, V> implements NavigableMap<K, V> {
	private final NavigableMap<K, V> delegate;

	/**
	 * @param delegate
	 * 		Delegate map to pass to.
	 */
	public DelegatingNavigableMap(@Nonnull NavigableMap<K, V> delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	@Override
	public Entry<K, V> lowerEntry(K key) {
		return delegate.lowerEntry(key);
	}

	@Override
	public K lowerKey(K key) {
		return delegate.lowerKey(key);
	}

	@Override
	public Entry<K, V> floorEntry(K key) {
		return delegate.floorEntry(key);
	}

	@Override
	public K floorKey(K key) {
		return delegate.floorKey(key);
	}

	@Override
	public Entry<K, V> ceilingEntry(K key) {
		return delegate.ceilingEntry(key);
	}

	@Override
	public K ceilingKey(K key) {
		return delegate.ceilingKey(key);
	}

	@Override
	public Entry<K, V> higherEntry(K key) {
		return delegate.higherEntry(key);
	}

	@Override
	public K higherKey(K key) {
		return delegate.higherKey(key);
	}

	@Override
	public Entry<K, V> firstEntry() {
		return delegate.firstEntry();
	}

	@Override
	public Entry<K, V> lastEntry() {
		return delegate.lastEntry();
	}

	@Override
	public Entry<K, V> pollFirstEntry() {
		return delegate.pollFirstEntry();
	}

	@Override
	public Entry<K, V> pollLastEntry() {
		return delegate.pollLastEntry();
	}

	@Override
	public NavigableMap<K, V> descendingMap() {
		return delegate.descendingMap();
	}

	@Override
	public NavigableSet<K> navigableKeySet() {
		return delegate.navigableKeySet();
	}

	@Override
	public NavigableSet<K> descendingKeySet() {
		return delegate.descendingKeySet();
	}

	@Override
	public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
		return delegate.subMap(fromKey, fromInclusive, toKey, toInclusive);
	}

	@Override
	public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
		return delegate.headMap(toKey, inclusive);
	}

	@Override
	public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
		return delegate.tailMap(fromKey, inclusive);
	}
}
