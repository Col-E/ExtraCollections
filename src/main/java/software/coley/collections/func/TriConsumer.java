package software.coley.collections.func;

/**
 * Generic consumer for 3 arguments.
 *
 * @param <A>
 * 		First parameter type.
 * @param <B>
 * 		Second parameter type.
 * @param <C>
 * 		Third parameter type.
 *
 * @author Matt Coley
 */
public interface TriConsumer<A, B, C> {
	/**
	 * @param a
	 * 		First arg.
	 * @param b
	 * 		Second arg.
	 * @param c
	 * 		Third arg.
	 */
	void accept(A a, B b, C c);
}