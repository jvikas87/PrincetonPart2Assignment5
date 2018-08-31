public class CircularSuffixArray {
	private String s;

	private Pair[] pairList;

	public CircularSuffixArray(String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		this.s = s;
		pairList = new Pair[s.length()];
		for (int index = 0; index < s.length(); index++) {
			Pair p = new Pair(index, s.charAt(index));
			pairList[index] = p;
		}
		sort(pairList);
	} // circular
		// suffix
		// array
		// of
		// s

	public int length() {
		return s.length();
	} // length of s

	public int index(int i) {
		if (i < 0 || i >= s.length()) {
			throw new IllegalArgumentException();
		}
		return pairList[i].index;
	}// returns index of ith sorted suffix

	public static void main(String[] args) {
		String s = "ABRACADABRA!";
		CircularSuffixArray array = new CircularSuffixArray(s);
		for (int i = 0; i < s.length(); i++) {
			System.out.println(array.index(i));
		}
	}// unit testing (required)

	private static class Pair implements Comparable<Pair> {
		private int index;
		private Character ch;

		public Pair(int index, Character ch) {
			super();
			this.index = index;
			this.ch = ch;
		}

		@Override
		public int compareTo(Pair arg0) {
			return this.ch.compareTo(arg0.ch);
		}

		@Override
		public String toString() {
			return index + ":" + ch;
		}
	}

	/**
	 * Rearranges the array of W-character strings in ascending order.
	 *
	 * @param a
	 *            the array to be sorted
	 * @param w
	 *            the number of characters per string
	 */
	private void sort(Pair[] a) {
		int n = a.length;
		int w = s.length();
		int R = 256; // extend ASCII alphabet size
		Pair[] aux = new Pair[n];

		for (int d = w - 1; d >= 0; d--) {
			// sort by key-indexed counting on dth character

			// compute frequency counts
			int[] count = new int[R + 1];
			for (int i = 0; i < n; i++) {
				int index = (a[i].index + d) % w;
				count[s.charAt(index) + 1]++;
			}
			// compute cumulates
			for (int r = 0; r < R; r++)
				count[r + 1] += count[r];

			// move data
			for (int i = 0; i < n; i++) {
				int index = (a[i].index + d) % w;
				aux[count[s.charAt(index)]++] = a[i];
			}

			// copy back
			for (int i = 0; i < n; i++)
				a[i] = aux[i];
		}
	}

}