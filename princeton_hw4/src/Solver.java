import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private Stack<Board> trace = new Stack<Board>();

	private class SearchNode implements Comparable<SearchNode> {
		private int moves;
		private Board board;
		private SearchNode previous;
		private int priority;

		public SearchNode(Board board, int moves, SearchNode previous) {
			this.board = board;
			this.moves = moves;
			this.previous = previous;
			priority = moves + this.board.manhattan();
		}

		public int compareTo(SearchNode that) {
			if (priority < that.priority)
				return -1;
			if (priority > that.priority)
				return 1;
			return 0;
		}
	}

	public Solver(Board initial) {
		MinPQ<SearchNode> que = new MinPQ<SearchNode>();
		MinPQ<SearchNode> queTwin = new MinPQ<SearchNode>();
		SearchNode node = new SearchNode(initial, 0, null);
		SearchNode nodeTwin = new SearchNode(initial.twin(), 0, null);
		que.insert(node);
		queTwin.insert(nodeTwin);
		while (!node.board.isGoal() && !nodeTwin.board.isGoal()) {
			node = que.delMin();
			nodeTwin = queTwin.delMin();
			for (Board neighbor : node.board.neighbors()) {
				if (node.previous != null && node.previous.board.equals(neighbor))
					continue;
				que.insert(new SearchNode(neighbor, node.moves + 1, node));
			}
			for (Board neighbor : nodeTwin.board.neighbors()) {
				if (nodeTwin.previous != null && nodeTwin.previous.board.equals(neighbor))
					continue;
				queTwin.insert(new SearchNode(neighbor, nodeTwin.moves + 1, nodeTwin));
			}
		}
		if (node.board.isGoal()) {
			trace.push(node.board);
			while (node.previous != null) {
				node = node.previous;
				trace.push(node.board);
			}
		}
	}

	public boolean isSolvable() {
		return trace.size() != 0;
	}

	public int moves() {
		return trace.size() - 1;
	}

	public Iterable<Board> solution() {
		return trace.size() == 0 ? null : trace;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);
		Solver solver = new Solver(initial);
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves= " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
