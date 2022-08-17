package software.coley.collections.obsevable;

import software.coley.collections.delegate.DelegatingList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * List type that allows listening to modifications.
 *
 * @param <T>
 * 		List item type.
 *
 * @author Matt Coley
 * @see ListChangeListener
 */
public class ObservableList<T> extends DelegatingList<T> {
	private final List<ListChangeListener<T>> listeners = new ArrayList<>();

	/**
	 * New observable list with no pre-existing items.
	 */
	public ObservableList() {
		// We use an array-list backing implementation since we want delegate to be modifiable (as opposed
		// to using something like Collections.emptyList()).
		this(ArrayList::new);
	}

	/**
	 * New observable list with some pre-existing items.
	 *
	 * @param items
	 * 		Existing items to create as an initial state.
	 */
	public ObservableList(Collection<T> items) {
		// We wrap the incoming list just to ensure our delegate can modify the list without unintended effects
		// if the user passes a list intended to be immutable.
		this(() -> new ArrayList<>(items));
	}

	/**
	 * New observable list with a backing list provided by a factory.
	 *
	 * @param factory
	 * 		Factory to provide a backing list implementation.
	 * 		Intended to be a single call to a list implementation constructor with zero ties to outside state.
	 */
	public ObservableList(Supplier<List<T>> factory) {
		super(factory.get());
	}

	/**
	 * Subscribe a listener to events about list modifications.
	 *
	 * @param listener
	 * 		Listener to add.
	 */
	public void addChangeListener(ListChangeListener<T> listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	/**
	 * Unsubscribe a listener from events about list modifications.
	 *
	 * @param listener
	 * 		Listener to remove.
	 *
	 * @return {@code true} on removal success.
	 */
	public boolean removeChangeListener(ListChangeListener<T> listener) {
		return listeners.remove(listener);
	}

	/**
	 * Called AFTER an operation completes.
	 *
	 * @param change
	 * 		Change to post to subscribed listeners.
	 */
	private void post(ListChange<T> change) {
		listeners.forEach(listener -> listener.onListChanged(this, change));
	}

	@Override
	public boolean add(T t) {
		int from = size();
		super.add(t);
		post(ListChange.addition(t, from));
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(Object o) {
		int index = indexOf(o);
		if (index >= 0) {
			boolean result = super.remove(o);
			if (result) {
				post(ListChange.removal((T) o, index));
			}
		}
		return false;
	}

	@Override
	public boolean addAll(@Nonnull Collection<? extends T> c) {
		int from = size();
		boolean result = super.addAll(c);
		post(ListChange.addition(new ArrayList<>(c), from));
		return result;
	}

	@Override
	public boolean addAll(int index, @Nonnull Collection<? extends T> c) {
		boolean result = super.addAll(index, c);
		post(ListChange.addition(new ArrayList<>(c), index));
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean removeAll(@Nonnull Collection<?> c) {
		boolean result = false;
		// Track items to remove, we will be removing them in sections.
		// This allows our change listener to handle disjoint indices of removed items.
		List<T> remaining = new ArrayList<>((Collection<? extends T>) c);
		List<T> section = new ArrayList<>();
		int sectionStart = -1;
		while (!remaining.isEmpty()) {
			T next = remaining.remove(0);
			int index = indexOf(next);
			if (index >= 0) {
				// Item is in the list.
				if (section.isEmpty() || index == sectionStart + section.size()) {
					// We can add it to the pending current list if its adjacent to the last item in the list.
					// Or if the list is empty then we always need to start adding to the new list.
					section.add(next);
				} else {
					// Index does not match, so this item is not next to the prior removed item.
					// We will delete the current list, then start a new list.
					boolean sectionResult = super.removeAll(section);
					if (sectionResult) {
						result = true;
						post(ListChange.removal(section, sectionStart));
					}
					// Start recording a new list.
					section.clear();
					section.add(next);
					sectionStart = index;
				}
			}
		}
		// Cleanup from remaining section
		if (!section.isEmpty()) {
			boolean sectionResult = super.removeAll(section);
			if (sectionResult) {
				result = true;
				post(ListChange.removal(section, sectionStart));
			}
		}
		return result;
	}

	@Override
	public void clear() {
		List<T> copy = new ArrayList<>(this);
		super.clear();
		post(ListChange.removal(copy, 0));
	}

	@Override
	public T set(int index, T element) {
		T result = super.set(index, element);
		post(ListChange.removal(element, index));
		post(ListChange.addition(result, index));
		return result;
	}

	@Override
	public void add(int index, T element) {
		super.add(index, element);
		post(ListChange.addition(element, index));
	}

	@Override
	public T remove(int index) {
		T result = super.remove(index);
		if (result != null) {
			post(ListChange.removal(result, index));
		}
		return result;
	}
}
