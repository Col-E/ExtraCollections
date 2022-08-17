package software.coley.collections.observable;

import software.coley.collections.Sets;

import java.util.Collections;
import java.util.Set;

/**
 * Describes a change in a {@link ObservableSet}.
 *
 * @param <T>
 * 		Set item type.
 *
 * @author Matt Coley
 */
public class SetChange<T> extends Change<T, Set<T>> {
	/**
	 * @param added
	 * 		Set of added items.
	 * @param removed
	 * 		Set of removed items.
	 */
	public SetChange(Set<T> added, Set<T> removed) {
		super(added, removed);
	}

	/**
	 * @param added
	 * 		Item added.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Change of the single addition.
	 */
	public static <T> SetChange<T> addition(T added) {
		return addition(Sets.of(added));
	}

	/**
	 * @param added
	 * 		Items added.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Change of the multiple additions.
	 */
	public static <T> SetChange<T> addition(Set<T> added) {
		return new SetChange<>(added, Collections.emptySet());
	}

	/**
	 * @param removed
	 * 		Item removed.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Change of the single removal.
	 */
	public static <T> SetChange<T> removal(T removed) {
		return addition(Sets.of(removed));
	}

	/**
	 * @param removed
	 * 		Items removed.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Change of the multiple removals.
	 */
	public static <T> SetChange<T> removal(Set<T> removed) {
		return new SetChange<>(Collections.emptySet(), removed);
	}
}
