import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

	// apply move-to-front encoding, reading from standard input and writing to
	// standard output
	public static void encode() {
		String q = BinaryStdIn.readString();
		char[] array = getAsciiArray();
		for (char candidateChar : q.toCharArray()) {
			int index = findIndexAndModify(candidateChar, array);
			BinaryStdOut.write(index, 8);
		}
		BinaryStdOut.flush();
		BinaryStdOut.close();
	}

	private static char[] getAsciiArray() {
		char[] array = new char[256];
		for (int index = 0; index < 256; index++) {
			array[index] = (char) index;
		}
		return array;
	}

	private static int findIndexAndModify(char ch, char[] array) {
		int index = 0;
		for (char c : array) {
			if (c == ch) {
				break;
			}
			index++;
		}
		for (int idx = index - 1; idx >= 0; idx--) {
			array[idx + 1] = array[idx];
		}
		array[0] = ch;
		return index;

	}

	// apply move-to-front decoding, reading from standard input and writing to
	// standard output
	public static void decode() {
		char[] array = getAsciiArray();
		while (!BinaryStdIn.isEmpty()) {
			int index = BinaryStdIn.readInt(8);
			BinaryStdOut.write(modifyArrayAndGetChar(array, index));
		}
		BinaryStdOut.flush();
		BinaryStdOut.close();
	}

	private static char modifyArrayAndGetChar(char[] array, int index) {
		char ch = array[index];
		for (int idx = index - 1; idx >= 0; idx--) {
			array[idx + 1] = array[idx];
		}
		array[0] = ch;
		return ch;
	}

	// if args[0] is '-', apply move-to-front encoding
	// if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {
		if (args[0].equals("-")) {
			encode();
		} else {
			decode();
		}
	}
}