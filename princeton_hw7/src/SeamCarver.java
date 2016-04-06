import java.awt.*;
import java.util.ArrayList;

import edu.princeton.cs.algs4.*;

public class SeamCarver {
	private Picture pic;
	private double[][] energies;

	public SeamCarver(Picture picture) {
		if (picture == null)
			throw new NullPointerException();
		pic = new Picture(picture);
		energies = new double[pic.height()][pic.width()];
	}

	public Picture picture() {
		return new Picture(pic);
	}

	public int width() {
		return pic.width();
	}

	public int height() {
		return pic.height();
	}

	public double energy(int x, int y) {
		if (x < 0 || x >= pic.width() || y < 0 || y >= pic.height())
			throw new IndexOutOfBoundsException();
		if (x == 0 || x == pic.width() - 1 || y == 0 || y == pic.height() - 1)
			return 1000.0;
		Color left = pic.get(x - 1, y);
		Color right = pic.get(x + 1, y);
		Color top = pic.get(x, y - 1);
		Color down = pic.get(x, y + 1);
		double deltaX = Math.pow((double) (right.getRed() - left.getRed()), 2)
				+ Math.pow((double) (right.getGreen() - left.getGreen()), 2)
				+ Math.pow((double) (right.getBlue() - left.getBlue()), 2);
		double deltaY = Math.pow((double) (down.getRed() - top.getRed()), 2)
				+ Math.pow((double) (down.getGreen() - top.getGreen()), 2)
				+ Math.pow((double) (down.getBlue() - top.getBlue()), 2);
		return Math.sqrt(deltaX + deltaY);
	}

	private void calenergy() {
		for (int i = 0; i < pic.height(); i++) {
			for (int j = 0; j < pic.width(); j++) {
				energies[i][j] = energy(j, i);
			}
		}
	}

	private void dfs(int x, int y, int[][] steps, double[][] sumEnergy) {
		if (x == pic.width() - 1) {
			sumEnergy[y][x] = energies[y][x];
			steps[y][x] = -1;
			return;
		}
		double min = Double.MAX_VALUE;
		int BestMv = -1;
		for (int mv = -1; mv <= 1; mv++) {
			if (y + mv < 0 || y + mv >= pic.height())
				continue;
			if (steps[y + mv][x + 1] == 0) {
				dfs(x + 1, y + mv, steps, sumEnergy);
			}
			if (sumEnergy[y + mv][x + 1] < min) {
				min = sumEnergy[y + mv][x + 1];
				BestMv = y + mv;
			}
		}
		sumEnergy[y][x] = min + energies[y][x];
		steps[y][x] = BestMv;
	}

	private void dfs2(int x, int y, int[][] steps, double[][] sumEnergy) {
		if (y == pic.height() - 1) {
			sumEnergy[y][x] = energies[y][x];
			steps[y][x] = -1;
			return;
		}
		double min = Double.MAX_VALUE;
		int BestMv = -1;
		for (int mv = -1; mv <= 1; mv++) {
			if (x + mv < 0 || x + mv >= pic.width())
				continue;
			if (steps[y + 1][x + mv] == 0) {
				dfs2(x + mv, y + 1, steps, sumEnergy);
			}
			if (sumEnergy[y + 1][x + mv] < min) {
				min = sumEnergy[y + 1][x + mv];
				BestMv = x + mv;
			}
		}
		sumEnergy[y][x] = min + energies[y][x];
		steps[y][x] = BestMv;
	}

	public int[] findHorizontalSeam() {
		calenergy();
		double[][] sumEnergy = new double[pic.height()][pic.width()];
		int[][] steps = new int[pic.height()][pic.width()];
		for (int i = 0; i < pic.height(); i++) {
			dfs(0, i, steps, sumEnergy);
		}
		double sum = Double.MAX_VALUE;
		int[] path = new int[pic.width()];
		for (int i = 0; i < pic.height(); i++) {
			if (sumEnergy[i][0] < sum) {
				sum = sumEnergy[i][0];
				path[0] = i;
			}
		}
		for (int i = 1; i < pic.width(); i++) {
			path[i] = steps[path[i - 1]][i - 1];
		}
		return path;
	}

	public int[] findVerticalSeam() {
		calenergy();
		double[][] sumEnergy = new double[pic.height()][pic.width()];
		int[][] steps = new int[pic.height()][pic.width()];
		for (int i = 0; i < pic.width(); i++) {
			dfs2(i, 0, steps, sumEnergy);
		}
		double sum = Double.MAX_VALUE;
		int[] path = new int[pic.height()];
		for (int i = 0; i < pic.width(); i++) {
			if (sumEnergy[0][i] < sum) {
				sum = sumEnergy[0][i];
				path[0] = i;
			}
		}
		for (int i = 1; i < pic.height(); i++) {
			path[i] = steps[i - 1][path[i - 1]];
		}
		return path;
	}

	public void removeHorizontalSeam(int[] seam) {
		if (seam == null)
			throw new NullPointerException();
		if (seam.length != pic.width() || seam[0] < 0 || seam[0] >= pic.height())
			throw new IllegalArgumentException();
		for (int i = 1; i < seam.length; i++) {
			if (seam[i] < 0 || seam[i] >= pic.height())
				throw new IllegalArgumentException();
			if (Math.abs(seam[i] - seam[i - 1]) > 1)
				throw new IllegalArgumentException();
		}
		if (pic.height() <= 1)
			throw new IllegalArgumentException();
		Picture npic = new Picture(pic.width(), pic.height() - 1);
		for (int i = 0; i < pic.width(); i++) {
			for (int j = 0; j < pic.height(); j++) {
				if (j == seam[i])
					continue;
				int pt = j;
				if (pt > seam[i])
					pt--;
				npic.set(i, pt, pic.get(i, j));
			}
		}
		pic = npic;
	}

	public void removeVerticalSeam(int[] seam) {
		if (seam == null)
			throw new NullPointerException();
		if (seam.length != pic.height() || seam[0] < 0 || seam[0] >= pic.width())
			throw new IllegalArgumentException();
		for (int i = 1; i < seam.length; i++) {
			if (seam[i] < 0 || seam[i] >= pic.width())
				throw new IllegalArgumentException();
			if (Math.abs(seam[i] - seam[i - 1]) > 1)
				throw new IllegalArgumentException();
		}
		if (pic.width() <= 1)
			throw new IllegalArgumentException();
		Picture npic = new Picture(pic.width() - 1, pic.height());
		for (int i = 0; i < pic.height(); i++) {
			for (int j = 0; j < pic.width(); j++) {
				if (j == seam[i])
					continue;
				int pt = j;
				if (pt > seam[i])
					pt--;
				npic.set(pt, i, pic.get(j, i));
			}
		}
		pic = npic;
	}
}
