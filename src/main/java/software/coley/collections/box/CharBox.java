package software.coley.collections.box;

import software.coley.collections.func.CharFunction;
import software.coley.collections.func.CharPredicate;

/**
 * {@code char} box.
 *
 * @author Matt Coley
 */
public class CharBox {
	private char value;

	/**
	 * New box with an initial value of 0.
	 */
	public CharBox() {}

	/**
	 * New box with the given initial value.
	 *
	 * @param value
	 * 		Initial value.
	 */
	public CharBox(char value) {
		set(value);
	}

	/**
	 * @return Current boxed value.
	 */
	public char get() {
		return value;
	}

	/**
	 * @param value
	 * 		Value to set.
	 */
	public void set(char value) {
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
	public <R> R map(CharFunction<R> function) {
		return function.apply(get());
	}

	/**
	 * @param predicate
	 * 		Predicate to test value with.
	 *
	 * @return Predicate test results.
	 */
	public boolean test(CharPredicate predicate) {
		return predicate.test(get());
	}

	/**
	 * @param other
	 * 		Some value.
	 *
	 * @return The passed value or the current value, whichever is lower.
	 */
	public char min(char other) {
		char c = get();
		return c < other ? c : other;
	}

	/**
	 * @param other
	 * 		Some value.
	 *
	 * @return The passed value or the current value, whichever is higher.
	 */
	public char max(char other) {
		char c = get();
		return c > other ? c : other;
	}

	/**
	 * @param min
	 * 		Some lower value.
	 * @param max
	 * 		some higher value.
	 *
	 * @return The current value clamped into the given range.
	 */
	public char clamp(char min, char max) {
		char c = get();
		return c < min ? min : c > max ? max : c;
	}

	/**
	 * Clamps the value if it is higher than the passed value.
	 *
	 * @param min
	 * 		Some value.
	 */
	public void setMin(char min) {
		if (get() < min) set(min);
	}

	/**
	 * Clamps the value if it is higher than the passed value.
	 *
	 * @param max
	 * 		Some value.
	 */
	public void setMax(char max) {
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
	public void setClamp(char min, char max) {
		char oldValue = get();
		if (oldValue < min) set(min);
		else if (oldValue > max) set(max);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CharBox box = (CharBox) o;

		return value == box.value;
	}

	@Override
	public int hashCode() {
		return value;
	}
}
