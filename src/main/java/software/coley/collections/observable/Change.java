package software.coley.collections.observable;

import java.util.Collection;

/**
 * Describes a change in an observable collection type.
 *
 * @param <T>
 * 		Collection item type.
 * @param <C>
 * 		Collection implementation type.
 *
 * @author Matt Coley
 * @see ListChange
 * @see MapChange
 * @see SetChange
 */
public abstract class Change<T, C extends Collection<T>> {
	private final C added;
	private final C removed;

	/**
	 * @param added
	 * 		Collection of added items.
	 * @param removed
	 * 		Collection of removed items.
	 */
	public Change(C added, C removed) {
		this.added = added;
		this.removed = removed;
	}

	/**
	 * @return {@code true} when there were added items in the change.
	 */
	public boolean wasAddition() {
		return !added.isEmpty();
	}

	/**
	 * @return {@code true} when there were removed items in the change.
	 */
	public boolean wasRemoval() {
		return !removed.isEmpty();
	}

	/**
	 * @return Collection of added items.
	 */
	public C getAdded() {
		return added;
	}

	/**
	 * @return Collection of removed items.
	 */
	public C getRemoved() {
		return removed;
	}
}
