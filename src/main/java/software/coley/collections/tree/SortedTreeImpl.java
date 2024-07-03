package software.coley.collections.tree;

import software.coley.collections.delegate.DelegatingSortedMap;

import javax.annotation.Nonnull;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;

/**
 * Sorted tree implementation.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map <i>(wrapped)</i> value type.
 *
 * @author Matt Coley
 */
public class SortedTreeImpl<K, V> extends DelegatingSortedMap<K, Tree<K, V>> implements SortedTree<K, V> {
	private final Supplier<SortedMap<K, ?>> delegateSupplier;
	private final SortedTree<K, V> parent;
	private final V value;

	/**
	 * Empty tree node.
	 */
	public SortedTreeImpl() {
		this(null);
	}

	/**
	 * @param value
	 * 		Value of tree node.
	 */
	public SortedTreeImpl(V value) {
		this(null, value);
	}

	/**
	 * @param parent
	 * 		Parent tree.
	 * @param value
	 * 		Value of tree node.
	 */
	public SortedTreeImpl(SortedTree<K, V> parent, V value) {
		this(TreeMap::new, parent, value);
	}

	/**
	 * @param delegateSupplier
	 * 		Supplier to create delegate maps with.
	 * @param parent
	 * 		Parent tree.
	 * @param value
	 * 		Value of tree node.
	 */
	public SortedTreeImpl(Supplier<SortedMap<K, ?>> delegateSupplier, SortedTree<K, V> parent, V value) {
		this(delegateSupplier, delegateSupplier.get(), parent, value);
	}

	/**
	 * @param delegateSupplier
	 * 		Supplier to create delegate maps with.
	 * @param delegate
	 * 		Delegate to use for this instance.
	 * @param parent
	 * 		Parent tree.
	 * @param value
	 * 		Value of tree node.
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public SortedTreeImpl(Supplier<SortedMap<K, ?>> delegateSupplier, SortedMap<K, ?> delegate, SortedTree<K, V> parent, V value) {
		super((SortedMap) delegate);
		this.delegateSupplier = delegateSupplier;
		this.parent = parent;
		this.value = value;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public SortedTree<K, V> getParent() {
		return parent;
	}

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Tree<K, V>> T createSubTree(V value) {
		return (T) new SortedTreeImpl<>(delegateSupplier, this, value);
	}

	@Nonnull
	@Override
	public SortedTree<K, V> subTree(K fromKey, K toKey) {
		return from(subMap(fromKey, toKey));
	}

	@Nonnull
	@Override
	public SortedTree<K, V> headTree(K toKey) {
		return from(headMap(toKey));
	}

	@Nonnull
	@Override
	public SortedTree<K, V> tailTree(K fromKey) {
		return from(tailMap(fromKey));
	}

	private SortedTree<K, V> from(SortedMap<K, Tree<K, V>> subMap) {
		SortedTreeImpl<K, V> subTree = new SortedTreeImpl<>(delegateSupplier, subMap, this, value);
		subTree.putAll(subMap);
		return subTree;
	}
}
