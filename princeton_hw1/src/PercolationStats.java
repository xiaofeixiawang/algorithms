
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] thres;
	private int T;

	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0)
			throw new IllegalArgumentException();
		this.T = T;
		thres = new double[T];
		for (int k = 0; k < T; k++) {
			Percolation p = new Percolation(N);
			int open = 0;
			while (!p.percolates()) {
				int i = StdRandom.uniform(1, N + 1);
				int j = StdRandom.uniform(1, N + 1);
				if (!p.isOpen(i, j)) {
					p.open(i, j);
					open++;
				}
			}
			thres[k] = (double) open / (double) (N * N);
		}
	}

	public double mean() {
		return StdStats.mean(thres);
	}

	public double stddev() {
		return StdStats.stddev(thres);
	}

	public double confidenceLo() {
		return mean() - (1.96 * stddev() / Math.sqrt(T));
	}

	public double confidenceHi() {
		return mean() + (1.96 * stddev() / Math.sqrt(T));
	}

	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats stats = new PercolationStats(N, T);
		StdOut.println("mean = " + stats.mean());
		StdOut.println("standard deviation = " + stats.stddev());
		StdOut.println("95% confidence interval = " + stats.confidenceLo() + " , " + stats.confidenceHi());
	}
}
