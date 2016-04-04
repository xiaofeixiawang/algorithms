import java.awt.*;
import java.util.*;
import edu.princeton.cs.algs4.*;

public class SAP {
	private final Digraph graph;

	public SAP(Digraph G) {
		if (G == null)
			throw new NullPointerException();
		graph = new Digraph(G);
	}

	public int length(int v, int w) {
		if (v < 0 || v >= graph.V() || w < 0 || w >= graph.V())
			throw new IndexOutOfBoundsException();
		return length(Arrays.asList(v), Arrays.asList(w));
	}

	public int ancestor(int v, int w) {
		if (v < 0 || v >= graph.V() || w < 0 || w >= graph.V())
			throw new IndexOutOfBoundsException();
		return ancestor(Arrays.asList(v), Arrays.asList(w));
	}

	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		for (Integer i : v) {
			if (i < 0 || i >= graph.V())
				throw new IndexOutOfBoundsException();
		}
		for (Integer i : w) {
			if (i < 0 || i >= graph.V())
				throw new IndexOutOfBoundsException();
		}
		if (v == null || w == null)
			throw new NullPointerException();
		return new DBFS(graph, v, w).length();
	}

	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		for (Integer i : v) {
			if (i < 0 || i >= graph.V())
				throw new IndexOutOfBoundsException();
		}
		for (Integer i : w) {
			if (i < 0 || i >= graph.V())
				throw new IndexOutOfBoundsException();
		}
		if (v == null || w == null)
			throw new NullPointerException();
		return new DBFS(graph, v, w).ancestor();
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}
}

class DBFS {
	private final BreadthFirstDirectedPaths v, w;
	private int ancestor = -1;
	private int l = -1;

	public DBFS(Digraph g, Iterable<Integer> v1, Iterable<Integer> w1) {
		this.v = new BreadthFirstDirectedPaths(g, v1);
		this.w = new BreadthFirstDirectedPaths(g, w1);
		for (int a = 0; a < g.V(); a++) {
			if (v.hasPathTo(a) && w.hasPathTo(a) && (l == -1 || l > v.distTo(a) + w.distTo(a))) {
				l = v.distTo(a) + w.distTo(a);
				ancestor = a;
			}
		}
	}

	public int length() {
		return l;
	}

	public int ancestor() {
		return ancestor;
	}
}