package software.coley.collections.func;

/**
 * Predicate taking in a {@code char}.
 *
 * @author Matt Coley
 */
public interface CharPredicate {
	/**
	 * @param value
	 * 		Test input.
	 *
	 * @return Test result.
	 */
	boolean test(char value);
}
