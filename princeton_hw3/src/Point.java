import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
	private final int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw() {
		StdDraw.point(x, y);
	}

	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public int compareTo(Point that) {
		if(this.y==that.y)return this.x-that.x;
		return this.y-that.y;
	}

	public double slopeTo(Point p1) {
		if (this.y == p1.y && this.x == p1.x)
			return Double.NEGATIVE_INFINITY;
		if (this.y == p1.y)
			return +0;
		if (this.x == p1.x)
			return Double.POSITIVE_INFINITY;
		return (double) (p1.y - this.y) / (double) (p1.x - this.x);
	}

	public Comparator<Point> slopeOrder() {
		return new CompPoint();
	}

	private class CompPoint implements Comparator<Point> {
		public int compare(Point p1, Point p2) {
			if (slopeTo(p1) < slopeTo(p2))
				return -1;
			if (slopeTo(p1) > slopeTo(p2))
				return 1;
			return 0;
		}
	}
}
