import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private ArrayList<LineSegment> tmp = new ArrayList<LineSegment>();

	public BruteCollinearPoints(Point[] points) {
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
		for (int i = 0; i < pointscopy.length - 3; i++) {
			for (int j = i + 1; j < pointscopy.length - 2; j++) {
				for (int k = j + 1; k < pointscopy.length - 1; k++) {
					for (int l = k + 1; l < pointscopy.length; l++) {
						if (pointscopy[i].slopeTo(pointscopy[j]) == pointscopy[i].slopeTo(pointscopy[k])
								&& pointscopy[i].slopeTo(pointscopy[j]) == pointscopy[i].slopeTo(pointscopy[l])) {
							tmp.add(new LineSegment(pointscopy[i], pointscopy[l]));
						}
					}
				}
			}
		}
	}

	public int numberOfSegments() {
		return tmp.size();
	}

	public LineSegment[] segments() {
		return tmp.toArray(new LineSegment[tmp.size()]);
	}
}
