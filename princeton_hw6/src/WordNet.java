import java.util.*;
import edu.princeton.cs.algs4.*;

public class WordNet {
	private final SAP sap;
	private final HashMap<Integer, String> disc;
	private final HashMap<String, Bag<Integer>> noun2id;

	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null)
			throw new NullPointerException();
		disc = new HashMap<Integer, String>();
		noun2id = new HashMap<String, Bag<Integer>>();
		readDisc(synsets);
		Digraph digraph = readHyper(hypernyms);
		sap = new SAP(digraph);
		if (new DirectedCycle(digraph).hasCycle())
			throw new IllegalArgumentException();
	}

	private void readDisc(String synsets) {
		In in = new In(synsets);
		while (in.hasNextLine()) {
			String[] tokens = in.readLine().split(",");
			int id = Integer.parseInt(tokens[0]);
			disc.put(id, tokens[1]);
			for (String noun : tokens[1].split(" ")) {
				if (!noun2id.containsKey(noun)) {
					Bag<Integer> bag = new Bag<Integer>();
					bag.add(id);
					noun2id.put(noun, bag);
				} else {
					noun2id.get(noun).add(id);
				}
			}
		}
	}

	private Digraph readHyper(String hypernyms) {
		In in = new In(hypernyms);
		Digraph digraph = new Digraph(disc.size());
		int count = 0;
		while (in.hasNextLine()) {
			count++;
			String[] tokens = in.readLine().split(",");
			int id = Integer.parseInt(tokens[0]);
			for (int i = 1; i < tokens.length; i++) {
				digraph.addEdge(id, Integer.parseInt(tokens[i]));
			}
		}
		if (disc.size() - count > 1)
			throw new IllegalArgumentException();
		return digraph;
	}

	public Iterable<String> nouns() {
		return noun2id.keySet();
	}

	public boolean isNoun(String word) {
		if (word == null)
			throw new NullPointerException();
		return noun2id.containsKey(word);
	}

	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new NullPointerException();
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();
		return sap.length(noun2id.get(nounA), noun2id.get(nounB));
	}

	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new NullPointerException();
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();
		return disc.get(sap.ancestor(noun2id.get(nounA), noun2id.get(nounB)));
	}

	public static void main(String[] args) {

	}
}
