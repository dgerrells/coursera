import edu.princeton.cs.algs4.*;

/**
 * 
 * @author David Gerrells
 * @date 12/30/2016
 * This class is used to find a given system percolates.
 * It contain functions to mutate the current system.
 */
public class Percolation {
	
	/** this current grid state of open and filled cells */
	private boolean[][] grid;
	/** the weighted quick union uf system to track percolation */
	private WeightedQuickUnionUF system;
	/** the systems grid size */
	private final int systemSize;
	/** the top component of the system */
	private final int top;
	/** the bottom component of the system */
	private final int bottom;
	/** the number of open sites on the system */
	private int openSites;
	
	/**
	 * Initialize the system with filled blocks, setup the weighted 
	 * quick union system, and set the top and bottom values.
	 * 
	 * @param n the size of the systems length and height
	 */
	public Percolation(final int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Invalid argument paased to constructor: "+n);
		}
		final int squaredSize = n*n;
		this.top = 0;
		this.systemSize = n;
		this.system = new WeightedQuickUnionUF(squaredSize+2);
		this.grid = new boolean[this.systemSize][this.systemSize];
		this.bottom = squaredSize +1;
	}
	
	private int getUFIndex(final int x, final int y) {
		return this.systemSize*(x-1) + y;
	}
	
	private void validateInput(final int row, final int col) {
		if (row < 1 || row > this.systemSize) {
			throw new IndexOutOfBoundsException("Row index given is out of range: " + row);
		}
		if (col < 1 || col > this.systemSize) {
			throw new IndexOutOfBoundsException("Col index given is out of range: " + col);
		}
	}

	private void tryUnion(final int srcX, final int srcY, final int dstX, final int dstY) {
		if (dstX < 1 || dstY < 1 || dstX > this.systemSize || dstY > this.systemSize || !this.isOpen(dstX,dstY)) {
			return;
		}
		this.system.union(this.getUFIndex(dstX, dstY), this.getUFIndex(srcX, srcY));
	}
 

	/**
	 * Open the given cell in the system
	 * Not zero-based indexing 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void open(final int x, final int y) {
		this.validateInput(x, y);
		if (this.isOpen(x, y)) {
			return;
		}
		this.grid[x-1][y-1] = true;
		this.openSites++;
		if (x == 1) {
			this.system.union(this.getUFIndex(x, y), this.top);
		}
		if (x == this.systemSize) {
			this.system.union(this.getUFIndex(x, y), this.bottom);
		}
		
		this.tryUnion(x, y, x-1, y);
		this.tryUnion(x, y, x+1, y);
		this.tryUnion(x, y, x, y-1);
		this.tryUnion(x, y, x, y+1);
	}
	
	/**
	 * Check if the given x and y tuple coordinate is open.
	 * Not zero-based indexing
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return boolean indicating if the cell is open
	 */
	public boolean isOpen(final int x, final int y) {
		this.validateInput(x, y);
		return this.grid[x-1][y-1];
	}
	
	/**
	 * Check if the given x and y tuple coordinate is full.
	 * Full mean that there exists a path from the top of the system to this cell.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return boolean indicating if the cell is full
	 */
	public boolean isFull(final int x, final int y) {
		this.validateInput(x, y);
		return this.system.connected(this.top, this.getUFIndex(x, y));
	}
	
	/**
	 * Returns the number of current open sites in the system.
	 * @return an integer representing the number of open sites
	 */
	public int numberOfOpenSites() {
		return this.openSites;
	}
	
	/**
	 * Checks if the given system percolates. This means that there exists
	 * a path from the top of the system to the bottom.
	 * @return boolean value indicating if the system percolates
	 */
	public boolean percolates() {
		return this.system.connected(this.bottom, this.top);
	}
	
}
