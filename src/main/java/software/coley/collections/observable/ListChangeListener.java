package software.coley.collections.observable;

import javax.annotation.Nonnull;

/**
 * Listener for receiving notifications of changes to an {@link ObservableList}.
 *
 * @param <T>
 * 		List item type.
 *
 * @author Matt Coley
 */
public interface ListChangeListener<T> {
	/**
	 * @param source
	 * 		Modified list.
	 * @param change
	 * 		Description of changes.
	 */
	void onListChanged(@Nonnull ObservableList<T> source, @Nonnull ListChange<T> change);
}
