import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
	public static void encode() {
		List<Character> list = new LinkedList<Character>();
		for (int i = 0; i < 256; i++) {
			list.add((char) i);
		}
		while (!BinaryStdIn.isEmpty()) {
			char c = BinaryStdIn.readChar();
			Iterator<Character> iter = list.iterator();
			for (int i = 0; i < 256; i++) {
				char cur = iter.next();
				if (cur == c) {
					BinaryStdOut.write(i, 8);
					list.remove(i);
					list.add(0, cur);
					break;
				}
			}
		}
		BinaryStdOut.close();
	}

	public static void decode() {
		List<Character> list = new LinkedList<Character>();
		for (int i = 0; i < 256; i++) {
			list.add((char) i);
		}
		while (!BinaryStdIn.isEmpty()) {
			int n = BinaryStdIn.readChar();
			BinaryStdOut.write(list.get(n), 8);
			char cur = list.get(n);
			list.remove(n);
			list.add(0, cur);
		}
		BinaryStdOut.close();
	}

	public static void main(String[] args) {
		if (args[0].equals("-"))
			MoveToFront.encode();
		if (args[0].equals("+"))
			MoveToFront.decode();
	}
}
