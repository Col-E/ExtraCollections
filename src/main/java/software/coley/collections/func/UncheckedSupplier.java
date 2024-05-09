package software.coley.collections.func;

import software.coley.collections.Unchecked;

import java.util.function.Supplier;

/**
 * Its {@link Supplier} but can throw an exception.
 *
 * @author Matt Coley
 */
@FunctionalInterface
public interface UncheckedSupplier<T> extends Supplier<T> {

	@Override
	default T get() {
		try {
			return uncheckedGet();
		} catch (Throwable t) {
			Unchecked.propagate(t);
			return null;
		}
	}

	/**
	 * @return Supplier output.
	 *
	 * @throws Throwable
	 * 		Whenever.
	 */
	T uncheckedGet() throws Throwable;
}
