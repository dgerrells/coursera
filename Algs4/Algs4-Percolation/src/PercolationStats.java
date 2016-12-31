import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	double standardDeviation, mean, confLo, confH;

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
		this.confLo = this.mean - 1.96 / Math.sqrt(trials);
		this.confH = this.mean - 1.96 / Math.sqrt(trials);
	}

	Percolation runTrial(final int n) {
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

	public double mean() {
		return this.mean;
	}

	public double stddev() {
		return this.standardDeviation;
	}

	public double confidenceLo() {
		return this.confLo;
	}

	public double confidenceHi() {
		return this.confH;
	}

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
