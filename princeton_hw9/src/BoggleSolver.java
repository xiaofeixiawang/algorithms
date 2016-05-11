import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
	private Node root;

	private static class Node {
		private String word;
		private Node[] next = new Node[26];
	}

	public BoggleSolver(String[] dictionary) {
		for (int i = 0; i < dictionary.length; i++) {
			put(dictionary[i], dictionary[i]);
		}
	}

	public Iterable<String> getAllValidWords(BoggleBoard board) {
		Node next;
		Set<String> valid = new HashSet<String>();
		boolean[][] visited = new boolean[board.rows()][board.cols()];
		for (int i = 0; i < board.rows(); i++) {
			for (int j = 0; j < board.cols(); j++) {
				if (board.getLetter(i, j) == 'Q') {
					next = get(root, 'Q', 0);
					next = get(next, 'U', 0);
				} else {
					next = get(root, board.getLetter(i, j), 0);
				}
				if (next != null) {
					dfs(board, visited, valid, i, j, next);
				}
			}
		}
		return valid;
	}

	private void dfs(BoggleBoard board, boolean[][] visited, Set<String> valid, int i, int j, Node x) {
		Node next;
		visited[i][j] = true;
		for (int iP = i - 1; iP <= i + 1; iP++) {
			for (int jP = j - 1; jP <= j + 1; jP++) {
				if (iP == i && jP == j)
					continue;
				if (iP < board.rows() && jP < board.cols() && iP >= 0 && jP >= 0) {
					if (!visited[iP][jP]) {
						if (board.getLetter(iP, jP) == 'Q') {
							next = get(x, 'Q', 0);
							next = get(next, 'U', 0);
						} else {
							next = get(x, board.getLetter(iP, jP), 0);
						}
						if (next != null) {
							dfs(board, visited, valid, iP, jP, next);
						}
					}
				}
			}
		}
		visited[i][j] = false;
		if (x.word != null && x.word.length() > 2)
			valid.add(x.word);
	}

	public int scoreOf(String word) {
		if (word.length() > 2 && get(word) != null)
			switch (word.length()) {
			case 3:
				return 1;
			case 4:
				return 1;
			case 5:
				return 2;
			case 6:
				return 3;
			case 7:
				return 5;
			default:
				return 11;
			}
		return 0;
	}

	public static void main(String args[]) {
		In in = new In(args[0]);
		String[] dictionary = in.readAllStrings();
		BoggleSolver bs = new BoggleSolver(dictionary);
		BoggleBoard board = new BoggleBoard(args[1]);
		int score = 0;
		for (String word : bs.getAllValidWords(board)) {
			StdOut.println(word);
			score += bs.scoreOf(word);
		}
		StdOut.println("Score = " + score);
	}

	private String get(String key) {
		Node x = get(root, key, 0);
		if (x == null)
			return null;
		return x.word;
	}

	private Node get(Node x, String key, int d) {
		if (x == null)
			return null;
		if (d == key.length())
			return x;
		char c = key.charAt(d);
		return get(x.next[c - 65], key, d + 1);
	}

	private Node get(Node x, char key, int d) {
		if (x == null)
			return null;
		if (d == 1)
			return x;
		return get(x.next[key - 65], key, d + 1);
	}

	private void put(String key, String word) {
		root = put(root, key, word, 0);
	}

	private Node put(Node x, String key, String word, int d) {
		if (x == null)
			x = new Node();
		if (d == key.length()) {
			x.word = word;
			return x;
		}
		char c = key.charAt(d);
		x.next[c - 65] = put(x.next[c - 65], key, word, d + 1);
		return x;
	}
}
