package software.coley.collections.tree;

import java.util.Map;
import java.util.NavigableMap;

/**
 * A {@link NavigableMap} which contains values of other {@link Map}s of the same key type.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map <i>(wrapped)</i> value type.
 *
 * @author Matt Coley
 * @see SortedTree
 */
public interface NavigableTree<K, V> extends SortedTree<K, V>, NavigableMap<K, Tree<K, V>> {
	/**
	 * Mirrors {@link #descendingMap()}.
	 *
	 * @return Reverse ordered view of the mappings contained in this tree.
	 */
	NavigableTree<K, V> descendingTree();

	/**
	 * Mirrors {@link #subMap(Object, boolean, Object, boolean)}.
	 *
	 * @param fromKey
	 * 		Low endpoint of the keys in the returned tree.
	 * @param fromInclusive
	 * 		Inclusive flag for {@code fromKey}.
	 * @param toKey
	 * 		High endpoint of the keys in the returned tree.
	 * @param toInclusive
	 * 		Exclusive flag for {@code toKey}.
	 *
	 * @return View of the portion of this tree whose keys range from
	 * {@code fromKey} <i>(inclusive when flagged)</i> to {@code toKey} <i>(exclusive when flagged)</i>.
	 */
	NavigableTree<K, V> subTree(K fromKey, boolean fromInclusive,
								K toKey, boolean toInclusive);

	/**
	 * Mirrors {@link #headMap(Object, boolean)}.
	 *
	 * @param toKey
	 * 		High endpoint of the keys in the returned tree.
	 * @param inclusive
	 * 		Inclusive flag for {@code toKey}.
	 *
	 * @return View of the portion of this tree whose keys are less than
	 * {@code toKey} <i>(exclusive when flagged)</i>.
	 */
	NavigableTree<K, V> headTree(K toKey, boolean inclusive);

	/**
	 * Mirrors {@link #tailMap(Object, boolean)}.
	 *
	 * @param fromKey
	 * 		Low endpoint of the keys in the returned tree.
	 * @param inclusive
	 * 		Inclusive flag for {@code fromKey}.
	 *
	 * @return View of the portion of this tree whose keys are greater than
	 * <i>(or equal to when flagged)</i> {@code fromKey}.
	 */
	NavigableTree<K, V> tailTree(K fromKey, boolean inclusive);
}
