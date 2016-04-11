import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
	private ArrayList<String> teams;
	private HashMap<String, Integer> team2id;
	private int[] w;
	private int[] l;
	private int[] r;
	private int[][] g;
	private int max = Integer.MIN_VALUE;
	private String leader;

	public BaseballElimination(String filename) {
		In in = new In(filename);
		int num = in.readInt();
		teams = new ArrayList<String>();
		team2id = new HashMap<String, Integer>();
		w = new int[num];
		l = new int[num];
		r = new int[num];
		g = new int[num][num];
		for (int i = 0; i < num; i++) {
			String team = in.readString();
			teams.add(team);
			team2id.put(team, i);
			w[i] = in.readInt();
			if (max < w[i]) {
				max = w[i];
				leader = team;
			}
			l[i] = in.readInt();
			r[i] = in.readInt();
			for (int j = 0; j < num; j++) {
				g[i][j] = in.readInt();
			}
		}

	}

	public int numberOfTeams() {
		return teams.size();
	}

	public Iterable<String> teams() {
		return teams;
	}

	public int wins(String team) {
		if (!team2id.containsKey(team))
			throw new IllegalArgumentException();
		return w[team2id.get(team)];
	}

	public int losses(String team) {
		if (!team2id.containsKey(team))
			throw new IllegalArgumentException();
		return l[team2id.get(team)];
	}

	public int remaining(String team) {
		if (!team2id.containsKey(team))
			throw new IllegalArgumentException();
		return r[team2id.get(team)];
	}

	public int against(String team1, String team2) {
		if (!team2id.containsKey(team1) || !team2id.containsKey(team2))
			throw new IllegalArgumentException();
		return g[team2id.get(team1)][team2id.get(team2)];
	}

	private boolean trivialEliminated(int id) {
		for (int i = 0; i < teams.size(); i++) {
			if (i != id && w[id] + r[id] < w[i])
				return true;
		}
		return false;
	}

	public boolean isEliminated(String team) {
		if (!team2id.containsKey(team))
			throw new IllegalArgumentException();
		if (trivialEliminated(team2id.get(team)))
			return true;
		Graph graph = new Graph(team2id.get(team));
		for (FlowEdge edge : graph.network.adj(teams.size())) {
			if (edge.flow() < edge.capacity())
				return true;
		}
		return false;
	}

	public Iterable<String> certificateOfElimination(String team) {
		if (!team2id.containsKey(team))
			throw new IllegalArgumentException();
		Set<String> res = new HashSet<String>();
		if (trivialEliminated(team2id.get(team))) {
			res.add(leader);
			return res;
		}
		Graph graph = new Graph(team2id.get(team));
		for (String t : teams) {
			if (graph.ff.inCut(team2id.get(t)))
				res.add(t);
		}
		if (res.size() == 0)
			return null;
		return res;
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(args[0]);
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team)) {
					StdOut.print(t + " ");
				}
				StdOut.println("}");
			} else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}

	private class Graph {
		private FlowNetwork network;
		private FordFulkerson ff;

		public Graph(int id) {
			int n = teams.size();
			int source = n;
			int sink = n + 1;
			int vs = n + 2;
			Set<FlowEdge> edges = new HashSet<>();
			for (int i = 0; i < n; i++) {
				if (i == id)
					continue;
				for (int j = 0; j < i; j++) {
					if (j == id || g[i][j] == 0)
						continue;
					edges.add(new FlowEdge(source, vs, g[i][j]));
					edges.add(new FlowEdge(vs, i, Integer.MAX_VALUE));
					edges.add(new FlowEdge(vs, j, Integer.MAX_VALUE));
					vs++;
				}
				edges.add(new FlowEdge(i, sink, w[id] + r[id] - w[i]));
			}
			network = new FlowNetwork(vs);
			for (FlowEdge edge : edges) {
				network.addEdge(edge);
			}
			ff = new FordFulkerson(network, source, sink);
		}
	}
}
