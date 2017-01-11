import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class RandomizedQueueTest {

	String s1 = "Jack Sparhawk", s2 = "Will turntable", s3 = "Mackle McGroover", s4 = "Octavious Montegrume";
	HashSet<String> testItems;
	RandomizedQueue<String> rq;

	@Before
	public void setUp() throws Exception {
		testItems = new HashSet<String>();
		testItems.add(s1);
		testItems.add(s2);
		testItems.add(s3);
		testItems.add(s4);

		rq = new RandomizedQueue<String>();
		rq.enqueue(s1);
		rq.enqueue(s2);
		rq.enqueue(s3);
		rq.enqueue(s4);
	}

	@Test
	public void testIsEmpty() {
		assertEquals(false, rq.isEmpty());
		rq.dequeue();
		rq.dequeue();
		rq.dequeue();
		rq.dequeue();
		assertEquals(true, rq.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(4, rq.size());
		rq.dequeue();
		assertEquals(3, rq.size());
		rq.dequeue();
		rq.dequeue();
		rq.dequeue();
		assertEquals(0, rq.size());
	}

	@Test
	public void testEnqueue() {
		rq = new RandomizedQueue<String>();
		rq.enqueue("wuba duba dub dub");
		assertEquals("wuba duba dub dub", rq.dequeue());
	}

	@Test(expected = NullPointerException.class)
	public void testEnqueue_Null() {
		rq.enqueue(null);
	}

	@Test
	public void testDequeue() {
		while (!rq.isEmpty()) {
			assertTrue("Should be in list of expected strings", this.testItems.contains(rq.dequeue()));
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void testDequeue_Empty() {
		while (!rq.isEmpty()) {
			assertTrue("Should be in list of expected strings", this.testItems.contains(rq.dequeue()));
		}
		rq.dequeue();
	}

	@Test
	public void testSample() {
		assertTrue("Should be in list of expected strings", this.testItems.contains(rq.sample()));
		assertEquals(4, rq.size());
		assertTrue("Should be in list of expected strings", this.testItems.contains(rq.sample()));
		assertTrue("Should be in list of expected strings", this.testItems.contains(rq.sample()));
		assertTrue("Should be in list of expected strings", this.testItems.contains(rq.sample()));
		// try a few times as it is random.
		assertEquals(4, rq.size());
	}

	@Test(expected = NoSuchElementException.class)
	public void testSample_empty() {
		rq = new RandomizedQueue<String>();
		rq.sample();
	}

	@Test
	public void testIterator() {
		Iterator<String> it = rq.iterator();

		while (it.hasNext()) {
			assertTrue("Should have string in expected strings list", this.testItems.contains(it.next()));
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void testIterator_NextIsEmpty() {
		Iterator<String> it = rq.iterator();

		while (it.hasNext()) {
			assertTrue("Should have string in expected strings list", this.testItems.contains(it.next()));
		}
		it.next();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testIterator_NoRemoveMethod() {
		rq.iterator().remove();
	}

}
