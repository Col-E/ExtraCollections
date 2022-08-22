package software.coley.collections.tree;

import software.coley.collections.Sets;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@link Map} which contains values of other {@link Map}s of the same key type.
 * <br>
 * To retrieve a value as you would a standard map, call {@link #get(Object)} to retrieve a sub-tree,
 * then call {@link #getValue()} on the sub-tree.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map <i>(wrapped)</i> value type.
 *
 * @author Matt Coley
 */
public interface Tree<K, V> extends Map<K, Tree<K, V>> {
	/**
	 * @return Value of current tree node.
	 */
	V getValue();

	/**
	 * @return Parent tree.
	 */
	Tree<K, V> getParent();

	/**
	 * @param value
	 * 		Value for the subtree to wrap.
	 * @param <T>
	 * 		Tree implementing type.
	 *
	 * @return New sub-tree linked to this tree.
	 */
	<T extends Tree<K, V>> T createSubTree(V value);

	/**
	 * @param key
	 * 		Key value.
	 * @param value
	 * 		Value to wrap.
	 * @param <T>
	 * 		Tree implementing type.
	 *
	 * @return Prior tree associated with key.
	 */
	@SuppressWarnings("unchecked")
	default <T extends Tree<K, V>> T putTree(K key, V value) {
		T subTree = createSubTree(value);
		return (T) put(key, subTree);
	}

	/**
	 * @param <T>
	 * 		Tree implementing type.
	 *
	 * @return Sub-trees that contain other sub-trees.
	 */
	@SuppressWarnings("unchecked")
	default <T extends Tree<K, V>> Set<T> getBranches() {
		return (Set<T>) values().stream()
				.filter(Tree::isBranch)
				.collect(Collectors.toSet());
	}

	/**
	 * @param <T>
	 * 		Tree implementing type.
	 *
	 * @return Sub-trees that contain no sub-trees.
	 */
	@SuppressWarnings("unchecked")
	default <T extends Tree<K, V>> Set<T> getLeaves() {
		return (Set<T>) values().stream()
				.filter(Tree::isLeaf)
				.collect(Collectors.toSet());
	}

	/**
	 * @param <T>
	 * 		Tree implementing type.
	 *
	 * @return All sub-trees, including non-direct ones, that contain no sub-trees.
	 */
	@SuppressWarnings("unchecked")
	default <T extends Tree<K, V>> Set<T> getAllLeaves() {
		if (isLeaf())
			return (Set<T>) Sets.of(this);
		Set<T> values = new HashSet<>();
		values().forEach(t -> values.addAll(t.getAllLeaves()));
		return values;
	}

	/**
	 * @return {@code true} for the root tree <i>(No parent)</i>.
	 */
	default boolean isRoot() {
		return getParent() == null;
	}

	/**
	 * @return {@code true} when there are no sub-trees.
	 */
	default boolean isLeaf() {
		return size() == 0;
	}

	/**
	 * @return {@code true} when there are sub-trees.
	 */
	default boolean isBranch() {
		return size() > 0;
	}
}
