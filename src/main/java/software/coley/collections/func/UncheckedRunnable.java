package software.coley.collections.func;

import software.coley.collections.Unchecked;

/**
 * Its {@link Runnable} but can throw an exception.
 *
 * @author Matt Coley
 */
@FunctionalInterface
public interface UncheckedRunnable extends Runnable {
	@Override
	default void run() {
		try {
			uncheckedRun();
		} catch (Throwable t) {
			Unchecked.propagate(t);
		}
	}

	/**
	 * @throws Throwable
	 * 		Whenever.
	 */
	void uncheckedRun() throws Throwable;
}
