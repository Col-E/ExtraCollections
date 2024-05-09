package software.coley.collections.func;

/**
 * Function mapping an object to an {@code long}.
 * @param <T> Object input type.
 */
public interface ObjToLongFunction<T> {
	/**
	 * @param value Input value.
	 * @return Mapped value.
	 */
	long map(T value);
}
