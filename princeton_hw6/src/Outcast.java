import java.util.*;

import edu.princeton.cs.algs4.*;

public class Outcast {
	private final WordNet wordnet;

	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}

	public String outcast(String[] nouns) {
		String res = "";
		int max = 0;
		for (String noun : nouns) {
			int dis = 0;
			for (String str : nouns) {
				dis += wordnet.distance(noun, str);
			}
			if (dis > max) {
				max = dis;
				res = noun;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
			In in = new In(args[t]);
			String[] nouns = in.readAllStrings();
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}
}
