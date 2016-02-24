import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;

public class Board {
	private int[][] blocks;
	private int N;

	public Board(int[][] blocks) {
		N = blocks.length;
		this.blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			this.blocks[i] = Arrays.copyOf(blocks[i], N);
	}

	public int dimension() {
		return N;
	}

	public int hamming() {
		int num = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] != 0 && blocks[i][j] != i * N + j + 1)
					num++;
			}
		}
		return num;
	}

	private int dis(int i, int j) {
		int i1 = (blocks[i][j] - 1) / N;
		int j1 = (blocks[i][j] - 1) % N;
		return Math.abs(i1 - i) + Math.abs(j1 - j);
	}

	public int manhattan() {
		int step = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] != 0 && blocks[i][j] != i * N + j + 1)
					step += dis(i, j);
			}
		}
		return step;
	}

	public boolean isGoal() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] != 0 && blocks[i][j] != i * N + j + 1)
					return false;
			}
		}
		return true;
	}

	public boolean equals(Object y) {
		if (y == this)
			return true;
		if (!(y instanceof Board))
			return false;
		Board that = (Board) y;
		if (that.N != this.N)
			return false;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (that.blocks[i][j] != this.blocks[i][j])
					return false;
			}
		}
		return true;
	}

	public Board twin() {
		int[][] twin = new int[N][N];
		for (int i = 0; i < N; i++) {
			twin[i] = Arrays.copyOf(blocks[i], N);
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N - 1; j++) {
				if (twin[i][j] != 0 && twin[i][j + 1] != 0) {
					int tmp = twin[i][j];
					twin[i][j] = twin[i][j + 1];
					twin[i][j + 1] = tmp;
					return new Board(twin);
				}
			}
		}
		return new Board(twin);
	}

	public Iterable<Board> neighbors() {
		Stack<Board> stack = new Stack<Board>();
		int x = 0, y = 0;
		boolean found = false;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0) {
					x = i;
					y = j;
					found = true;
					break;
				}
			}
			if (found)
				break;
		}
		if (x > 0)
			stack.push(neighbors(x - 1, y, x, y));
		if (y > 0)
			stack.push(neighbors(x, y - 1, x, y));
		if (x < N - 1)
			stack.push(neighbors(x + 1, y, x, y));
		if (y < N - 1)
			stack.push(neighbors(x, y + 1, x, y));
		return stack;
	}

	private Board neighbors(int curx, int cury, int prex, int prey) {
		int[][] neighborBlock = new int[N][N];
		for (int i = 0; i < N; i++) {
			neighborBlock[i] = Arrays.copyOf(blocks[i], N);
		}
		neighborBlock[prex][prey] = neighborBlock[curx][cury];
		neighborBlock[curx][cury] = 0;
		return new Board(neighborBlock);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(blocks[i][j] + " ");
			}
			s.append("\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {

	}
}
