package software.coley.collections.observable;

import org.junit.jupiter.api.Test;
import software.coley.collections.Lists;
import software.coley.collections.box.IntBox;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
	private static final Comparator<MapChange.Entry<String, String>> ENTRY_CMP = Comparator.comparing(MapChange.Entry::getKey);

	@Test
	void put() {
		IntBox box = new IntBox();

		ObservableMap<String, String> map = new ObservableMap<>();
		map.addChangeListener((source, change) -> {
			assertEquals(1, change.getAddedEntries().size());

			MapChange.Entry<String, String> entry = change.getAddedEntries().iterator().next();
			assertEquals("k", entry.getKey());
			assertEquals("v", entry.getValue());
			assertTrue(change.wasAdded(entry));
			assertFalse(change.wasRemoved(entry));
			assertFalse(change.wasReplaced(entry));
			box.increment();
		});

		map.put("k", "v");
		assertEquals(1, box.get());
	}

	@Test
	void putAll() {
		IntBox box = new IntBox();
		Map<String, String> stuff = new HashMap<>();
		stuff.put("k1", "v1");
		stuff.put("k2", "v2");

		ObservableMap<String, String> map = new ObservableMap<>();
		map.addChangeListener((source, change) -> {
			assertEquals(2, change.getAddedEntries().size());

			List<MapChange.Entry<String, String>> added = Lists.sorted(ENTRY_CMP, change.getAddedEntries());
			MapChange.Entry<String, String> entry = added.get(0);
			assertEquals("k1", entry.getKey());
			assertEquals("v1", entry.getValue());
			assertTrue(change.wasAdded(entry));
			assertFalse(change.wasRemoved(entry));
			assertFalse(change.wasReplaced(entry));

			entry = added.get(1);
			assertEquals("k2", entry.getKey());
			assertEquals("v2", entry.getValue());
			assertTrue(change.wasAdded(entry));
			assertFalse(change.wasRemoved(entry));
			assertFalse(change.wasReplaced(entry));

			box.increment();
		});

		map.putAll(stuff);
		assertEquals(1, box.get());
	}

	@Test
	void putReplace() {
		IntBox box = new IntBox();

		ObservableMap<String, String> map = new ObservableMap<>();
		map.put("k", "v");

		map.addChangeListener((source, change) -> {
			assertEquals(1, change.getRemovedEntries().size());
			assertEquals(1, change.getAddedEntries().size());

			MapChange.Entry<String, String> entry = change.getRemovedEntries().iterator().next();
			assertEquals("k", entry.getKey());
			assertEquals("v", entry.getValue());
			assertFalse(change.wasAdded(entry));
			assertFalse(change.wasRemoved(entry));
			assertTrue(change.wasReplaced(entry));

			entry = change.getAddedEntries().iterator().next();
			assertEquals("k", entry.getKey());
			assertEquals("other", entry.getValue());
			assertFalse(change.wasAdded(entry));
			assertFalse(change.wasRemoved(entry));
			assertTrue(change.wasReplaced(entry));

			box.increment();
		});

		map.put("k", "other");
		assertEquals(1, box.get());
	}

	@Test
	void putAllReplace() {
		IntBox box = new IntBox();
		Map<String, String> stuff = new HashMap<>();
		stuff.put("k1", "other1");
		stuff.put("k2", "other2");

		ObservableMap<String, String> map = new ObservableMap<>();
		map.put("k1", "v1");
		map.put("k2", "v2");

		map.addChangeListener((source, change) -> {
			assertEquals(2, change.getRemovedEntries().size());
			assertEquals(2, change.getAddedEntries().size());

			List<MapChange.Entry<String, String>> removed = Lists.sorted(ENTRY_CMP, change.getRemovedEntries());
			MapChange.Entry<String, String> entry = removed.get(0);
			assertEquals("k1", entry.getKey());
			assertEquals("v1", entry.getValue());
			assertFalse(change.wasAdded(entry));
			assertFalse(change.wasRemoved(entry));
			assertTrue(change.wasReplaced(entry));
			entry = removed.get(1);
			assertEquals("k2", entry.getKey());
			assertEquals("v2", entry.getValue());
			assertFalse(change.wasAdded(entry));
			assertFalse(change.wasRemoved(entry));
			assertTrue(change.wasReplaced(entry));

			List<MapChange.Entry<String, String>> added = Lists.sorted(ENTRY_CMP, change.getAddedEntries());
			entry = added.get(0);
			assertEquals("k1", entry.getKey());
			assertEquals("other1", entry.getValue());
			assertFalse(change.wasAdded(entry));
			assertFalse(change.wasRemoved(entry));
			assertTrue(change.wasReplaced(entry));
			entry = added.get(1);
			assertEquals("k2", entry.getKey());
			assertEquals("other2", entry.getValue());
			assertFalse(change.wasAdded(entry));
			assertFalse(change.wasRemoved(entry));
			assertTrue(change.wasReplaced(entry));

			box.increment();
		});

		map.putAll(stuff);
		assertEquals(1, box.get());
	}
}
