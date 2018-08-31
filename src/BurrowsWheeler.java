import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	// apply Burrows-Wheeler transform, reading from standard input and writing
	// to standard output

	public static void transform() {
		String q = BinaryStdIn.readString();
		CircularSuffixArray suffixArray = new CircularSuffixArray(q);
		int row = 0;
		StringBuilder builder = new StringBuilder();
		for (int index = 0; index < q.length(); index++) {
			int targetIndex = suffixArray.index(index);
			if (targetIndex == 0) {
				row = index;
			}
			if (targetIndex - 1 >= 0) {
				builder.append(q.charAt(targetIndex - 1));
			} else {
				builder.append(q.charAt(q.length() - 1));
			}
		}	
		BinaryStdOut.write(row, 32);
		BinaryStdOut.write(builder.toString());
		BinaryStdOut.flush();
		BinaryStdOut.close();
	}

	// apply Burrows-Wheeler inverse transform, reading from standard input and
	// writing to standard output
	public static void inverseTransform() {
		int start = BinaryStdIn.readInt(32);
		String value = BinaryStdIn.readString();
		Pair[] pairs = map(value.toCharArray());

		char[] array = new char[value.length()];

		int begin = 0;
		int arrayIndex = 0;
		for (Pair p : pairs) {
			if (p != null) {
				char ch = (char) arrayIndex;
				for (int index = begin; index < begin + p.size; index++) {
					array[index] = ch;
				}
				begin = begin + p.size;
			}
			arrayIndex++;
		}
		PairBack[] pairBacks = mapBack(value.toCharArray());
		StringBuilder builder = new StringBuilder();
		int count = 0;
		int len = value.length();
		while (count < len) {
			builder.append(array[start]);
			int index = start - pairs[array[start]].start;
			start = pairBacks[array[start]].list.get(index);
			count++;
		}
		BinaryStdOut.write(builder.toString());
		BinaryStdOut.flush();
		BinaryStdOut.close();
	}

	// if args[0] is '-', apply Burrows-Wheeler transform
	// if args[0] is '+', apply Burrows-Wheeler inverse transform
	public static void main(String[] args) {
		//q = BinaryStdIn.readString();
		if (args[0].equals("-")) {
			transform();
		} else {
			inverseTransform();
		}
	}

	private static Pair[] map(char[] array) {
		Pair[] cache = new Pair[256];
		for (char ch : array) {
			if (cache[ch] == null) {
				cache[ch] = new Pair();
			}
			cache[ch].size++;
		}
		int start = 0;
		for (Pair p : cache) {
			if (p != null) {
				p.start = start;
				start += p.size;
			}
		}
		return cache;
	}

	private static PairBack[] mapBack(char[] array) {
		
		PairBack[] pairback = new PairBack[256];
		for (int index = 0; index < array.length; index++) {
			if (pairback[array[index]] == null) {
				pairback[array[index]] = new PairBack();
				pairback[array[index]].list = new ArrayList<>();
			}
			pairback[array[index]].list.add(index);
		}
		return pairback;
	}

	private static class Pair {
		private int size;
		private int start;
	}

	private static class PairBack {
		private List<Integer> list;

		@Override
		public String toString() {
			return list.toString();
		}
	}

}