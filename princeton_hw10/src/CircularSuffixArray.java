import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
	private String input;
	private Integer[] index;

	public CircularSuffixArray(String s) {
		if (s == null)
			throw new NullPointerException();
		input = s;
		index = new Integer[s.length()];
		for (int i = 0; i < index.length; i++) {
			index[i] = i;
		}
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				int l = input.length();
				for (int i = 0; i < l; i++) {
					char c1 = input.charAt((a + i) % l);
					char c2 = input.charAt((b + i) % l);
					if (c1 > c2)
						return 1;
					if (c1 < c2)
						return -1;
				}
				return 0;
			}
		});
	}

	public int length() {
		return input.length();
	}

	public int index(int i) {
		if (i < 0 || i >= length())
			throw new IndexOutOfBoundsException();
		return index[i];
	}

	public static void main(String[] args) {
		CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
		for (int i = 0; i < csa.length(); i++) {
			System.out.println(csa.index(i));
		}
	}
}
