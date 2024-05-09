package software.coley.collections.box;

import software.coley.collections.func.FloatFunction;
import software.coley.collections.func.FloatPredicate;

/**
 * {@code float} box.
 *
 * @author Matt Coley
 */
public class FloatBox {
	private float value;

	/**
	 * New box with an initial value of 0.
	 */
	public FloatBox() {}

	/**
	 * New box with the given initial value.
	 *
	 * @param value
	 * 		Initial value.
	 */
	public FloatBox(float value) {
		set(value);
	}

	/**
	 * @return Current boxed value.
	 */
	public float get() {
		return value;
	}

	/**
	 * @param value
	 * 		Value to set.
	 */
	public void set(float value) {
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
	public <R> R map(FloatFunction<R> function) {
		return function.apply(get());
	}

	/**
	 * @param predicate
	 * 		Predicate to test value with.
	 *
	 * @return Predicate test results.
	 */
	public boolean test(FloatPredicate predicate) {
		return predicate.test(get());
	}

	/**
	 * @param other
	 * 		Some value.
	 *
	 * @return The passed value or the current value, whichever is lower.
	 */
	public float min(float other) {
		return Math.min(get(), other);
	}

	/**
	 * @param other
	 * 		Some value.
	 *
	 * @return The passed value or the current value, whichever is higher.
	 */
	public float max(float other) {
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
	public float clamp(float min, float max) {
		return get() > max ? max : Math.max(get(), min);
	}

	/**
	 * Clamps the value if it is higher than the passed value.
	 *
	 * @param min
	 * 		Some value.
	 */
	public void setMin(float min) {
		if (get() < min) set(min);
	}

	/**
	 * Clamps the value if it is higher than the passed value.
	 *
	 * @param max
	 * 		Some value.
	 */
	public void setMax(float max) {
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
	public void setClamp(float min, float max) {
		float oldValue = get();
		if (oldValue < min) set(min);
		else if (oldValue > max) set(max);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FloatBox box = (FloatBox) o;

		return value == box.value;
	}

	@Override
	public int hashCode() {
		return Float.floatToRawIntBits(value);
	}
}
