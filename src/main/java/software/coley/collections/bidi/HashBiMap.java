package software.coley.collections.bidi;

import java.util.HashMap;

/**
 * A {@link BiMap} using {@link java.util.HashMap} underneath.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map value type.
 */
public class HashBiMap<K, V> extends AbstractBiMap<K, V> {
	/**
	 * Empty bi-map.
	 */
	public HashBiMap() {
		super(new HashMap<>());
	}

	/**
	 * Pre-populated bimap.
	 *
	 * @param delegate
	 * 		Delegate map to pass to.
	 */
	public HashBiMap(HashMap<K, V> delegate) {
		super(delegate);
	}

	@Override
	protected <K1, V1> AbstractBiMap<K1, V1> createImpl() {
		return new HashBiMap<>();
	}
}
