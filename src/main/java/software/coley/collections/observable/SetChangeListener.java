package software.coley.collections.observable;

import javax.annotation.Nonnull;

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
	void onSetChanged(@Nonnull ObservableSet<T> source, @Nonnull SetChange<T> change);
}
