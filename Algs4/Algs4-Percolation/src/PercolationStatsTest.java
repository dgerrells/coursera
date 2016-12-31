import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PercolationStatsTest {

	final int gridSize = 10, trialCountLow = 2, trialCountHigh = 2000;
	final String nanVal = "" + Double.NaN;

	@Test
	public void testPercolationStats_ValidInput() {
		// new PercolationStats(200, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPercolationStats_BadInputGridSize() {
		new PercolationStats(-1, 200);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPercolationStats_BadInputTrialSize() {
		new PercolationStats(1, -200);
	}

	@Test
	public void testMean_LowTrials() {
		PercolationStats stats = new PercolationStats(gridSize, trialCountLow);
		assertTrue("Value should be between 0 and 1", stats.mean() < 1 && stats.mean() > 0);
	}

	@Test
	public void testMean_HighTrials() {
		PercolationStats stats = new PercolationStats(gridSize, this.trialCountHigh);
		assertTrue("Value should be between 0 and 1", stats.mean() < 1 && stats.mean() > 0);
	}

	@Test
	public void testStddev_LowTrials() {
		PercolationStats stats = new PercolationStats(gridSize, this.trialCountHigh);
		assertTrue("Value should be between 0 and 1", stats.stddev() < 1 && stats.stddev() > 0);
	}

	@Test
	public void testStddev_HighTrials() {
		PercolationStats stats = new PercolationStats(gridSize, this.trialCountHigh);
		assertTrue("Value should be between 0 and 1", stats.stddev() < 1 && stats.stddev() > 0);
	}

	@Test
	public void testStddev_1Trial() {
		PercolationStats stats = new PercolationStats(gridSize, 1);
		assertTrue("Value should be NaN", this.nanVal.equals("" + stats.stddev()));
	}

	@Test
	public void testConfidenceLo_LowTrials() {
		PercolationStats stats = new PercolationStats(gridSize, this.trialCountHigh);
		assertTrue("Value should be between 0 and 1", stats.confidenceLo() < 1 && stats.confidenceLo() > 0);
	}

	@Test
	public void testConfidenceLo_HighTrials() {
		PercolationStats stats = new PercolationStats(gridSize, this.trialCountHigh);
		assertTrue("Value should be between 0 and 1", stats.confidenceLo() < 1 && stats.confidenceLo() > 0);
	}

	@Test
	public void testConfidenceHi_LowTrials() {
		PercolationStats stats = new PercolationStats(gridSize, this.trialCountHigh);
		assertTrue("Value should be between 0 and 1", stats.confidenceHi() < 1 && stats.confidenceHi() > 0);
	}

	@Test
	public void testConfidenceHi_HighTrials() {
		PercolationStats stats = new PercolationStats(gridSize, this.trialCountHigh);
		assertTrue("Value should be between 0 and 1", stats.confidenceHi() < 1 && stats.confidenceHi() > 0);
	}

	@Test
	public void testPercolationStats_1x1Grid() {
		PercolationStats stats = new PercolationStats(1, this.trialCountHigh);
		assertTrue("Value should be NaN", this.nanVal.equals("" + stats.confidenceHi()));
		assertTrue("Value should be NaN", this.nanVal.equals("" + stats.confidenceLo()));
		assertTrue("Value should be NaN", this.nanVal.equals("" + stats.mean()));
	}

}
