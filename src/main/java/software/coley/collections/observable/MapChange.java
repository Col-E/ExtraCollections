package software.coley.collections.observable;

import software.coley.collections.Sets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
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
	public MapChange(@Nonnull Set<Entry<K, V>> addedEntries, @Nonnull Set<Entry<K, V>> removedEntries) {
		super(extractValues(addedEntries), extractValues(removedEntries));
		this.addedEntries = addedEntries;
		this.removedEntries = removedEntries;
	}

	@Nonnull
	private static <K, V> Set<V> extractValues(@Nonnull Set<Entry<K, V>> entries) {
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
	@Nonnull
	public static <K, V> MapChange<K, V> addition(@Nonnull Map<K, V> added) {
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
	@Nonnull
	public static <K, V> MapChange<K, V> removal(@Nonnull Map<K, V> removed) {
		Set<Entry<K, V>> mappedRemoved = removed.entrySet().stream()
				.map(Entry::new)
				.collect(Collectors.toSet());
		return new MapChange<>(Collections.emptySet(), mappedRemoved);
	}

	/**
	 * @param added
	 * 		Added items.
	 * @param removed
	 * 		Removed items.
	 * @param <K>
	 * 		Map key type.
	 * @param <V>
	 * 		Map value type.
	 *
	 * @return Change of the added and removed items.
	 */
	@Nonnull
	public static <K, V> MapChange<K, V> of(@Nullable Map<K, V> added, @Nullable Map<K, V> removed) {
		Set<Entry<K, V>> mappedAdded = added == null ?
				Collections.emptySet() :
				added.entrySet().stream()
						.map(Entry::new)
						.collect(Collectors.toSet());
		Set<Entry<K, V>> mappedRemoved = removed == null ?
				Collections.emptySet() :
				removed.entrySet().stream()
						.map(Entry::new)
						.collect(Collectors.toSet());
		return new MapChange<>(mappedAdded, mappedRemoved);
	}

	/**
	 * @return Added entries.
	 */
	@Nonnull
	public Set<Entry<K, V>> getAddedEntries() {
		return addedEntries;
	}

	/**
	 * @return Removed entries.
	 */
	@Nonnull
	public Set<Entry<K, V>> getRemovedEntries() {
		return removedEntries;
	}

	/**
	 * @return Iterator for all entries <i>(added and removed)</i>.
	 */
	@Nonnull
	public Iterator<Entry<K, V>> entryIterator() {
		return Sets.iterator(addedEntries, removedEntries);
	}

	/**
	 * @param entry
	 * 		Entry to check.
	 *
	 * @return {@code true} when the entry holds an added key in this change.
	 */
	public boolean wasAdded(@Nonnull Entry<K, ?> entry) {
		return wasAdded(entry.key);
	}

	/**
	 * @param key
	 * 		Key to check.
	 *
	 * @return {@code true} when the key was added in this change.
	 */
	public boolean wasAdded(@Nullable K key) {
		return addedEntries.stream().anyMatch(e -> Objects.equals(key, e.getKey())) &&
				removedEntries.stream().noneMatch(e -> Objects.equals(key, e.getKey()));
	}

	/**
	 * @param entry
	 * 		Entry to check.
	 *
	 * @return {@code true} when the entry holds a removed key in this change.
	 */
	public boolean wasRemoved(@Nonnull Entry<K, ?> entry) {
		return wasRemoved(entry.key);
	}

	/**
	 * @param key
	 * 		Key to check.
	 *
	 * @return {@code true} when the key was removed in this change.
	 */
	public boolean wasRemoved(@Nullable K key) {
		return removedEntries.stream().anyMatch(e -> Objects.equals(key, e.getKey())) &&
				addedEntries.stream().noneMatch(e -> Objects.equals(key, e.getKey()));
	}

	/**
	 * @param entry
	 * 		Entry to check.
	 *
	 * @return {@code true} when the entry holds a replaced key in this change.
	 */
	public boolean wasReplaced(@Nonnull Entry<K, ?> entry) {
		return wasReplaced(entry.key);
	}

	/**
	 * @param key
	 * 		Key to check.
	 *
	 * @return {@code true} when the key was replaced in this change.
	 */
	public boolean wasReplaced(@Nullable K key) {
		return removedEntries.stream().anyMatch(e -> Objects.equals(key, e.getKey())) &&
				addedEntries.stream().anyMatch(e -> Objects.equals(key, e.getKey()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MapChange)) return false;

		MapChange<?, ?> mapChange = (MapChange<?, ?>) o;

		if (!addedEntries.equals(mapChange.addedEntries)) return false;
		return removedEntries.equals(mapChange.removedEntries);
	}

	@Override
	public int hashCode() {
		int result = addedEntries.hashCode();
		result = 31 * result + removedEntries.hashCode();
		return result;
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
		public Entry(@Nonnull Map.Entry<K, V> entry) {
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

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Entry)) return false;

			Entry<?, ?> entry = (Entry<?, ?>) o;

			if (!Objects.equals(key, entry.key)) return false;
			return Objects.equals(value, entry.value);
		}

		@Override
		public int hashCode() {
			int result = key != null ? key.hashCode() : 0;
			result = 31 * result + (value != null ? value.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}
}
