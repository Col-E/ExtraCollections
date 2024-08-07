package software.coley.collections.bidi;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A {@link BiMap} implementation that uses {@link Supplier}s to support any backing {@link Map} type
 * for the underlying {@link software.coley.collections.delegate.DelegatingMap}.
 *
 * @param <K>
 * 		Map key type.
 * @param <V>
 * 		Map value type.
 *
 * @author Matt Coley
 */
public class AnyBiMap<K, V> extends AbstractBiMap<K, V> {
	private final Supplier<Map<?, ?>> delegateSupplier;

	@SuppressWarnings("unchecked")
	public AnyBiMap(@Nonnull Supplier<Map<?, ?>> delegateSupplier) {
		this(delegateSupplier, (Map<K, V>) delegateSupplier.get());
	}

	public AnyBiMap(@Nonnull Supplier<Map<?, ?>> delegateSupplier, @Nonnull Map<K, V> delegate) {
		super(delegate);
		this.delegateSupplier = delegateSupplier;
	}

	@Nonnull
	@Override
	protected <K1, V1> AbstractBiMap<K1, V1> createImpl() {
		return new AnyBiMap<>(delegateSupplier);
	}
}
