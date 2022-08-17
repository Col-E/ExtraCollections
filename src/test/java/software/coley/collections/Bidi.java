package software.coley.collections;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import software.coley.collections.bidi.AnyBiMap;
import software.coley.collections.bidi.BiMap;
import software.coley.collections.bidi.HashBiMap;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link BiMap}
 */
@SuppressWarnings("all")
public class Bidi {
	@Nested
	class Hash {
		@Test
		public void test_HashBiMap_Unique() {
			BiMap<String, String> stringBiMap = new HashBiMap<>();
			assertDoesNotThrow(() -> {
				stringBiMap.put("a", "b");
				stringBiMap.put("b", "c");
				stringBiMap.put("c", "d");
			});
		}

		@Test
		public void test_HashBiMap_DuplicateValues() {
			BiMap<String, String> stringBiMap = new HashBiMap<>();
			assertThrows(IllegalArgumentException.class, () -> {
				stringBiMap.put("a", "a");
				stringBiMap.put("b", "a");
			});
		}

		@Test
		public void test_HashBiMap_DuplicateDelegateValues() {
			HashMap<String, String> delegate = new HashMap<>();
			delegate.put("a", "a");
			delegate.put("b", "a");
			assertThrows(IllegalArgumentException.class, () -> {
				new HashBiMap<>(delegate);
			});
		}
	}

	@Nested
	class Any {
		@Test
		public void test_AnyBiMap_Unique() {
			BiMap<String, String> stringBiMap = new AnyBiMap<>(HashMap::new);
			assertDoesNotThrow(() -> {
				stringBiMap.put("a", "b");
				stringBiMap.put("b", "c");
				stringBiMap.put("c", "d");
			});
			assertDoesNotThrow(() -> {
				new AnyBiMap<>(HashMap::new, stringBiMap);
			});
		}

		@Test
		public void test_AnyBiMap_DuplicateValues() {
			BiMap<String, String> stringBiMap = new AnyBiMap<>(HashMap::new);
			assertThrows(IllegalArgumentException.class, () -> {
				stringBiMap.put("a", "a");
				stringBiMap.put("b", "a");
			});
		}

		@Test
		public void test_AnyBiMap_DuplicateDelegateValues() {
			HashMap<String, String> delegate = new HashMap<>();
			delegate.put("a", "a");
			delegate.put("b", "a");
			assertThrows(IllegalArgumentException.class, () -> {
				new AnyBiMap<>(HashMap::new, delegate);
			});
			assertThrows(IllegalArgumentException.class, () -> {
				new AnyBiMap<>(() -> delegate, delegate);
			});
			assertThrows(IllegalArgumentException.class, () -> {
				// Lazily requests map with dupes
				new AnyBiMap<>(() -> delegate).reversed();
			});
			assertThrows(IllegalArgumentException.class, () -> {
				// Lazily requests map with dupes
				new AnyBiMap<>(() -> delegate, new HashMap<>()).reversed();
			});
		}
	}
}
