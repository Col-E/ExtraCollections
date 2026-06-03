package software.coley.collections;

import org.junit.jupiter.api.Test;
import software.coley.collections.box.Box;

import java.util.ArrayList;
import java.util.Collections;
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
	void intersection() {
		assertEquals(singletonList("c"), Lists.intersection(asList("a", "b", "c"), asList("c", "d", "e")));
		assertEquals(emptyList(), Lists.intersection(asList("a", "b", "c"), emptyList()));
		assertEquals(emptyList(), Lists.intersection(asList("a", "b", "c"), null));
		assertEquals(emptyList(), Lists.intersection(emptyList(), asList("a", "b", "c")));
		assertEquals(emptyList(), Lists.intersection(null, asList("a", "b", "c")));
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
	void identityIndexOf() {
		Foo a = new Foo();
		Foo b = new Foo();
		Foo c = new Foo();
		List<Foo> strings = asList(a, b);
		assertEquals(0, Lists.identityIndexOf(strings, a));
		assertEquals(1, Lists.identityIndexOf(strings, b));
		assertEquals(2, Lists.identityIndexOf(strings, c));
	}

	@Test
	void binarySearch() {
		// Base case, empty list
		assertEquals(-1, Lists.binarySearch(Collections.emptyList(), "a"));

		// Alphabetical test case
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
	void binaryBoxedSearch() {
		// Same test as the above but boxed
		List<Box<String>> strings = asList(new Box<>("a"), new Box<>("b"), new Box<>("c"), /* new Box<>("d") */ new Box<>("e"), new Box<>("f"));
		assertEquals(0, Lists.binaryUnboxingSearch(strings, "a", Box::get));
		assertEquals(2, Lists.binaryUnboxingSearch(strings, "c", Box::get));
		assertEquals(3, Lists.binaryUnboxingSearch(strings, "e", Box::get));
		assertEquals(4, Lists.binaryUnboxingSearch(strings, "f", Box::get));
		assertEquals(-1, Lists.binaryUnboxingSearch(strings, " ", Box::get));
		assertEquals(-2, Lists.binaryUnboxingSearch(strings, "d", Box::get));
		assertEquals(-4, Lists.binaryUnboxingSearch(strings, "g", Box::get));
		assertEquals(0, Lists.binaryUnboxingSearch(strings, "a", Box::get, 1, 5));
		assertEquals(-1, Lists.binaryUnboxingSearch(strings, "a", Box::get, 2, 5));
		assertEquals(-2, Lists.binaryUnboxingSearch(strings, "a", Box::get, 3, 5));
		assertEquals(-3, Lists.binaryUnboxingSearch(strings, "a", Box::get, 4, 5));
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
	void sortedInsert() {
		List<String> strings = new ArrayList<>(asList("a", "b", "c", /* d */ "e", "f"));
		assertTrue(Lists.sortedInsert(strings, " "));
		assertEquals(0, strings.indexOf(" "));
		assertTrue(Lists.sortedInsert(strings, "a"));
		assertEquals(1, strings.indexOf("a"));
		assertEquals(2, strings.lastIndexOf("a"));
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

	static class Foo {}
}