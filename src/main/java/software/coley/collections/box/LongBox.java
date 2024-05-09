package software.coley.collections.box;

import java.util.function.LongFunction;
import java.util.function.LongPredicate;

/**
 * {@code long} box.
 *
 * @author Matt Coley
 */
public class LongBox {
	private long value;

	/**
	 * New box with an initial value of 0.
	 */
	public LongBox() {}

	/**
	 * New box with the given initial value.
	 *
	 * @param value
	 * 		Initial value.
	 */
	public LongBox(long value) {
		set(value);
	}

	/**
	 * @return Current boxed value.
	 */
	public long get() {
		return value;
	}

	/**
	 * @param value
	 * 		Value to set.
	 */
	public void set(long value) {
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
	public <R> R map(LongFunction<R> function) {
		return function.apply(get());
	}

	/**
	 * @param predicate
	 * 		Predicate to test value with.
	 *
	 * @return Predicate test results.
	 */
	public boolean test(LongPredicate predicate) {
		return predicate.test(get());
	}

	/**
	 * @param other
	 * 		Some value.
	 *
	 * @return The passed value or the current value, whichever is lower.
	 */
	public long min(long other) {
		return Math.min(get(), other);
	}

	/**
	 * @param other
	 * 		Some value.
	 *
	 * @return The passed value or the current value, whichever is higher.
	 */
	public long max(long other) {
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
	public long clamp(long min, long max) {
		return get() > max ? max : Math.max(get(), min);
	}

	/**
	 * Clamps the value if it is higher than the passed value.
	 *
	 * @param min
	 * 		Some value.
	 */
	public void setMin(long min) {
		if (get() < min) set(min);
	}

	/**
	 * Clamps the value if it is higher than the passed value.
	 *
	 * @param max
	 * 		Some value.
	 */
	public void setMax(long max) {
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
	public void setClamp(long min, long max) {
		long oldValue = get();
		if (oldValue < min) set(min);
		else if (oldValue > max) set(max);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LongBox box = (LongBox) o;

		return value == box.value;
	}

	@Override
	public int hashCode() {
		return (int) (value ^ (value >>> 32));
	}
}
