package software.coley.collections;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListsTest {

	@Test
	void add() {
		assertEquals(asList("a", "b"), Lists.add(singletonList("a"), "b"));
		assertEquals(asList("a", null), Lists.add(singletonList("a"), null)); // will add null literal
		assertEquals(singletonList("b"), Lists.add(null, "b"));
		assertEquals(asList("a", "b", "c"), Lists.add(asList("a", "b"), "c"));
	}

	@Test
	void combine() {
		assertEquals(singletonList("a"), Lists.combine(singletonList("a"), emptyList()));
		assertEquals(singletonList("a"), Lists.combine(singletonList("a"), null));
		assertEquals(singletonList("b"), Lists.combine(emptyList(), singletonList("b")));
		assertEquals(singletonList("b"), Lists.combine(null, singletonList("b")));
		assertEquals(asList("a", "b"), Lists.combine(singletonList("a"), singletonList("b")));
	}

	@Test
	void distinct() {
		assertEquals(asList("a", "b", "d", "e"), Lists.disjoint(asList("a", "b", "c"), asList("c", "d", "e")));
		assertEquals(asList("a", "b", "c"), Lists.disjoint(asList("a", "b", "c"), null));
		assertEquals(asList("a", "b", "c"), Lists.disjoint(asList("a", "b", "c"), emptyList()));
		assertEquals(asList("a", "b", "c"), Lists.disjoint(null, asList("a", "b", "c")));
		assertEquals(asList("a", "b", "c"), Lists.disjoint(emptyList(), asList("a", "b", "c")));
	}

	@Test
	void disjoint() {
		assertEquals(asList("a", "b", "d", "e"), Lists.disjoint(asList("a", "b", "c"), asList("c", "d", "e")));
		assertEquals(asList("a", "b", "c"), Lists.disjoint(asList("a", "b", "c"), emptyList()));
		assertEquals(asList("a", "b", "c"), Lists.disjoint(asList("a", "b", "c"), null));
		assertEquals(asList("a", "b", "c"), Lists.disjoint(emptyList(), asList("a", "b", "c")));
		assertEquals(asList("a", "b", "c"), Lists.disjoint(null, asList("a", "b", "c")));
	}

	@Test
	void union() {
		assertEquals(singletonList("c"), Lists.union(asList("a", "b", "c"), asList("c", "d", "e")));
		assertEquals(emptyList(), Lists.union(asList("a", "b", "c"), emptyList()));
		assertEquals(emptyList(), Lists.union(asList("a", "b", "c"), null));
		assertEquals(emptyList(), Lists.union(emptyList(), asList("a", "b", "c")));
		assertEquals(emptyList(), Lists.union(null, asList("a", "b", "c")));
	}

	@Test
	void reversed() {
		assertEquals(asList("c", "b", "a"), Lists.reversed(asList("a", "b", "c")));
	}

	@Test
	void of() {
		assertEquals(singletonList("a"), Lists.of("a"));
		assertEquals(asList("a", "b"), Lists.of(new String[]{"a", "b"}));
		assertEquals(asList("a", "b"), Lists.ofVar("a", "b"));
	}

	@Test
	void binarySearch() {
		List<String> strings = asList("a", "b", "c", /* d */ "e", "f");
		assertEquals(0, Lists.binarySearch(strings, "a"));
		assertEquals(2, Lists.binarySearch(strings, "c"));
		assertEquals(3, Lists.binarySearch(strings, "e"));
		assertEquals(4, Lists.binarySearch(strings, "f"));
		assertEquals(-1, Lists.binarySearch(strings, " ")); // <space> is not in the list but would appear first
		assertEquals(-2, Lists.binarySearch(strings, "d")); // d is not in the list but would appear between c and e
		assertEquals(-4, Lists.binarySearch(strings, "g")); // g is not in the list but would appear last

		// Bounded range checks have the negative value relative to the 'first' position.
		// So inserting "a" into the [] range for {a, b, [c, e, f]} yields {-first -1}
		assertEquals(0, Lists.binarySearch(strings, "a", 1, 5));
		assertEquals(-1, Lists.binarySearch(strings, "a", 2, 5));
		assertEquals(-2, Lists.binarySearch(strings, "a", 3, 5));
		assertEquals(-3, Lists.binarySearch(strings, "a", 4, 5));
	}

	@Test
	void sortedInsertIndex() {
		// Unlike the binary search call, this normalizes the index to positive range
		List<String> strings = asList("a", "b", "c", /* d */ "e", "f");
		assertEquals(0, Lists.sortedInsertIndex(strings, " "));
		assertEquals(0, Lists.sortedInsertIndex(strings, "a"));
		assertEquals(5, Lists.sortedInsertIndex(strings, "g"));
	}

	@Test
	void compare() {
		assertEquals(0, Lists.compare(asList("a", "b"), asList("a", "b")));
		assertTrue(0 > Lists.compare(asList("a", "b"), asList("c", "d"))); // negative result
		assertTrue(0 < Lists.compare(asList("c", "d"), asList("a", "b"))); // positive result

		Comparator<String> cmp = String.CASE_INSENSITIVE_ORDER::compare;
		assertEquals(0, Lists.compare(cmp, asList("a", "b"), asList("a", "b")));
		assertTrue(0 > Lists.compare(cmp, asList("a", "b"), asList("c", "d"))); // negative result
		assertTrue(0 < Lists.compare(cmp, asList("c", "d"), asList("a", "b"))); // positive result
	}

	@Test
	void noopList() {
		List<Object> list = Lists.noopList();
		list.add("test");
		assertEquals(0, list.size());
	}
}