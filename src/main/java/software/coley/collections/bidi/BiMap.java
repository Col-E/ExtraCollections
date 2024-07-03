package software.coley.collections.bidi;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;

/**
 * Outline of an invertible map.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map value type.
 *
 * @author Matt Coley
 */
public interface BiMap<K, V> extends Map<K, V> {
	/**
	 * {@inheritDoc}
	 *
	 * @return Set of unique values stored in the map.
	 */
	@Override
	@Nonnull
	Set<V> values();

	/**
	 * @return Inversion of the map.
	 */
	@Nonnull
	BiMap<V, K> reversed();
}
