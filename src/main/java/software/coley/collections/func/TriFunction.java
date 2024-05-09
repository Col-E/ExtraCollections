package software.coley.collections.func;

/**
 * Generic function for 3 arguments.
 *
 * @param <A>
 * 		First parameter type.
 * @param <B>
 * 		Second parameter type.
 * @param <C>
 * 		Third parameter type.
 * @param <R>
 * 		Return type.
 *
 * @author Matt Coley
 */
interface TriFunction<A, B, C, R> {
	/**
	 * @param a
	 * 		First arg.
	 * @param b
	 * 		Second arg.
	 * @param c
	 * 		Third arg.
	 *
	 * @return Return value.
	 */
	R apply(A a, B b, C c);
}