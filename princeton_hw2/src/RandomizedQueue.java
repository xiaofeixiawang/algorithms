
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] val;
	private int size;

	public RandomizedQueue() {
		val = (Item[]) new Object[2];
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	private void newArray(int s) {
		Item[] tmp = (Item[]) new Object[s];
		for (int i = 0; i < s / 2; i++) {
			tmp[i] = val[i];
		}
		val = tmp;
	}

	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException();
		if (size == val.length) {
			newArray(size * 2);
		}
		val[size++] = item;
	}

	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();
		int No = StdRandom.uniform(size);
		Item res = val[No];
		val[No] = val[--size];
		val[size] = null;
		if (size > 0 && size == val.length / 4) {
			newArray(size * 2);
		}
		return res;
	}

	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException();
		int No = StdRandom.uniform(size);
		return val[No];
	}

	public Iterator<Item> iterator() {
		return new IterRanQue();
	}

	private class IterRanQue<Item> implements Iterator<Item> {
		private Item[] copy;
		private int copysize;

		public IterRanQue() {
			copy = (Item[]) new Object[size];
			for (int i = 0; i < size; i++) {
				copy[i] = (Item) val[i];
			}
			copysize = size;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public boolean hasNext() {
			return copysize > 0;
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			int No = StdRandom.uniform(copysize);
			Item res = copy[No];
			copy[No] = copy[--copysize];
			copy[copysize] = null;
			return res;
		}
	}

	// public String toString() {
	// StringBuilder s = new StringBuilder();
	// for (int i = 0; i < size; i++)
	// s.append(val[i] + " ");
	// return s.toString();
	// }

	public static void main(String[] args) {
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		queue.enqueue(1);
		StdOut.println(queue.toString());
		queue.enqueue(2);
		StdOut.println(queue.toString());
		queue.enqueue(3);
		StdOut.println(queue.toString());
		queue.enqueue(4);
		StdOut.println(queue.toString());
		queue.enqueue(5);
		StdOut.println(queue.toString());
		queue.enqueue(6);
		StdOut.println(queue.toString());
		queue.enqueue(7);
		StdOut.println(queue.toString());
		queue.enqueue(8);
		StdOut.println(queue.toString());
		queue.enqueue(9);
		StdOut.println(queue.toString());
		queue.enqueue(10);
		StdOut.println(queue.toString());
		StdOut.println(queue.dequeue());
		StdOut.println(queue.toString());
		StdOut.println(queue.dequeue());
		StdOut.println(queue.toString());
		StdOut.println(queue.dequeue());
		StdOut.println(queue.toString());
		StdOut.println(queue.dequeue());
		StdOut.println(queue.toString());
		StdOut.println(queue.dequeue());
		StdOut.println(queue.toString());
	}
}
