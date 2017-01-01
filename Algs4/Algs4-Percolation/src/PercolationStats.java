import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * 
 * @author David Gerrells
 * @date 12/30/2016 Class used to run stats for the Percolation class. Can be
 *       run from command line by passing in 2 values: grid size n, and trial
 *       runs, trials. Both values must be integers larger than 0.
 */
public class PercolationStats {

	/**
	 * stats values calculated after construction. No need to calculate them
	 * every time they are needed
	 */
	private double standardDeviation, mean, confLo, confH;

	/**
	 * Will run the given trials on systems of n size and store the stddev,
	 * mean, confL, and confH.
	 * 
	 * @param n
	 * @param trials
	 */
	public PercolationStats(final int n, final int trials) {
		if (n < 1 || trials < 1) {
			throw new IllegalArgumentException(
					"Invalid grid or trial count passed in. GridSize: " + n + " Trials: " + trials);
		}
		double[] trialThreshholds = new double[trials];
		int trialCount = 0;
		while (trialCount < trials) {
			trialThreshholds[trialCount] = (double) this.runTrial(n).numberOfOpenSites() / (n * n);
			trialCount++;
		}
		this.standardDeviation = StdStats.stddev(trialThreshholds);
		this.mean = n == 1 ? Double.NaN : StdStats.mean(trialThreshholds);
		this.confLo = this.mean - (1.96 * this.standardDeviation) / Math.sqrt(trials);
		this.confH = this.mean - (1.96 * this.standardDeviation) / Math.sqrt(trials);
	}

	private Percolation runTrial(final int n) {
		Percolation percolate = new Percolation(n);
		while (!percolate.percolates()) {
			final int x = StdRandom.uniform(1, n + 1);
			final int y = StdRandom.uniform(1, n + 1);
			if (!percolate.isOpen(x, y)) {
				percolate.open(x, y);
			}
		}
		return percolate;
	}

	/**
	 * Returns the double mean value from the trails run.
	 * 
	 * @return
	 */
	public double mean() {
		return this.mean;
	}

	/**
	 * Returns the double standard deviation from the trials run.
	 * 
	 * @return
	 */
	public double stddev() {
		return this.standardDeviation;
	}

	/**
	 * Returns the low confidence value from the trials run.
	 * 
	 * @return
	 */
	public double confidenceLo() {
		return this.confLo;
	}

	/**
	 * Returns the high confidence value from the trials run.
	 * 
	 * @return
	 */
	public double confidenceHi() {
		return this.confH;
	}

	/**
	 * Main entry point for running from command line. Must pass in two
	 * arguments, grid size "n" and trial runs "trial". Both values must be
	 * integers larger than 0.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			StdOut.println("Must pass in grid size and trial count.");
			return;
		}
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats stats = new PercolationStats(n, trials);
		StdOut.println("mean                    = " + stats.mean());
		StdOut.println("stddev                  = " + stats.stddev());
		StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
	}
}
