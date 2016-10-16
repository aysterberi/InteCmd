package intecmd.commands;

import intecmd.CommandInterface;

import java.io.*;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class WordCount implements CommandInterface, Callable {

	private String message;
	private Long charCount;
	private Long wordCount;
	private Long newlineCount;
	private boolean printWords, printChars, printLines;

	public WordCount() {
		message = "";
		wordCount = 0L;
		newlineCount = 0L;
		charCount = 0L;
	}

	@Override
	public void in(String[] data) {
		if (data == null) {
			System.out.println("No input detected. Run wc help for more information.");
			return;
		}
		parseArguments(data);
	}

	private void parseArguments(String[] data) {

		for (int i = 0; i < data.length; i++) {
			switch (data[i]) {
				case "wc":
					break; //fluff from the command interpreter
				case "":
					return;
				case "-c":
					printChars = true;
					break;
				case "-l":
					printLines = true;
					break;
				case "-w":
					printWords = true;
					break;
				case "help":
					System.out.print(help());
					break;
				default:
					openFile(data[i]);
			}
		}
	}

	/**
	 * A simple word counting procedure. Whitespace is
	 * defined by the Java criterion as outlined in {@link Character}
	 * <p>
	 * We use {@link LineNumberReader} to keep track of \r\n & \n for us.
	 * This way we can be platform-agnostic as the Java library
	 * takes care of it for us.
	 * <p>
	 * We also keep a count of the amount of 16-bit Unicode
	 * characters.
	 * <p>
	 * How does it work? Well, we step through each character
	 * and check if it's whitespace. If it's whitespace,
	 * we store it in last and proceed to the next character.
	 * If this character is not whitespace AND the previous
	 * character was a whitespace character, we have encountered
	 * the start of a word so we increase our counter.
	 * Repeat until we reach EOF (signalled as int -1)
	 *
	 * @param is The data that needs to be counted
	 * @return [0] = words, [1] = chars, [2] = lines
	 * @throws Exception If the InputStream cannot be read
	 */
	public long[] processStream(InputStream is) throws Exception {
		long words = 0L;
		long chars = 0L;
		long lines = 0L;
		LineNumberReader in = new LineNumberReader(new InputStreamReader((is)));
		int c;
		char cc;
		char last = ' ';
		// -1 is EOF
		while ((c = in.read()) != -1) {
			chars++;
			cc = (char) c;
			if (Character.isWhitespace(cc)) {
				last = cc;
				continue;
			}
			if (!Character.isWhitespace(cc) && Character.isWhitespace(last)) {
				words++;
			}
			last = cc;
		}
		lines = in.getLineNumber();
		in.close(); //release resource
		return new long[]{words, chars, lines};
	}


	public void openFile(String s) {
		try {
			InputStream in = new FileInputStream(new File(s));
			processStream(in);
		} catch (Exception e) {
			message = "Could not open " + s + ".\n";
		}

	}

	@Override
	public String out() {
//		if (!done) {
//			return help();
//		}
		return wordCount + ".";
	}

	private String format() {
		StringBuilder sb = new StringBuilder();
		if (printWords) {
			sb.append("Words: ").append(wordCount).append(".");
		}
		if (printChars) {
			sb.append("Lines: ").append(newlineCount).append(".");
		}
		if (printLines) {
			sb.append("Lines: ").append(newlineCount).append(".");
		}
		return sb.toString();
	}

	@Override
	public String help() {
		String s;
		String general = "\nwc - wordcount. \nThis program counts all words for a given file. " +
				"\nWhitespace is used as the delimiter. Empty lines will not be counted." +
				"\nFlags:\n-l\t\tCount all lines.\n-w\t\tCount all words.\n-c\t\tCount all 16-bit Unicode characters.\nDefault (no flags) shows all of these counts.\n\nFor example, to count the lines in file xyz.txt run:\n\t\t\twc -l xyz.txt";

		s = message.isEmpty() ? message + general : general;
		return s;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public Object call() throws Exception {
//		try {
//			count();
//		} catch (Exception e) {
//			return message;
//		}
		return format();
	}

}
