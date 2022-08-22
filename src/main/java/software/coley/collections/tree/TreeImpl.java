package software.coley.collections.tree;

import software.coley.collections.delegate.DelegatingMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Base tree implementation.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map <i>(wrapped)</i> value type.
 *
 * @author Matt Coley
 */
public class TreeImpl<K, V> extends DelegatingMap<K, Tree<K, V>> implements Tree<K, V> {
	private final Supplier<Map<K, ?>> delegateSupplier;
	private final Tree<K, V> parent;
	private final V value;

	/**
	 * Empty tree node.
	 */
	public TreeImpl() {
		this(null, null);
	}

	/**
	 * @param value
	 * 		Value of tree node.
	 */
	public TreeImpl(V value) {
		this(null, value);
	}

	/**
	 * @param parent
	 * 		Parent tree.
	 * @param value
	 * 		Value of tree node.
	 */
	public TreeImpl(Tree<K, V> parent, V value) {
		this(HashMap::new, parent, value);
	}

	/**
	 * @param delegateSupplier
	 * 		Supplier to create delegate maps with.
	 * @param parent
	 * 		Parent tree.
	 * @param value
	 * 		Value of tree node.
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public TreeImpl(Supplier<Map<K, ?>> delegateSupplier, Tree<K, V> parent, V value) {
		super((Map) delegateSupplier.get());
		this.delegateSupplier = delegateSupplier;
		this.parent = parent;
		this.value = value;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public Tree<K, V> getParent() {
		return parent;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Tree<K, V>> T createSubTree(V value) {
		return (T) new TreeImpl<>(delegateSupplier, this, value);
	}
}
