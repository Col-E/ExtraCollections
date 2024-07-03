package software.coley.collections.bidi;

import software.coley.collections.delegate.DelegatingMap;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Base implementation of {@link BiMap}.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map value type.
 */
public abstract class AbstractBiMap<K, V> extends DelegatingMap<K, V> implements BiMap<K, V> {
	/**
	 * @param delegate
	 * 		Delegate map to pass to.
	 */
	public AbstractBiMap(@Nonnull Map<K, V> delegate) {
		super(delegate);
		validate(delegate);
	}

	/**
	 * @param map
	 * 		Map to validate.
	 *
	 * @throws IllegalArgumentException
	 * 		When the map has duplicate values.
	 */
	protected void validate(@Nonnull Map<K, V> map) {
		Set<V> values = new HashSet<>();
		for (V value : map.values())
			if (values.contains(value))
				throw new IllegalArgumentException("Delegate map has duplicate value: " + value);
			else
				values.add(value);
	}

	@Override
	public V put(K key, V value) {
		if (values().contains(value))
			throw new IllegalArgumentException("BiMap already has value: " + value);
		return super.put(key, value);
	}

	@Override
	public void putAll(@Nonnull Map<? extends K, ? extends V> m) {
		Set<V> values = values();
		for (V value : m.values())
			if (values.contains(value))
				throw new IllegalArgumentException("BiMap already has value: " + value);
		super.putAll(m);
	}

	@Override
	@Nonnull
	public Set<V> values() {
		return new HashSet<>(super.values());
	}

	@Nonnull
	@Override
	public BiMap<V, K> reversed() {
		BiMap<V, K> map = createImpl();
		forEach((key, value) -> map.put(value, key));
		return map;
	}

	@Nonnull
	protected abstract <K1, V1> AbstractBiMap<K1, V1> createImpl();
}
