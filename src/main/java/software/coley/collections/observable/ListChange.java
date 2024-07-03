package software.coley.collections.observable;

import software.coley.collections.Lists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Describes a change in a {@link ObservableList}.
 *
 * @param <T>
 * 		List item type.
 *
 * @author Matt Coley
 */
public class ListChange<T> extends Change<T, List<T>> {
	private final int start;
	private final int end;

	/**
	 * @param added
	 * 		List of added items.
	 * @param removed
	 * 		List of removed items.
	 * @param start
	 * 		Start index in the {@link ObservableList} of the change.
	 * @param end
	 * 		End index in the {@link ObservableList} of the change.
	 */
	public ListChange(@Nonnull List<T> added, @Nonnull List<T> removed, int start, int end) {
		super(added, removed);
		this.start = start;
		this.end = end;
	}

	/**
	 * @param added
	 * 		Item added.
	 * @param from
	 * 		Index added at.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Change of the single addition.
	 */
	@Nonnull
	public static <T> ListChange<T> addition(@Nullable T added, int from) {
		return addition(Lists.of(added), from);
	}

	/**
	 * @param added
	 * 		Items added.
	 * @param from
	 * 		Index added at.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Change of the multiple additions.
	 */
	@Nonnull
	public static <T> ListChange<T> addition(@Nonnull List<T> added, int from) {
		return new ListChange<>(added, Collections.emptyList(), from, from + added.size());
	}

	/**
	 * @param removed
	 * 		Item removed.
	 * @param from
	 * 		Index removed at.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Change of the single removal.
	 */
	@Nonnull
	public static <T> ListChange<T> removal(@Nullable T removed, int from) {
		return removal(Lists.of(removed), from);
	}

	/**
	 * @param removed
	 * 		Items removed.
	 * @param from
	 * 		Index removed at.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Change of the multiple removals.
	 */
	@Nonnull
	public static <T> ListChange<T> removal(@Nonnull List<T> removed, int from) {
		return new ListChange<>(Collections.emptyList(), removed, from, from + removed.size());
	}

	/**
	 * @return Start index in the {@link ObservableList} of the change.
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return End index in the {@link ObservableList} of the change.
	 */
	public int getEnd() {
		return end;
	}
}
