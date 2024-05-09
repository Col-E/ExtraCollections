package software.coley.collections.func;

/**
 * Function that takes in a {@code float}.
 *
 * @param <R>
 * 		Mapping return type.
 *
 * @author Matt Coley
 */
public interface FloatFunction<R> {
	/**
	 * @param value
	 * 		Input value.
	 *
	 * @return Mapped value.
	 */
	R apply(float value);
}
