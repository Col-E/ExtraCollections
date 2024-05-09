package software.coley.collections.box;

import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;

/**
 * {@code double} box.
 *
 * @author Matt Coley
 */
public class DoubleBox {
	private double value;

	/**
	 * New box with an initial value of 0.
	 */
	public DoubleBox() {}

	/**
	 * New box with the given initial value.
	 *
	 * @param value
	 * 		Initial value.
	 */
	public DoubleBox(double value) {
		set(value);
	}

	/**
	 * @return Current boxed value.
	 */
	public double get() {
		return value;
	}

	/**
	 * @param value
	 * 		Value to set.
	 */
	public void set(double value) {
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
	public <R> R map(DoubleFunction<R> function) {
		return function.apply(get());
	}

	/**
	 * @param predicate
	 * 		Predicate to test value with.
	 *
	 * @return Predicate test results.
	 */
	public boolean test(DoublePredicate predicate) {
		return predicate.test(get());
	}

	/**
	 * @param other
	 * 		Some value.
	 *
	 * @return The passed value or the current value, whichever is lower.
	 */
	public double min(double other) {
		return Math.min(get(), other);
	}

	/**
	 * @param other
	 * 		Some value.
	 *
	 * @return The passed value or the current value, whichever is higher.
	 */
	public double max(double other) {
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
	public double clamp(double min, double max) {
		return get() > max ? max : Math.max(get(), min);
	}

	/**
	 * Clamps the value if it is higher than the passed value.
	 *
	 * @param min
	 * 		Some value.
	 */
	public void setMin(double min) {
		if (get() < min) set(min);
	}

	/**
	 * Clamps the value if it is higher than the passed value.
	 *
	 * @param max
	 * 		Some value.
	 */
	public void setMax(double max) {
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
	public void setClamp(double min, double max) {
		double oldValue = get();
		if (oldValue < min) set(min);
		else if (oldValue > max) set(max);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DoubleBox box = (DoubleBox) o;

		return value == box.value;
	}

	@Override
	public int hashCode() {
		long bits = Double.doubleToRawLongBits(value);
		return (int) (bits ^ (bits >>> 32));
	}
}
