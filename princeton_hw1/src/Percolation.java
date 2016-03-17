
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF grid, full;
	private boolean[] isOpen;
	private int lines;

	public Percolation(int N) {
		if (N < 1)
			throw new IllegalArgumentException();
		lines = N;
		grid = new WeightedQuickUnionUF(N * N + 2);
		full = new WeightedQuickUnionUF(N * N + 2);
		isOpen = new boolean[N * N + 2];
		isOpen[0] = true;
		isOpen[N * N + 1] = true;
		for (int i = 1; i <= N; i++) {
			grid.union(0, i);
			full.union(0, i);
			grid.union(N * N + 1, N * N + 1 - i);
		}
	}

	private boolean check(int i, int j) {
		if (i < 1 || j < 1 || i > lines || j > lines)
			return false;
		return true;
	}

	public void open(int i, int j) {
		if (!check(i, j))
			throw new IndexOutOfBoundsException();
		if (!isOpen(i, j)) {
			isOpen[(i - 1) * lines + j] = true;
			if (i > 1 && isOpen(i - 1, j)) {
				grid.union((i - 1) * lines + j, (i - 2) * lines + j);
				full.union((i - 1) * lines + j, (i - 2) * lines + j);
			}
			if (i < lines && isOpen(i + 1, j)) {
				grid.union((i - 1) * lines + j, i * lines + j);
				full.union((i - 1) * lines + j, i * lines + j);
			}
			if (j > 1 && isOpen(i, j - 1)) {
				grid.union((i - 1) * lines + j, (i - 1) * lines + j - 1);
				full.union((i - 1) * lines + j, (i - 1) * lines + j - 1);
			}
			if (j < lines && isOpen(i, j + 1)) {
				grid.union((i - 1) * lines + j, (i - 1) * lines + j + 1);
				full.union((i - 1) * lines + j, (i - 1) * lines + j + 1);
			}
		}
	}

	public boolean isOpen(int i, int j) {
		if (!check(i, j))
			throw new IndexOutOfBoundsException();
		return isOpen[(i - 1) * lines + j];
	}

	public boolean isFull(int i, int j) {
		if (!check(i, j))
			throw new IndexOutOfBoundsException();
		if (!isOpen(i, j))
			return false;
		return full.connected(0, (i - 1) * lines + j);
	}

	public boolean percolates() {
		if (lines == 1)
			return isOpen(1, 1);
		return grid.connected(0, lines * lines + 1);
	}

	public static void main(String[] args) {
		Percolation percolation = new Percolation(1);
		StdOut.println(percolation.percolates());
		percolation.open(1, 1);
		StdOut.println(percolation.percolates());
		Percolation percolation2 = new Percolation(2);
		StdOut.println(percolation2.percolates());
		percolation2.open(1, 1);
		StdOut.println(percolation2.percolates());
		percolation2.open(2, 1);
		StdOut.println(percolation2.percolates());
	}
}
