package software.coley.collections;

import org.junit.jupiter.api.Test;
import software.coley.collections.tree.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link Tree}
 */
public class TreeTest {
	@Test
	public void test_equality() {
		Tree<String, String> tree1 = new TreeImpl<>("root");
		Tree<String, String> tree2 = new SortedTreeImpl<>("root");
		Tree<String, String> tree3 = new NavigableTreeImpl<>("root");
		List<Tree<String, String>> trees = Lists.ofVar(tree1, tree2, tree3);
		for (Tree<String, String> tree : trees) {
			tree.putTree("keyA", "value1");
			tree.putTree("keyB", "value2");
			tree.putTree("keyC", "value3");
		}
		assertEquals(tree1, tree2);
		assertEquals(tree2, tree3);
	}

	@Test
	public void test_childParentRelations() {
		Tree<String, String> root = new TreeImpl<>("root");
		Tree<String, String> subTree = root.computeIfAbsent("test", root::createSubTree);
		assertEquals("test", subTree.getValue());
		assertEquals(root, subTree.getParent());
	}

	@Test
	public void test_subtree_sameType() {
		Tree<String, String> tree1 = new TreeImpl<>("root");
		Tree<String, String> tree2 = new SortedTreeImpl<>("root");
		Tree<String, String> tree3 = new NavigableTreeImpl<>("root");
		List<Tree<String, String>> trees = Lists.ofVar(tree1, tree2, tree3);
		for (Tree<String, String> tree : trees) {
			for (int i = 0; i < 5; i++) {
				tree.putTree("key", "value");
				Tree<String, String> subTree = tree.get("key");
				assertEquals(tree.getClass(), subTree.getClass());
				tree = subTree;
			}
		}
	}

	@Test
	public void test_sorted_keyOrder() {
		Tree<String, String> tree1 = new SortedTreeImpl<>("root");
		Tree<String, String> tree2 = new NavigableTreeImpl<>("root");
		List<Tree<String, String>> trees = Lists.ofVar(tree1, tree2);
		for (Tree<String, String> tree : trees) {
			tree.putTree("keyA", "value1");
			tree.putTree("keyB", "value2");
			tree.putTree("keyC", "value3");
		}
		assertEquals(Lists.ofVar("keyA", "keyB", "keyC"), new ArrayList<>(tree1.keySet()));
		assertEquals(Lists.ofVar("keyA", "keyB", "keyC"), new ArrayList<>(tree2.keySet()));
	}

	@Test
	public void test_sorted_headAndTail_full() {
		SortedTree<String, String> tree = new SortedTreeImpl<>("root");
		SortedTree<String, String> headZ = tree.headTree("z");
		SortedTree<String, String> tailA = tree.tailTree("a");
		// Populate original tree
		List<String> keys = Lists.ofVar("keyA", "keyB", "keyC", "keyD", "keyE");
		tree.putTree("keyA", "value1");
		tree.putTree("keyB", "value2");
		tree.putTree("keyC", "value3");
		tree.putTree("keyD", "value4");
		tree.putTree("keyE", "value5");
		assertEquals("keyA", tree.firstKey());
		assertEquals("keyE", tree.lastKey());
		// 'headTree' should be a "live" view of the tree for keys less than 'z' (which they should all be)
		assertEquals("keyA", headZ.firstKey());
		assertEquals("keyE", headZ.lastKey());
		assertEquals(keys, new ArrayList<>(headZ.keySet()));
		// 'tailTree' should be a "live" view of the tree for keys greater than 'a' (which they should all be)
		assertEquals("keyA", tailA.firstKey());
		assertEquals("keyE", tailA.lastKey());
		assertEquals(keys, new ArrayList<>(tailA.keySet()));
	}

	@Test
	public void test_sorted_headAndTail_partial() {
		SortedTree<String, String> tree = new SortedTreeImpl<>("root");
		SortedTree<String, String> headC = tree.headTree("keyC");
		SortedTree<String, String> tailC = tree.tailTree("keyC");
		// Populate original tree
		List<String> keys = Lists.ofVar("keyA", "keyB", "keyC", "keyD", "keyE");
		tree.putTree("keyA", "value1");
		tree.putTree("keyB", "value2");
		tree.putTree("keyC", "value3");
		tree.putTree("keyD", "value4");
		tree.putTree("keyE", "value5");
		assertEquals("keyA", tree.firstKey());
		assertEquals("keyE", tree.lastKey());
		// 'headTree' should be a "live" view of the tree for keys less than 'keyC' (which the first two should be)
		assertEquals("keyA", headC.firstKey());
		assertEquals("keyB", headC.lastKey());
		assertEquals(keys.subList(0, 2), new ArrayList<>(headC.keySet()));
		// 'tailTree' should be a "live" view of the tree for keys greater than 'keyC' (which the last three should be)
		assertEquals("keyC", tailC.firstKey());
		assertEquals("keyE", tailC.lastKey());
		assertEquals(keys.subList(2, 5), new ArrayList<>(tailC.keySet()));
	}

	@Test
	public void test_navigable_descending() {
		NavigableTree<String, String> tree = new NavigableTreeImpl<>("root");
		NavigableTree<String, String> descending = tree.descendingTree();
		// Populate original tree
		List<String> keys = Lists.ofVar("keyA", "keyB", "keyC", "keyD", "keyE");
		tree.putTree("keyA", "value1");
		tree.putTree("keyB", "value2");
		tree.putTree("keyC", "value3");
		tree.putTree("keyD", "value4");
		tree.putTree("keyE", "value5");
		assertEquals("keyA", tree.firstKey());
		assertEquals("keyE", tree.lastKey());
		// 'descendingTree' should be a "live" view of the reverse order tree, so check opposite cases on it.
		assertEquals("keyE", descending.firstKey());
		assertEquals("keyA", descending.lastKey());
		assertEquals(Lists.reversed(keys), new ArrayList<>(descending.keySet()));
	}
}
