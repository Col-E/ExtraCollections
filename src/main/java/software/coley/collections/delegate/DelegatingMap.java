package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Map type that delegates to another implementation.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map value type.
 *
 * @author Matt Coley
 */
public class DelegatingMap<K, V> implements Map<K, V> {
	private final Map<K, V> delegate;

	/**
	 * @param delegate
	 * 		Delegate map to pass to.
	 */
	public DelegatingMap(@Nonnull Map<K, V> delegate) {
		this.delegate = Objects.requireNonNull(delegate, "Cannot delegate to a null map");
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return delegate.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return delegate.get(key);
	}

	@Override
	public V put(K key, V value) {
		return delegate.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return delegate.remove(key);
	}

	@Override
	public void putAll(@Nonnull Map<? extends K, ? extends V> m) {
		delegate.putAll(m);
	}

	@Override
	public void clear() {
		delegate.clear();
	}

	@Override
	@Nonnull
	public Set<K> keySet() {
		return delegate.keySet();
	}

	@Override
	@Nonnull
	public Collection<V> values() {
		return delegate.values();
	}

	@Override
	@Nonnull
	public Set<Entry<K, V>> entrySet() {
		return delegate.entrySet();
	}

	@Override
	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
}