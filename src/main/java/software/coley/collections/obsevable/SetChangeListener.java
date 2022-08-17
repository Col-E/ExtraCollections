package software.coley.collections.obsevable;

/**
 * Listener for receiving notifications of changes to an {@link ObservableSet}.
 *
 * @param <T>
 * 		Set item type.
 *
 * @author Matt Coley
 */
public interface SetChangeListener<T> {
	/**
	 * @param source
	 * 		Modified set.
	 * @param change
	 * 		Description of changes.
	 */
	void onSetChanged(ObservableSet<T> source, SetChange<T> change);
}
