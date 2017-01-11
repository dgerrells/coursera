import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();

		final int k = Integer.parseInt(args[0]);

		if (k == 0) {
			return;
		}

		int n = 1;
		while (!StdIn.isEmpty()) {
			String str = StdIn.readString();
			if (n <= k) {
				rq.enqueue(str);
			} else if (StdRandom.uniform() < (double) k / n) {
				rq.dequeue();
				rq.enqueue(str);
			}
			n++;
		}

		while (!rq.isEmpty()) {
			StdOut.println(rq.dequeue());
		}
	}

}
