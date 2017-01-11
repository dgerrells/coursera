import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private static int growthRate = 2, shrinkRate = 4;

	private Item[] items;
	private int size;

	public RandomizedQueue() {
		this.items = (Item[]) new Object[1];
		this.size = 0;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int size() {
		return this.size;
	}

	public void enqueue(final Item item) {
		if (item == null) {
			throw new NullPointerException("Cannot enqueue a null value");
		}
		items[size++] = item;
		if (size == items.length) {
			this.resizeItems(growthRate * size);
		}
	}

	public Item dequeue() {
		this.checkEmpty();

		int rnd = (int) (StdRandom.uniform() * this.size);
		Item item = this.items[rnd];
		this.items[rnd] = this.items[--this.size];
		this.items[this.size] = null;

		if (this.size <= this.items.length / shrinkRate) {
			this.resizeItems(this.items.length / growthRate);
		}
		return item;
	}

	public Item sample() {
		this.checkEmpty();
		return this.items[(int) (StdRandom.uniform() * this.size)];
	}

	public Iterator<Item> iterator() {
		final Item[] iterItems = (Item[]) new Object[this.size];
		for (int i = this.size - 1; i > -1; i--) {
			iterItems[i] = this.items[i];
		}

		return new Iterator<Item>() {
			private int size = iterItems.length;

			public boolean hasNext() {
				return this.size > 0;
			}

			public Item next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException("Cannot cal next on an empty iterator");
				}

				int rnd = (int) (StdRandom.uniform() * this.size);
				Item item = iterItems[rnd];
				iterItems[rnd] = iterItems[--this.size];
				iterItems[this.size] = null;
				return item;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	private void checkEmpty() {
		if (this.isEmpty()) {
			throw new NoSuchElementException("Cannot sample or dequeue from an empty RandomizedQueue");
		}
	}

	private void resizeItems(final int newSize) {
		Item[] newItems = (Item[]) new Object[newSize];
		for (int i = this.size - 1; i > -1; i--) {
			newItems[i] = this.items[i];
		}
		this.items = newItems;
	}

}
