package software.coley.collections.func;

/**
 * Function that takes in a {@code char}.
 *
 * @param <R>
 * 		Mapping return type.
 *
 * @author Matt Coley
 */
public interface CharFunction<R> {
	/**
	 * @param value
	 * 		Input value.
	 *
	 * @return Mapped value.
	 */
	R apply(char value);
}
