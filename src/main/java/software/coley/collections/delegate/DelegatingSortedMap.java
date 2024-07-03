package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.SortedMap;

/**
 * Sorted Map type that delegates to another implementation.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map value type.
 *
 * @author Matt Coley
 */
public class DelegatingSortedMap<K, V> extends DelegatingMap<K, V> implements SortedMap<K, V> {
	private final SortedMap<K, V> delegate;

	/**
	 * @param delegate
	 * 		Delegate map to pass to.
	 */
	public DelegatingSortedMap(@Nonnull SortedMap<K, V> delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	@Override
	@Nullable
	public Comparator<? super K> comparator() {
		return delegate.comparator();
	}

	@Override
	@Nonnull
	public SortedMap<K, V> subMap(K fromKey, K toKey) {
		return delegate.subMap(fromKey, toKey);
	}

	@Override
	@Nonnull
	public SortedMap<K, V> headMap(K toKey) {
		return delegate.headMap(toKey);
	}

	@Override
	@Nonnull
	public SortedMap<K, V> tailMap(K fromKey) {
		return delegate.tailMap(fromKey);
	}

	@Override
	@Nullable
	public K firstKey() {
		return delegate.firstKey();
	}

	@Override
	@Nullable
	public K lastKey() {
		return delegate.lastKey();
	}
}
