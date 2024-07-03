package software.coley.collections;

import software.coley.collections.func.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.*;

/**
 * Convenience calls for the error-able lambda types.
 *
 * @author Matt Coley
 */
public class Unchecked {
	/**
	 * @param value
	 * 		Value to cast.
	 * @param <T>
	 * 		Target type.
	 *
	 * @return Value casted.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cast(@Nullable Object value) {
		return (T) value;
	}

	/**
	 * @param runnable
	 * 		Runnable.
	 */
	public static void run(@Nonnull UncheckedRunnable runnable) {
		runnable.run();
	}

	/**
	 * @param supplier
	 * 		Supplier.
	 * @param <T>
	 * 		Supplier type.
	 *
	 * @return Supplied value.
	 */
	public static <T> T get(@Nonnull UncheckedSupplier<T> supplier) {
		return supplier.get();
	}

	/**
	 * @param supplier
	 * 		Supplier.
	 * @param fallback
	 * 		Value to return if supplier fails.
	 * @param <T>
	 * 		Supplier type.
	 *
	 * @return Supplied value, or fallback if supplier failed.
	 */
	public static <T> T getOr(@Nullable UncheckedSupplier<T> supplier, T fallback) {
		if (supplier == null)
			return fallback;
		try {
			return supplier.get();
		} catch (Throwable t) {
			return fallback;
		}
	}

	/**
	 * @param consumer
	 * 		Consumer.
	 * @param value
	 * 		Consumed value.
	 * @param <T>
	 * 		Consumer type.
	 */
	public static <T> void accept(@Nonnull UncheckedConsumer<T> consumer, T value) {
		consumer.accept(value);
	}

	/**
	 * @param consumer
	 * 		Consumer.
	 * @param t
	 * 		First value.
	 * @param u
	 * 		Second value.
	 * @param <T>
	 * 		First type.
	 * @param <U>
	 * 		Second type.
	 */
	public static <T, U> void baccept(@Nonnull UncheckedBiConsumer<T, U> consumer, T t, U u) {
		consumer.accept(t, u);
	}

	/**
	 * @param fn
	 * 		Function.
	 * @param value
	 * 		Function value.
	 * @param <T>
	 * 		Input type.
	 * @param <R>
	 * 		Output type.
	 */
	public static <T, R> R map(@Nonnull UncheckedFunction<T, R> fn, T value) {
		return fn.apply(value);
	}

	/**
	 * @param fn
	 * 		Function.
	 * @param t
	 * 		First function value.
	 * @param u
	 * 		Second function value.
	 * @param <T>
	 * 		First input type.
	 * @param <U>
	 * 		Second input type.
	 * @param <R>
	 * 		Output type.
	 */
	public static <T, U, R> R bmap(@Nonnull UncheckedBiFunction<T, U, R> fn, T t, U u) {
		return fn.apply(t, u);
	}

	/**
	 * Helper method to created unchecked runnable.
	 *
	 * @param runnable
	 * 		Unchecked runnable.
	 *
	 * @return Unchecked runnable.
	 */
	@Nonnull
	public static Runnable runnable(@Nonnull UncheckedRunnable runnable) {
		return runnable;
	}

	/**
	 * Helper method to created unchecked supplier.
	 *
	 * @param supplier
	 * 		Unchecked supplier.
	 *
	 * @return Unchecked supplier.
	 */
	@Nonnull
	public static <T> Supplier<T> supply(@Nonnull UncheckedSupplier<T> supplier) {
		return supplier;
	}

	/**
	 * Helper method to created unchecked consumer.
	 *
	 * @param consumer
	 * 		Unchecked consumer.
	 *
	 * @return Unchecked consumer.
	 */
	@Nonnull
	public static <T> Consumer<T> consumer(@Nonnull UncheckedConsumer<T> consumer) {
		return consumer;
	}

	/**
	 * Helper method to created unchecked consumer.
	 *
	 * @param consumer
	 * 		Unchecked consumer.
	 *
	 * @return Unchecked consumer.
	 */
	@Nonnull
	public static <T, U> BiConsumer<T, U> bconsumer(@Nonnull UncheckedBiConsumer<T, U> consumer) {
		return consumer;
	}

	/**
	 * Helper method to created unchecked function.
	 *
	 * @param fn
	 * 		Unchecked function.
	 *
	 * @return Unchecked function.
	 */
	@Nonnull
	public static <T, R> Function<T, R> function(@Nonnull UncheckedFunction<T, R> fn) {
		return fn;
	}

	/**
	 * Helper method to created unchecked function.
	 *
	 * @param fn
	 * 		Unchecked function.
	 *
	 * @return Unchecked function.
	 */
	@Nonnull
	public static <T, U, R> BiFunction<T, U, R> bfunction(@Nonnull UncheckedBiFunction<T, U, R> fn) {
		return fn;
	}

	/**
	 * Propagates throwable.
	 *
	 * @param t
	 * 		Throwable to propagate.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Throwable> void propagate(@Nonnull Throwable t) throws X {
		throw (X) t;
	}

	/**
	 * Runs an action via a consumer on each item of the collection. If an error occurs for a given item,
	 * it is passed along to the error consumer along with the error before moving onto the next item in
	 * the collection.
	 *
	 * @param collection
	 * 		Collection to iterate over.
	 * @param consumer
	 * 		Action to run on each item.
	 * @param errorConsumer
	 * 		Error handling taking in the item that the consumer failed on, and the error thrown.
	 * @param <T>
	 * 		Item type.
	 */
	public static <T> void checkedForEach(@Nonnull Collection<T> collection,
	                                      @Nonnull UncheckedConsumer<T> consumer,
	                                      @Nonnull BiConsumer<T, Throwable> errorConsumer) {
		// Iterate over a shallow-copy in case the consumer updates the original collection.
		for (T item : new ArrayList<>(collection)) {
			try {
				consumer.accept(item);
			} catch (Throwable t) {
				errorConsumer.accept(item, t);
			}
		}
	}

	/**
	 * Runs an action via a bi-consumer on each entry of the map. If an error occurs for a given entry,
	 * it is passed along to the error consumer along with the error before moving onto the next item in the map.
	 *
	 * @param map
	 * 		Map to iterate over.
	 * @param consumer
	 * 		Action to run on each item.
	 * @param errorConsumer
	 * 		Error handling taking in the entry that the consumer failed on, and the error thrown.
	 * @param <K>
	 * 		Map key type.
	 * @param <V>
	 * 		Map value type.
	 */
	public static <K, V> void checkedForEach(@Nonnull Map<K, V> map,
	                                         @Nonnull BiConsumer<K, V> consumer,
	                                         @Nonnull TriConsumer<K, V, Throwable> errorConsumer) {
		// Iterate over a shallow-copy in case the consumer updates the original collection.
		for (Map.Entry<K, V> entry : new ArrayList<>(map.entrySet())) {
			K key = entry.getKey();
			V value = entry.getValue();
			try {
				consumer.accept(key, value);
			} catch (Throwable t) {
				errorConsumer.accept(key, value, t);
			}
		}
	}
}
