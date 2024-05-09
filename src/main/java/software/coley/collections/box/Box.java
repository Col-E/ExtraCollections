package software.coley.collections.box;

import software.coley.collections.func.ObjToIntFunction;
import software.coley.collections.func.ObjToLongFunction;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Object box.
 *
 * @param <T>
 * 		Object type.
 *
 * @author Matt Coley
 */
public class Box<T> {
	private T value;

	/**
	 * New box with no assigned value.
	 */
	public Box() {
		this(null);
	}

	/**
	 * New box with a given value.
	 *
	 * @param value
	 * 		Value to initially assign.
	 */
	public Box(@Nullable T value) {
		set(value);
	}

	/**
	 * @return Current boxed value.
	 */
	@Nullable
	public T get() {
		return value;
	}

	/**
	 * @param value
	 * 		Value to set.
	 */
	public void set(T value) {
		this.value = value;
	}

	/**
	 * @param function
	 * 		Mapping function.
	 * @param <R>
	 * 		Result type.
	 *
	 * @return Mapped value, or {@code null} if the box has no value.
	 */
	public <R> R map(Function<T, R> function) {
		return isSet() ? function.apply(get()) : null;
	}

	/**
	 * @param function
	 * 		Mapping function.
	 *
	 * @return Mapped value, or {@code -1} if the box has no value.
	 */
	public int mapToInt(ObjToIntFunction<T> function) {
		return isSet() ? -1 : function.map(get());
	}

	/**
	 * @param function
	 * 		Mapping function.
	 *
	 * @return Mapped value.
	 */
	public int mapNullableToInt(ObjToIntFunction<T> function) {
		return function.map(get());
	}

	/**
	 * @param function
	 * 		Mapping function.
	 *
	 * @return Mapped value, or {@code -1L} if the box has no value.
	 */
	public long mapToLong(ObjToLongFunction<T> function) {
		return isSet() ? -1L : function.map(get());
	}

	/**
	 * @param function
	 * 		Mapping function.
	 *
	 * @return Mapped value.
	 */
	public long mapNullableToLong(ObjToLongFunction<T> function) {
		return function.map(get());
	}

	/**
	 * @param function
	 * 		Mapping function.
	 * @param <R>
	 * 		Result type.
	 *
	 * @return Mapped value.
	 */
	public <R> R mapNullable(Function<T, R> function) {
		return function.apply(get());
	}

	/**
	 * @param newValue
	 * 		New value to set.
	 *
	 * @return Old value.
	 */
	public T getAndSet(T newValue) {
		T oldValue = get();
		set(newValue);
		return oldValue;
	}

	/**
	 * @param newValue
	 * 		Function to adapt old value.
	 *
	 * @return Old value.
	 */
	public T getAndCompute(Function<T, T> newValue) {
		T oldValue = get();
		set(newValue.apply(oldValue));
		return oldValue;
	}

	/**
	 * @param supplier
	 * 		Supplier to provide a value if none is set.
	 *
	 * @return Current value.
	 */
	public T computeIfAbsent(Supplier<T> supplier) {
		if (!isSet())
			set(supplier.get());
		return value;
	}

	/**
	 * Updates the boxed value if the new value is less than the current value.
	 *
	 * @param value
	 * 		Value to potentially set.
	 * @param comparator
	 * 		Comparator to see if the value is less than the current value.
	 */
	public void setMin(T value, Comparator<T> comparator) {
		if (!isSet() || comparator.compare(value, get()) < 0)
			set(value);
	}

	/**
	 * Updates the boxed value if the new value is greater than the current value.
	 *
	 * @param value
	 * 		Value to potentially set.
	 * @param comparator
	 * 		Comparator to see if the value is greater than the current value.
	 */
	public void setMax(T value, Comparator<T> comparator) {
		if (!isSet() || comparator.compare(value, get()) > 0)
			set(value);
	}

	/**
	 * Remove value.
	 */
	public void clear() {
		set(null);
	}

	/**
	 * @param predicate
	 * 		Predicate to test value with.
	 *
	 * @return Predicate test results. Always {@code false} if the boxed value is not set.
	 */
	public boolean test(Predicate<T> predicate) {
		return isSet() && predicate.test(get());
	}

	/**
	 * @return {@code true} if there is a set value.
	 * {@code false} if the box has no assigned value.
	 */
	public boolean isSet() {
		return value != null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Box<?> box = (Box<?>) o;

		return Objects.equals(value, box.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
