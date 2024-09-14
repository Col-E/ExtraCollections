package software.coley.collections.tree;

import software.coley.collections.delegate.DelegatingNavigableMap;

import javax.annotation.Nonnull;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Supplier;

/**
 * Navigable tree implementation.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map <i>(wrapped)</i> value type.
 *
 * @author Matt Coley
 */
public class NavigableTreeImpl<K, V> extends DelegatingNavigableMap<K, Tree<K, V>> implements NavigableTree<K, V> {
	private final Supplier<NavigableMap<K, ?>> delegateSupplier;
	private final NavigableTree<K, V> parent;
	private final V value;

	/**
	 * Empty tree node.
	 */
	public NavigableTreeImpl() {
		this(null);
	}

	/**
	 * @param value
	 * 		Value of tree node.
	 */
	public NavigableTreeImpl(V value) {
		this(null, value);
	}

	/**
	 * @param parent
	 * 		Parent tree.
	 * @param value
	 * 		Value of tree node.
	 */
	public NavigableTreeImpl(NavigableTree<K, V> parent, V value) {
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
	public NavigableTreeImpl(Supplier<NavigableMap<K, ?>> delegateSupplier, NavigableTree<K, V> parent, V value) {
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
	public NavigableTreeImpl(Supplier<NavigableMap<K, ?>> delegateSupplier, NavigableMap<K, ?> delegate, NavigableTree<K, V> parent, V value) {
		super((NavigableMap) delegate);
		this.delegateSupplier = delegateSupplier;
		this.parent = parent;
		this.value = value;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public NavigableTree<K, V> getParent() {
		return parent;
	}

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Tree<K, V>> T createSubTree(V value) {
		return (T) new NavigableTreeImpl<>(delegateSupplier, this, value);
	}

	@Nonnull
	@Override
	public NavigableTree<K, V> descendingTree() {
		return from(descendingMap());
	}

	@Nonnull
	@Override
	public NavigableTree<K, V> subTree(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
		return from(subMap(fromKey, fromInclusive, toKey, toInclusive));
	}

	@Nonnull
	@Override
	public NavigableTree<K, V> subTree(K fromKey, K toKey) {
		return from((NavigableMap<K, Tree<K, V>>) subMap(fromKey, toKey));
	}

	@Nonnull
	@Override
	public NavigableTree<K, V> headTree(K toKey, boolean inclusive) {
		return from(headTree(toKey, inclusive));
	}

	@Nonnull
	@Override
	public NavigableTree<K, V> headTree(K toKey) {
		return from((NavigableMap<K, Tree<K, V>>) headMap(toKey));
	}

	@Nonnull
	@Override
	public NavigableTree<K, V> tailTree(K fromKey, boolean inclusive) {
		return from(tailMap(fromKey, inclusive));
	}

	@Nonnull
	@Override
	public NavigableTree<K, V> tailTree(K fromKey) {
		return from((NavigableMap<K, Tree<K, V>>) tailMap(fromKey));
	}

	private NavigableTree<K, V> from(NavigableMap<K, Tree<K, V>> subMap) {
		NavigableTreeImpl<K, V> subTree = new NavigableTreeImpl<>(delegateSupplier, subMap, this, value);
		subTree.putAll(subMap);
		return subTree;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Tree) || !super.equals(o)) return false;

		Tree<?, ?> that = (Tree<?, ?>) o;
		return Objects.equals(value, that.getValue());
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "NavigableTreeImpl{" + value + '}';
	}
}
