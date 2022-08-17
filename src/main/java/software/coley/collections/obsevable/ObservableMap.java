package software.coley.collections.obsevable;

import software.coley.collections.Maps;
import software.coley.collections.delegate.DelegatingMap;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Map type that allows listening to modifications.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map value type.
 *
 * @author Matt Coley
 * @see MapChangeListener
 */
public class ObservableMap<K, V> extends DelegatingMap<K, V> {
	private final List<MapChangeListener<K, V>> listeners = new ArrayList<>();

	/**
	 * New observable map with no pre-existing items.
	 */
	public ObservableMap() {
		// We use an hash-map backing implementation since we want delegate to be modifiable (as opposed
		// to using something like Collections.emptyMap()).
		this(HashMap::new);
	}

	/**
	 * New observable map with some pre-existing items.
	 *
	 * @param items
	 * 		Existing items to create as an initial state.
	 */
	public ObservableMap(Map<K, V> items) {
		// We wrap the incoming map just to ensure our delegate can modify the map without unintended effects
		// if the user passes a map intended to be immutable.
		this(() -> new HashMap<>(items));
	}

	/**
	 * New observable map with a backing map provided by a factory.
	 *
	 * @param factory
	 * 		Factory to provide a backing map implementation.
	 * 		Intended to be a single call to a map implementation constructor with zero ties to outside state.
	 */
	public ObservableMap(Supplier<Map<K, V>> factory) {
		super(factory.get());
	}

	/**
	 * Subscribe a listener to events about map modifications.
	 *
	 * @param listener
	 * 		Listener to add.
	 */
	public void addChangeListener(MapChangeListener<K, V> listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	/**
	 * Called AFTER an operation completes.
	 *
	 * @param change
	 * 		Change to post to subscribed listeners.
	 */
	private void post(MapChange<K, V> change) {
		listeners.forEach(listener -> listener.onMapChanged(this, change));
	}

	/**
	 * Unsubscribe a listener from events about map modifications.
	 *
	 * @param listener
	 * 		Listener to remove.
	 *
	 * @return {@code true} on removal success.
	 */
	public boolean removeChangeListener(MapChangeListener<K, V> listener) {
		return listeners.remove(listener);
	}

	@Override
	public V put(K key, V value) {
		V result = super.put(key, value);
		if (result != null) {
			post(MapChange.removal(Maps.of(key, result)));
		}
		post(MapChange.addition(Maps.of(key, value)));
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void putAll(@Nonnull Map<? extends K, ? extends V> m) {
		Map<K, V> replaced = new HashMap<>();
		entrySet().stream()
				.filter(e -> m.containsKey(e.getKey()))
				.forEach(e -> replaced.put(e.getKey(), e.getValue()));
		super.putAll(m);
		if (!replaced.isEmpty()) {
			post(MapChange.addition(replaced));
		}
		post(MapChange.addition((Map<K, V>) m));
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		Map<K, V> copy = new HashMap<>(this);
		copy.replaceAll(function);
		putAll(copy);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(Object key, Object value) {
		boolean result = super.remove(key, value);
		if (result) {
			post(MapChange.removal(Maps.of((K) key, (V) value)));
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public V remove(Object key) {
		V result = super.remove(key);
		if (result != null) {
			post(MapChange.removal(Maps.of((K) key, result)));
		}
		return result;
	}

	@Override
	public void clear() {
		Map<K, V> copy = new HashMap<>(this);
		super.clear();
		post(MapChange.removal(copy));
	}
}
