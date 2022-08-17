package software.coley.collections.obsevable;

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
	void onMapChanged(ObservableMap<K, V> source, MapChange<K, V> change);
}
