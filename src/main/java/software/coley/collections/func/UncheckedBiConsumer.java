package software.coley.collections.func;

import software.coley.collections.Unchecked;

import java.util.function.BiConsumer;

/**
 * Its {@link BiConsumer} but can throw an exception.
 *
 * @author xDark
 */
@FunctionalInterface
public interface UncheckedBiConsumer<T, U> extends BiConsumer<T, U> {
	@Override
	default void accept(T t, U u) {
		try {
			uncheckedAccept(t, u);
		} catch (Throwable th) {
			Unchecked.propagate(th);
		}
	}

	void uncheckedAccept(T t, U u) throws Throwable;
}
