package software.coley.collections.box;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;

/**
 * {@code int} box.
 *
 * @author Matt Coley
 */
public class IntBox {
	private int value;

	/**
	 * New box with an initial value of 0.
	 */
	public IntBox() {}

	/**
	 * New box with the given initial value.
	 *
	 * @param value
	 * 		Initial value.
	 */
	public IntBox(int value) {
		set(value);
	}

	/**
	 * @return Current boxed value.
	 */
	public int get() {
		return value;
	}

	/**
	 * @param value
	 * 		Value to set.
	 */
	public void set(int value) {
		this.value = value;
	}

	/**
	 * @param function
	 * 		Mapping function.
	 * @param <R>
	 * 		Result type.
	 *
	 * @return Mapped value.
	 */
	public <R> R map(IntFunction<R> function) {
		return function.apply(get());
	}

	/**
	 * @param predicate
	 * 		Predicate to test value with.
	 *
	 * @return Predicate test results.
	 */
	public boolean test(IntPredicate predicate) {
		return predicate.test(get());
	}

	/**
	 * @param other
	 * 		Some value.
	 *
	 * @return The passed value or the current value, whichever is lower.
	 */
	public int min(int other) {
		return Math.min(get(), other);
	}

	/**
	 * @param other
	 * 		Some value.
	 *
	 * @return The passed value or the current value, whichever is higher.
	 */
	public int max(int other) {
		return Math.max(get(), other);
	}

	/**
	 * @param min
	 * 		Some lower value.
	 * @param max
	 * 		some higher value.
	 *
	 * @return The current value clamped into the given range.
	 */
	public int clamp(int min, int max) {
		return get() > max ? max : Math.max(get(), min);
	}

	/**
	 * Clamps the value if it is higher than the passed value.
	 *
	 * @param min
	 * 		Some value.
	 */
	public void setMin(int min) {
		if (get() < min) set(min);
	}

	/**
	 * Clamps the value if it is higher than the passed value.
	 *
	 * @param max
	 * 		Some value.
	 */
	public void setMax(int max) {
		if (get() > max) set(max);
	}

	/**
	 * Clamps the value if it is outside the passed min-max range.
	 *
	 * @param min
	 * 		Some lower value.
	 * @param max
	 * 		some higher value.
	 */
	public void setClamp(int min, int max) {
		int oldValue = get();
		if (oldValue < min) set(min);
		else if (oldValue > max) set(max);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		IntBox box = (IntBox) o;

		return value == box.value;
	}

	@Override
	public int hashCode() {
		return value;
	}
}
