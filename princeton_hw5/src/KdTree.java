import java.util.Comparator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private Node root;
	private int num;

	public KdTree() {
		root = null;
		num = 0;
	}

	public boolean isEmpty() {
		return num == 0;
	}

	public int size() {
		return num;
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		root = insert(root, p, true);
	}

	private Node insert(Node node, Point2D p, boolean vertical) {
		if (node == null) {
			num++;
			return new Node(p, vertical, null, null);
		}
		if (node.point.equals(p))
			return node;
		Comparator<Point2D> com = Point2D.Y_ORDER;
		if (vertical)
			com = Point2D.X_ORDER;
		int ans = com.compare(p, node.point);
		if (ans >= 0)
			node.right = insert(node.right, p, !vertical);
		else
			node.left = insert(node.left, p, !vertical);
		return node;
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		return contains(root, p, true);
	}

	private boolean contains(Node node, Point2D p, boolean vertical) {
		if (node == null)
			return false;
		if (node.point.equals(p))
			return true;
		Comparator<Point2D> com = Point2D.Y_ORDER;
		if (vertical)
			com = Point2D.X_ORDER;
		int ans = com.compare(p, node.point);
		if (ans >= 0)
			return contains(node.right, p, !vertical);
		else
			return contains(node.left, p, !vertical);
	}

	public void draw() {
		draw(root, new RectHV(0, 0, 1, 1));
	}

	private void draw(Node node, RectHV rect) {
		if (node == null)
			return;
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		node.point.draw();
		StdDraw.setPenRadius();
		StdDraw.setPenColor(StdDraw.RED);
		Point2D first = new Point2D(node.point.x(), rect.ymin());
		Point2D second = new Point2D(node.point.x(), rect.ymax());
		if (!node.isVertical) {
			StdDraw.setPenColor(StdDraw.BLUE);
			first = new Point2D(rect.xmin(), node.point.y());
			second = new Point2D(rect.xmax(), node.point.y());
		}
		first.drawTo(second);
		draw(node.left, new RectHV(rect.xmin(), rect.ymin(), second.x(), second.y()));
		draw(node.right, new RectHV(first.x(), first.y(), rect.xmax(), rect.ymax()));
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new NullPointerException();
		Stack<Point2D> res = new Stack<Point2D>();
		range(rect, res, root, true);
		return res;
	}

	private void range(RectHV rect, Stack<Point2D> res, Node node, boolean vertical) {
		if (node == null)
			return;
		if (rect.contains(node.point))
			res.push(node.point);
		double coord = node.point.x();
		double coordMin = rect.xmin();
		double coordMax = rect.xmax();
		if (!vertical) {
			coord = node.point.y();
			coordMin = rect.ymin();
			coordMax = rect.ymax();
		}
		if (coordMin < coord)
			range(rect, res, node.left, !vertical);
		if (coordMax >= coord)
			range(rect, res, node.right, !vertical);
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		Point2D[] res = new Point2D[1];
		if (root == null)
			return null;
		res[0] = root.point;
		nearest(root, res, p, new RectHV(0, 0, 1, 1));
		return res[0];
	}

	private void nearest(Node node, Point2D[] res, Point2D p, RectHV rect) {
		if (node == null)
			return;
		if (node.point.distanceSquaredTo(p) < res[0].distanceSquaredTo(p))
			res[0] = node.point;
		Node firstNode = node.left;
		Node secondNode = node.right;
		RectHV first, second;
		if (node.isVertical) {
			first = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
			second = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
			if (p.x() >= node.point.x()) {
				RectHV tmp = first;
				first = second;
				second = tmp;
				firstNode = node.right;
				secondNode = node.left;
			}
		} else {
			first = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
			second = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
			if (p.y() >= node.point.y()) {
				RectHV tmp = first;
				first = second;
				second = tmp;
				firstNode = node.right;
				secondNode = node.left;
			}
		}
		nearest(firstNode, res, p, first);
		if (second.distanceSquaredTo(p) < res[0].distanceSquaredTo(p)) {
			nearest(secondNode, res, p, second);
		}
	}

	private class Node {
		private Point2D point;
		private boolean isVertical;
		private Node left;
		private Node right;

		public Node(Point2D point, boolean isVertical, Node left, Node right) {
			this.point = point;
			this.isVertical = isVertical;
			this.left = left;
			this.right = right;
		}
	}
}
