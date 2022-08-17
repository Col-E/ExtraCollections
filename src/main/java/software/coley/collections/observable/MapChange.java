package software.coley.collections.observable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Describes a change in a {@link ObservableMap}.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map value type.
 *
 * @author Matt Coley
 */
public class MapChange<K, V> extends SetChange<V> {
	private final Set<Entry<K, V>> addedEntries;
	private final Set<Entry<K, V>> removedEntries;

	/**
	 * @param addedEntries
	 * 		Added map entries.
	 * @param removedEntries
	 * 		Removed map entries.
	 */
	public MapChange(Set<Entry<K, V>> addedEntries, Set<Entry<K, V>> removedEntries) {
		super(extractValues(addedEntries), extractValues(removedEntries));
		this.addedEntries = addedEntries;
		this.removedEntries = removedEntries;
	}

	private static <K, V> Set<V> extractValues(Set<Entry<K, V>> entries) {
		return entries.stream()
				.map(Entry::getValue)
				.collect(Collectors.toSet());
	}

	/**
	 * @param added
	 * 		Added items.
	 * @param <K>
	 * 		Map key type.
	 * @param <V>
	 * 		Map value type.
	 *
	 * @return Change of the added items.
	 */
	public static <K, V> MapChange<K, V> addition(Map<K, V> added) {
		Set<Entry<K, V>> mappedAdded = added.entrySet().stream()
				.map(Entry::new)
				.collect(Collectors.toSet());
		return new MapChange<>(mappedAdded, Collections.emptySet());
	}

	/**
	 * @param removed
	 * 		Removed items.
	 * @param <K>
	 * 		Map key type.
	 * @param <V>
	 * 		Map value type.
	 *
	 * @return Change of the removed items.
	 */
	public static <K, V> MapChange<K, V> removal(Map<K, V> removed) {
		Set<Entry<K, V>> mappedRemoved = removed.entrySet().stream()
				.map(Entry::new)
				.collect(Collectors.toSet());
		return new MapChange<>(Collections.emptySet(), mappedRemoved);
	}

	/**
	 * @return Added entries.
	 */
	public Set<Entry<K, V>> getAddedEntries() {
		return addedEntries;
	}

	/**
	 * @return Removed entries.
	 */
	public Set<Entry<K, V>> getRemovedEntries() {
		return removedEntries;
	}

	/**
	 * Pair type mirroring an {@link Map.Entry}.
	 *
	 * @param <K>
	 * 		Map key type.
	 * @param <V>
	 * 		Map value type.
	 */
	public static class Entry<K, V> {
		private final K key;
		private final V value;

		/**
		 * @param entry
		 * 		Entry to mirror.
		 */
		public Entry(Map.Entry<K, V> entry) {
			this(entry.getKey(), entry.getValue());
		}

		/**
		 * @param key
		 * 		Entry key.
		 * @param value
		 * 		Entry value.
		 */
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * @return Entry key.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * @return Entry value.
		 */
		public V getValue() {
			return value;
		}
	}
}
