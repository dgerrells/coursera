import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PercolationTest {

	Percolation perc;
	final int gridSize = 10;

	@Before
	public void setUp() {
		perc = new Percolation(this.gridSize);
	}

	@Test
	public void isOpen() {
		assertFalse("Should not be an open cell", this.perc.isOpen(4, 4));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void isOpenCellIsInvalidInputNegativeIndex() {
		this.perc.isOpen(-12, 4);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void isOpenCellIsInvalidInputLargeIndex() {
		this.perc.isOpen(123, 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeConstructorArg() {
		new Percolation(-12);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testZeroConstructorArg() {
		new Percolation(0);
	}

	@Test
	public void openCellCellShouldBeOpenAfterMethod() {
		this.perc.open(2, 1);
		assertTrue("Should have the site at 2 1 be open", this.perc.isOpen(2, 1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void openCellIsInvalidInputNegativeIndex() {
		this.perc.open(-12, 4);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void openCellIsInvalidInputLargeIndex() {
		this.perc.open(123, 4);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void isFullCellIsInvalidInputNegativeIndex() {
		this.perc.isFull(-12, 4);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void isFullCellIsInvalidInputLargeIndex() {
		this.perc.isFull(123, 4);
	}

	@Test
	public void isFullCellIsFull() {
		this.perc.open(1, 1);
		this.perc.open(2, 1);
		assertTrue("Cell should be full at 2 1", this.perc.isFull(2, 1));
	}

	@Test
	public void isFullCellIsNotFull() {
		this.perc.open(1, 1);
		this.perc.open(2, 1);
		this.perc.open(4, 1);
		assertFalse("Cell should not be full at 2 1", this.perc.isFull(3, 1));
	}

	@Test
	public void numberOfOpenSites() {
		this.perc.open(1, 1);
		this.perc.open(2, 1);
		this.perc.open(4, 1);
		assertEquals(this.perc.numberOfOpenSites(), 3);
		this.perc.open(7, 1);
		assertNotEquals(this.perc.numberOfOpenSites(), 3);
	}

	@Test
	public void percolates_ItShouldPerc() {
		this.perc = new Percolation(4);
		this.perc.open(1, 1);
		this.perc.open(1, 2);
		this.perc.open(1, 3);
		this.perc.open(1, 4);
		assertEquals(this.perc.percolates(), true);
	}

	@Test
	public void percolates_ItShouldPerc1x1Grid() {
		this.perc = new Percolation(1);
		this.perc.open(1, 1);
		assertEquals(this.perc.percolates(), true);
	}

	@Test
	public void percolates_ItShouldNotPerc() {
		this.perc = new Percolation(4);
		this.perc.open(1, 1);
		this.perc.open(2, 1);
		this.perc.open(4, 1);
		this.perc.open(4, 1);
		assertNotEquals(this.perc.percolates(), true);
	}

}
