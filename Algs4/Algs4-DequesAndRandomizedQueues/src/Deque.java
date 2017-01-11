import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private Node front, end;
	private int size;

	public Deque() {
		this.size = 0;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int size() {
		return this.size;
	}

	public void addFirst(final Item item) {
		this.checkNullInput(item);

		if (this.isEmpty()) {
			front = new Node(item, null, null);
			end = front;
		} else {
			Node n = new Node(item, this.front, null);
			front.prev = n;
			front = n;
		}
		this.size++;
	}

	public void addLast(final Item item) {
		this.checkNullInput(item);

		if (this.isEmpty()) {
			end = new Node(item, null, null);
			front = end;
		} else {
			Node n = new Node(item, null, this.end);
			end.next = n;
			end = n;
		}
		this.size++;
	}

	private void checkNullInput(final Item value) {
		if (value == null) {
			throw new NullPointerException("Cannot add null as a value to a Deque");
		}
	}

	public Item removeFirst() {
		if (this.front == null) {
			throw new NoSuchElementException("Cannot remove an element from and empty Deque");
		}

		this.size--;
		Item v = front.value;
		front = front.next;
		if (this.isEmpty()) {
			this.end = null;
		} else {
			front.prev = null;
		}
		return v;
	}

	public Item removeLast() {
		if (this.end == null) {
			throw new NoSuchElementException("Cannot remove an element from and empty Deque");
		}

		this.size--;
		Item v = end.value;
		end = end.prev;
		if (this.isEmpty()) {
			this.front = null;
		} else {
			end.next = null;
		}
		return v;
	}

	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			private Node index = front;

			public boolean hasNext() {
				return index != null;
			}

			public Item next() {
				if (!hasNext()) {
					throw new NoSuchElementException("Cannot call next on an iterator backed by and empty Deque");
				}
				Item val = index.value;
				index = index.next;
				return val;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	private class Node {
		private Item value;
		private Node next, prev;

		public Node(final Item value, final Node next, final Node prev) {
			this.next = next;
			this.prev = prev;
			this.value = value;
		}
	}

}
