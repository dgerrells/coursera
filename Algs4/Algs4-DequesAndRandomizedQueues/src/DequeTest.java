import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class DequeTest {

	Deque<String> testDequeFrontFirst, testDequeBackFirst;
	String s1 = "Jack Sparhawk", s2 = "Will turntable", s3 = "Mackle McGroover", s4 = "Octavious Montegrume";

	@Before
	public void setUp() throws Exception {
		testDequeFrontFirst = new Deque<String>();
		testDequeFrontFirst.addFirst(s1);
		testDequeFrontFirst.addFirst(s2);
		testDequeFrontFirst.addLast(s3);
		testDequeFrontFirst.addLast(s4);

		testDequeBackFirst = new Deque<String>();
		testDequeBackFirst.addLast(s1);
		testDequeBackFirst.addLast(s2);
		testDequeBackFirst.addFirst(s3);
		testDequeBackFirst.addFirst(s4);

	}

	@Test
	public void testDeque() {
		Deque<Integer> d = new Deque<Integer>();
		d.addFirst(24);
		d.addLast(42);
		assertEquals(2, d.size());
		assertEquals(new Integer(24), d.removeFirst());
	}

	@Test
	public void testIsEmpty() {
		assertEquals(false, this.testDequeBackFirst.isEmpty());
		assertEquals(false, this.testDequeFrontFirst.isEmpty());

		this.testDequeBackFirst = new Deque<String>();
		assertEquals(true, this.testDequeBackFirst.isEmpty());

		this.testDequeFrontFirst.removeFirst();
		this.testDequeFrontFirst.removeFirst();
		this.testDequeFrontFirst.removeFirst();
		this.testDequeFrontFirst.removeFirst();
		assertEquals(true, this.testDequeFrontFirst.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(4, this.testDequeBackFirst.size());
		assertEquals(4, this.testDequeFrontFirst.size());

		this.testDequeBackFirst.removeFirst();
		this.testDequeBackFirst.removeLast();

		this.testDequeFrontFirst.removeFirst();
		this.testDequeFrontFirst.removeLast();

		assertEquals(2, this.testDequeBackFirst.size());
		assertEquals(2, this.testDequeFrontFirst.size());

		this.testDequeBackFirst.removeFirst();
		this.testDequeBackFirst.removeLast();

		this.testDequeFrontFirst.removeFirst();
		this.testDequeFrontFirst.removeLast();

		assertEquals(0, this.testDequeBackFirst.size());
		assertEquals(0, this.testDequeFrontFirst.size());
	}

	@Test
	public void testAddFirst() {
		assertEquals(s2, testDequeFrontFirst.removeFirst());
		assertEquals(s1, testDequeFrontFirst.removeFirst());
		this.testDequeFrontFirst.addFirst("Wubaduba dub dub");
		assertEquals("Wubaduba dub dub", testDequeFrontFirst.removeFirst());
		assertEquals(s3, testDequeFrontFirst.removeFirst());
		assertEquals(s4, testDequeFrontFirst.removeFirst());

		assertEquals(s4, testDequeBackFirst.removeFirst());
		assertEquals(s3, testDequeBackFirst.removeFirst());
		this.testDequeBackFirst.addFirst("Wubaduba dub dub");
		assertEquals("Wubaduba dub dub", testDequeBackFirst.removeFirst());
		assertEquals(s1, testDequeBackFirst.removeFirst());
		assertEquals(s2, testDequeBackFirst.removeFirst());

		testDequeBackFirst.addFirst("1");
		testDequeBackFirst.removeLast();
		testDequeBackFirst.addFirst("2");
		testDequeBackFirst.removeLast();
	}

	@Test
	public void testAddLast() {
		assertEquals(s4, testDequeFrontFirst.removeLast());
		assertEquals(s3, testDequeFrontFirst.removeLast());
		this.testDequeFrontFirst.addLast("Wubaduba dub dub");
		assertEquals("Wubaduba dub dub", testDequeFrontFirst.removeLast());
		assertEquals(s1, testDequeFrontFirst.removeLast());
		assertEquals(s2, testDequeFrontFirst.removeLast());

		assertEquals(s2, testDequeBackFirst.removeLast());
		assertEquals(s1, testDequeBackFirst.removeLast());
		this.testDequeBackFirst.addLast("Wubaduba dub dub");
		assertEquals("Wubaduba dub dub", testDequeBackFirst.removeLast());
		assertEquals(s3, testDequeBackFirst.removeLast());
		assertEquals(s4, testDequeBackFirst.removeLast());
	}

	@Test(expected = NullPointerException.class)
	public void testAddLast_NullValue() {
		this.testDequeFrontFirst.addLast(null);
	}

	@Test(expected = NullPointerException.class)
	public void testAddFirst_NullValue() {
		this.testDequeFrontFirst.addFirst(null);
	}

	@Test(expected = NoSuchElementException.class)
	public void testRemoveFirst_EmptyDeque() {
		new Deque<Integer>().removeFirst();
	}

	@Test(expected = NoSuchElementException.class)
	public void testRemoveLast_EmptyDeque() {
		new Deque<Integer>().removeLast();
	}

	@Test
	public void testRemoveFirst() {
		assertEquals(s2, testDequeFrontFirst.removeFirst());
		assertEquals(s1, testDequeFrontFirst.removeFirst());
		assertEquals(s3, testDequeFrontFirst.removeFirst());
		assertEquals(s4, testDequeFrontFirst.removeFirst());

		assertEquals(s4, testDequeBackFirst.removeFirst());
		assertEquals(s3, testDequeBackFirst.removeFirst());
		assertEquals(s1, testDequeBackFirst.removeFirst());
		assertEquals(s2, testDequeBackFirst.removeFirst());
	}

	@Test
	public void testRemoveLast() {
		assertEquals(s4, testDequeFrontFirst.removeLast());
		assertEquals(s3, testDequeFrontFirst.removeLast());
		assertEquals(s1, testDequeFrontFirst.removeLast());
		assertEquals(s2, testDequeFrontFirst.removeLast());

		assertEquals(s2, testDequeBackFirst.removeLast());
		assertEquals(s1, testDequeBackFirst.removeLast());
		assertEquals(s3, testDequeBackFirst.removeLast());
		assertEquals(s4, testDequeBackFirst.removeLast());
	}

	@Test
	public void testIterator_ValidUse() {
		Iterator<String> it = this.testDequeFrontFirst.iterator();
		String[] validValues = new String[] { s2, s1, s3, s4 };
		int assertIndex = 0;
		while (it.hasNext()) {
			assertEquals(validValues[assertIndex++], it.next());
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void testIterator_CannotCallNextOnEmptyIterator() {
		Iterator<String> it = this.testDequeFrontFirst.iterator();
		String[] validValues = new String[] { s2, s1, s3, s4 };
		int assertIndex = 0;
		while (it.hasNext()) {
			assertEquals(validValues[assertIndex++], it.next());
		}
		it.next();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testIterator_CannotRemoveElementsForTheIterator() {
		this.testDequeFrontFirst.iterator().remove();
	}

}
