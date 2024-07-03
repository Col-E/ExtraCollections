package software.coley.collections.observable;

import javax.annotation.Nonnull;

/**
 * Listener for receiving notifications of changes to an {@link ObservableList}.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map value type.
 *
 * @author Matt Coley
 */
public interface MapChangeListener<K, V> {
	/**
	 * @param source
	 * 		Modified map.
	 * @param change
	 * 		Description of changes.
	 */
	void onMapChanged(@Nonnull ObservableMap<K, V> source, @Nonnull MapChange<K, V> change);
}
