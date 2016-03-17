import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FastCollinearPoints {
	private HashMap<Double, List<Point>> foundSegments = new HashMap<>();
	private ArrayList<LineSegment> tmp = new ArrayList<LineSegment>();

	public FastCollinearPoints(Point[] points) {
		Point[] pointscopy = Arrays.copyOf(points, points.length);
		if (pointscopy == null)
			throw new NullPointerException();
		Arrays.sort(pointscopy);
		for (int i = 0; i < pointscopy.length; i++) {
			if (pointscopy[i] == null)
				throw new NullPointerException();
			if (i > 0 && pointscopy[i].compareTo(pointscopy[i - 1]) == 0)
				throw new IllegalArgumentException();
		}
		for (Point start : points) {
			Arrays.sort(pointscopy, start.slopeOrder());
			ArrayList<Point> slopePoints = new ArrayList<Point>();
			double preSlope = Double.NEGATIVE_INFINITY;
			double slope = 0;
			for (int i = 1; i < pointscopy.length; i++) {
				slope = start.slopeTo(pointscopy[i]);
				if (slope == preSlope)
					slopePoints.add(pointscopy[i]);
				else {
					if (slopePoints.size() >= 3) {
						slopePoints.add(start);
						addifnew(slopePoints, preSlope);
					}
					slopePoints.clear();
					slopePoints.add(pointscopy[i]);
				}
				preSlope = slope;
			}
			if (slopePoints.size() >= 3) {
				slopePoints.add(start);
				addifnew(slopePoints, preSlope);
			}
		}
	}

	private void addifnew(ArrayList<Point> slopePoints, double slope) {
		List<Point> endpoints = foundSegments.get(slope);
		Collections.sort(slopePoints);
		Point start = slopePoints.get(0);
		Point end = slopePoints.get(slopePoints.size() - 1);
		if (endpoints == null) {
			endpoints = new ArrayList<>();
			endpoints.add(end);
			foundSegments.put(slope, endpoints);
			tmp.add(new LineSegment(start, end));
		} else {
			for (Point cur : endpoints) {
				if (cur.compareTo(end) == 0)
					return;
			}
			endpoints.add(end);
			tmp.add(new LineSegment(start, end));
		}
	}

	public int numberOfSegments() {
		return tmp.size();
	}

	public LineSegment[] segments() {
		return tmp.toArray(new LineSegment[tmp.size()]);
	}
}
