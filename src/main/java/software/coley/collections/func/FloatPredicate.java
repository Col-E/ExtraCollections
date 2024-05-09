package software.coley.collections.func;

/**
 * Predicate taking in a {@code float}.
 *
 * @author Matt Coley
 */
public interface FloatPredicate {
	/**
	 * @param value
	 * 		Test input.
	 *
	 * @return Test result.
	 */
	boolean test(float value);
}
