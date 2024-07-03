package software.coley.collections.tree;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.SortedMap;

/**
 * A {@link SortedMap} which contains values of other {@link Map}s of the same key type.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map <i>(wrapped)</i> value type.
 *
 * @author Matt Coley
 * @see Tree
 */
public interface SortedTree<K, V> extends Tree<K, V>, SortedMap<K, Tree<K, V>> {
	/**
	 * Mirrors {@link #subMap(Object, Object)}.
	 *
	 * @param fromKey
	 * 		Low endpoint <i>(inclusive)</i> of the keys in the returned tree.
	 * @param toKey
	 * 		High endpoint <i>(exclusive)</i> of the keys in the returned tree.
	 *
	 * @return View of the portion of this tree whose keys range from
	 * {@code fromKey} <i>(inclusive)</i> to {@code toKey} <i>(exclusive)</i>.
	 */
	@Nonnull
	SortedTree<K, V> subTree(K fromKey, K toKey);

	/**
	 * Mirrors {@link #headMap(Object)}.
	 *
	 * @param toKey
	 * 		High endpoint <i>(exclusive)</i> of the keys in the returned tree.
	 *
	 * @return View of the portion of this tree whose keys are less than
	 * {@code toKey} <i>(exclusive)</i>.
	 */
	@Nonnull
	SortedTree<K, V> headTree(K toKey);

	/**
	 * Mirrors {@link #tailMap(Object)}.
	 *
	 * @param fromKey
	 * 		Low endpoint <i>(inclusive)</i> of the keys in the returned tree.
	 *
	 * @return View of the portion of this tree whose keys are greater than or equal to
	 * {@code fromKey}.
	 */
	@Nonnull
	SortedTree<K, V> tailTree(K fromKey);

}
