package software.coley.collections.tuple;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Simple pair with some utility methods.
 *
 * @param <L>
 * 		Left type.
 * @param <R>
 * 		Right type.
 *
 * @author Matt Coley
 */
public class Pair<L, R> {
	private final L left;
	private final R right;

	/**
	 * @param left
	 * 		Left value.
	 * @param right
	 * 		Right value.
	 */
	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * @param newLeft
	 * 		New left value.
	 *
	 * @return Pair with new left value.
	 */
	public Pair<L, R> withLeft(L newLeft) {
		return new Pair<>(newLeft, right);
	}

	/**
	 * @param newRight
	 * 		New right value.
	 *
	 * @return Pair with new right value.
	 */
	public Pair<L, R> withRight(R newRight) {
		return new Pair<>(left, newRight);
	}

	/**
	 * @return Pair with left and right values swapped.
	 */
	public Pair<R, L> flip() {
		return new Pair<>(right, left);
	}

	/**
	 * @param mapper
	 * 		Mapper to convert the left value to the new type.
	 * @param <LM>
	 * 		Mapped left type.
	 *
	 * @return Pair with mapped left value.
	 */
	public <LM> Pair<LM, R> mapLeft(Function<L, LM> mapper) {
		return new Pair<>(mapper.apply(left), right);
	}

	/**
	 * @param mapper
	 * 		Mapper to convert the right value to the new type.
	 * @param <RM>
	 * 		Mapped right type.
	 *
	 * @return Pair with mapped right value.
	 */
	public <RM> Pair<L, RM> mapRight(Function<R, RM> mapper) {
		return new Pair<>(left, mapper.apply(right));
	}

	/**
	 * @param leftMapper
	 * 		Mapper to convert the left value to the new type.
	 * @param rightMapper
	 * 		Mapper to convert the right value to the new type.
	 * @param <LM>
	 * 		Mapped left type.
	 * @param <RM>
	 * 		Mapped right type.
	 *
	 * @return Pair with mapped values.
	 */
	public <LM, RM> Pair<LM, RM> mapBoth(Function<L, LM> leftMapper, Function<R, RM> rightMapper) {
		return new Pair<>(leftMapper.apply(left), rightMapper.apply(right));
	}

	/**
	 * @param map
	 * 		Map to check in.
	 *
	 * @return {@code true} if the {@link Map#get(Object)} of the {@link #getLeft()} is {@link #getRight()}.
	 */
	public boolean mapContainsKeyValue(Map<L, R> map) {
		if (map.containsKey(left))
			return Objects.equals(map.get(left), right);
		return false;
	}

	/**
	 * @return {@code true} when {@link #getLeft()} is non-null.
	 */
	public boolean hasLeft() {
		return left != null;
	}

	/**
	 * @return {@code true} when {@link #getRight()} is non-null.
	 */
	public boolean hasRight() {
		return right != null;
	}

	/**
	 * @return Left value.
	 */
	public L getLeft() {
		return left;
	}

	/**
	 * @return Right value.
	 */
	public R getRight() {
		return right;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pair<?, ?> pair = (Pair<?, ?>) o;
		return Objects.equals(left, pair.left) &&
				Objects.equals(right, pair.right);
	}

	@Override
	public int hashCode() {
		int result = left != null ? left.hashCode() : 0;
		result = 31 * result + (right != null ? right.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Pair{" +
				"left=" + left +
				", right=" + right +
				'}';
	}
}
