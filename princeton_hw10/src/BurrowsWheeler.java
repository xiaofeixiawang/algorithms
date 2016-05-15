import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	public static void encode() {
		String input = BinaryStdIn.readString();
		CircularSuffixArray csa = new CircularSuffixArray(input);
		for (int i = 0; i < input.length(); i++) {
			if (csa.index(i) == 0) {
				BinaryStdOut.write(i);
				break;
			}
		}
		for (int i = 0; i < input.length(); i++) {
			int index = csa.index(i);
			BinaryStdOut.write(input.charAt((index - 1 + input.length()) % input.length()), 8);
		}
		BinaryStdOut.close();
	}

	public static void decode() {
		int first = BinaryStdIn.readInt();
		char[] t = BinaryStdIn.readString().toCharArray();
		int[] next = new int[t.length];
		Map<Character, Queue<Integer>> pos = new HashMap<Character, Queue<Integer>>();
		for (int i = 0; i < t.length; i++) {
			if (!pos.containsKey(t[i])) {
				pos.put(t[i], new LinkedList<Integer>());
			}
			pos.get(t[i]).add(i);
		}
		Arrays.sort(t);
		for (int i = 0; i < t.length; i++) {
			next[i] = pos.get(t[i]).remove();
		}
		for (int i = 0; i < t.length; i++) {
			BinaryStdOut.write(t[first]);
			first = next[first];
		}
		BinaryStdOut.close();
	}

	public static void main(String[] args) {
		if (args[0].equals("-"))
			encode();
		if (args[0].equals("+"))
			decode();
	}
}
