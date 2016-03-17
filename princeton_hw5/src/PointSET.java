
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
	private final SET<Point2D> points;

	public PointSET() {
		points = new SET<Point2D>();
	}

	public boolean isEmpty() {
		return points.isEmpty();
	}

	public int size() {
		return points.size();
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		if (!points.contains(p))
			points.add(p);
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		return points.contains(p);
	}

	public void draw() {
		for (Point2D point : points)
			point.draw();
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new NullPointerException();
		Stack<Point2D> res = new Stack<Point2D>();
		for (Point2D point : points) {
			if (rect.contains(point))
				res.push(point);
		}
		return res;
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		if (isEmpty())
			return null;
		Point2D res = null;
		double dis = Double.MAX_VALUE;
		for (Point2D point : points) {
			if (p.distanceSquaredTo(point) < dis) {
				dis = p.distanceSquaredTo(point);
				res = point;
			}
		}
		return res;
	}
}
