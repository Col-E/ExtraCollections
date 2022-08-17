package software.coley.collections.observable;

import software.coley.collections.delegate.DelegatingSet;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

/**
 * Set type that allows listening to modifications.
 *
 * @param <T>
 * 		Set item type.
 *
 * @author Matt Coley
 * @see SetChangeListener
 */
public class ObservableSet<T> extends DelegatingSet<T> {
	private final List<SetChangeListener<T>> listeners = new ArrayList<>();

	/**
	 * New observable set with no pre-existing items.
	 */
	public ObservableSet() {
		// We use an hash-set backing implementation since we want delegate to be modifiable (as opposed
		// to using something like Collections.emptySet()).
		this(HashSet::new);
	}

	/**
	 * New observable set with some pre-existing items.
	 *
	 * @param items
	 * 		Existing items to create as an initial state.
	 */
	public ObservableSet(Collection<T> items) {
		// We wrap the incoming set just to ensure our delegate can modify the set without unintended effects
		// if the user passes a set intended to be immutable.
		this(() -> new HashSet<>(items));
	}

	/**
	 * New observable set with a backing set provided by a factory.
	 *
	 * @param factory
	 * 		Factory to provide a backing set implementation.
	 * 		Intended to be a single call to a set implementation constructor with zero ties to outside state.
	 */
	public ObservableSet(Supplier<Set<T>> factory) {
		super(factory.get());
	}

	/**
	 * Subscribe a listener to events about set modifications.
	 *
	 * @param listener
	 * 		Listener to add.
	 */
	public void addChangeListener(SetChangeListener<T> listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	/**
	 * Unsubscribe a listener from events about set modifications.
	 *
	 * @param listener
	 * 		Listener to remove.
	 *
	 * @return {@code true} on removal success.
	 */
	public boolean removeChangeListener(SetChangeListener<T> listener) {
		return listeners.remove(listener);
	}

	/**
	 * Called AFTER an operation completes.
	 *
	 * @param change
	 * 		Change to post to subscribed listeners.
	 */
	private void post(SetChange<T> change) {
		listeners.forEach(listener -> listener.onSetChanged(this, change));
	}

	@Override
	public boolean add(T t) {
		super.add(t);
		post(SetChange.addition(t));
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(Object o) {
		boolean result = super.remove(o);
		if (result) {
			post(SetChange.removal((T) o));
		}
		return result;
	}

	@Override
	public boolean addAll(@Nonnull Collection<? extends T> c) {
		boolean result = super.addAll(c);
		post(SetChange.addition(new HashSet<>(c)));
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean removeAll(@Nonnull Collection<?> c) {
		boolean result = super.removeAll(c);
		post(SetChange.removal((Set<T>) new HashSet<>(c)));
		return result;
	}

	@Override
	public void clear() {
		Set<T> copy = new HashSet<>(this);
		super.clear();
		post(SetChange.removal(copy));
	}
}
