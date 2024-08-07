package software.coley.collections.func;

/**
 * Function mapping an object to an {@code int}.
 *
 * @param <T>
 * 		Object input type.
 *
 * @author Matt Coley
 */
public interface ObjToIntFunction<T> {
	/**
	 * @param value
	 * 		Input value.
	 *
	 * @return Mapped value.
	 */
	int map(T value);
}
