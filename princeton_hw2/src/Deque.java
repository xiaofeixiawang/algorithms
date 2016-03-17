
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
	private class Node<Item> {
		private Item val;
		private Node<Item> prev, next;

		public Node(Item val) {
			this.val = val;
			this.prev = null;
			this.next = null;
		}
	}

	private Node<Item> head, tail;
	private int size;

	public Deque() {
		head = null;
		tail = null;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException();
		if (head == null) {
			head = new Node<Item>(item);
			tail = head;
		} else {
			Node<Item> tmp = new Node<Item>(item);
			head.prev = tmp;
			tmp.next = head;
			head = tmp;
		}
		size++;
	}

	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException();
		if (head == null) {
			head = new Node<Item>(item);
			tail = head;
		} else {
			Node<Item> tmp = new Node<Item>(item);
			tail.next = tmp;
			tmp.prev = tail;
			tail = tmp;
		}
		size++;
	}

	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		Item res = head.val;
		head = head.next;
		if (head == null)
			tail = null;
		else
			head.prev = null;
		size--;
		return res;
	}

	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		Item res = tail.val;
		tail = tail.prev;
		if (tail != null)
			tail.next = null;
		else
			head = null;
		size--;
		return res;
	}

	public Iterator<Item> iterator() {
		return new IterDeque<Item>(head);
	}

	private class IterDeque<Item> implements Iterator<Item> {
		private Node<Item> head;

		public IterDeque(Node<Item> head) {
			this.head = head;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public boolean hasNext() {
			return head != null;
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item res = head.val;
			head = head.next;
			return res;
		}
	}

	// public String toString() {
	// StringBuilder s = new StringBuilder();
	// Node<Item> tmp = head;
	// while (tmp != null) {
	// s.append(tmp.val + " ");
	// tmp = tmp.next;
	// }
	// return s.toString();
	// }

	public static void main(String[] args) {
		Deque<String> deque = new Deque<String>();
		deque.addFirst("1");
		StdOut.println("addfirst to string: " + deque.toString());
		deque.addFirst("2");
		StdOut.println("addfirst to string: " + deque.toString());
		deque.addFirst("3");
		StdOut.println("addfirst to string: " + deque.toString());
		deque.addFirst("4");
		StdOut.println("addfirst to string: " + deque.toString());
		deque.addFirst("5");
		StdOut.println("addfirst to string: " + deque.toString());
		deque.removeFirst();
		StdOut.println("removefirst to string: " + deque.toString());
		deque.removeFirst();
		StdOut.println("removefirst to string: " + deque.toString());
		deque.removeFirst();
		StdOut.println("removefirst to string: " + deque.toString());
		deque.removeFirst();
		StdOut.println("removefirst to string: " + deque.toString());
		deque.removeFirst();
		StdOut.println("removefirst to string: " + deque.toString());
		deque.addLast("1");
		StdOut.println("addlast to string: " + deque.toString());
		deque.addLast("2");
		StdOut.println("addlast to string: " + deque.toString());
		deque.addLast("3");
		StdOut.println("addlast to string: " + deque.toString());
		deque.addLast("4");
		StdOut.println("addlast to string: " + deque.toString());
		deque.addLast("5");
		StdOut.println("addlast to string: " + deque.toString());
		deque.removeLast();
		StdOut.println("removelast to string: " + deque.toString());
		deque.removeLast();
		StdOut.println("removelast to string: " + deque.toString());
		deque.removeLast();
		StdOut.println("removelast to string: " + deque.toString());
		deque.removeLast();
		StdOut.println("removelast to string: " + deque.toString());
		deque.removeLast();
		StdOut.println("removelast to string: " + deque.toString());
	}
}
