package intecmd.commands;

import intecmd.CommandInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class WordCount implements CommandInterface, Callable {

	private Logger log;
	private State currentState = State.ALL;
	private String message;
	private Long charCount;
	private Long wordCount;
	private Long newlineCount;
	private BufferedReader bufferedReader;
	private boolean done;

	public WordCount() {
		log = Logger.getLogger(WordCount.class.getName());
		message = "";
		wordCount = 0L;
		newlineCount = 0L;
		charCount = 0L;
	}

	@Override
	public void in(String[] data) {
		if (data != null) {
			parseArguments(data);
		}
	}

	private void parseArguments(String[] data) {

		for (int i = 0; i < data.length; i++) {
			switch (data[i]) {
				case "wc":
					break; //fluff from the command interpreter
				case "":
					return;
				case "-c":
					currentState = State.CHARS;
					break;
				case "-l":
					currentState = State.LINES;
					break;
				case "-w":
					currentState = State.WORDS;
					break;
				case "help":
					System.out.print(help());
					break;
				default:
					tryOpenFile(data[i]);
			}
		}
	}

	private void tryOpenFile(String s) {
		try {
			FileReader fileReader = new FileReader(s);
			bufferedReader = new BufferedReader(fileReader);

		} catch (IOException e) {
			message = "Could not open " + s + ".\n";
		}

	}

	private void count() throws Exception {
		boolean whitespace = true;
		StringBuilder sb = new StringBuilder();
		String s;
		while ((s = bufferedReader.readLine()) != null) {
			s += " "; //handle omission of whitespace at beginning of line
			newlineCount++;
			for (Character c : s.toCharArray()) {

				if (Character.isWhitespace(c)) {
					whitespace = true;
					continue;
				}
				if ((whitespace) && !Character.isWhitespace(c)) {
					wordCount++;
					whitespace = false;
					continue;
				}
			}

		}
		done = true;
		/*
		So, we read in a line using a BufferedReader
		and as long as we have another line
		->
		We walk the string as a char array
		If the last character was a space (the whitespace flag)
		and the current character is not a space
		we have reached a new word
		then we increment the counter
		update last character
		and take a step forward
		thus reading a new character

		 */
	}

	@Override
	public String out() {
		if (!done) {
			return help();
		}
		return wordCount + ".";
	}

	private String format() {
		StringBuilder sb = new StringBuilder();
		switch (currentState) {
			case WORDS:
				sb.append("Words: ").append(wordCount).append(".");
				break;
			case LINES:
				sb.append("Lines: ").append(newlineCount).append(".");
				break;
			case CHARS:
				sb.append("Characters: ").append(charCount).append(".");
				break;
			case ALL:
				//fall through
			default:
				sb.append("Words: ").append(wordCount).append(".\n");
				sb.append("Lines: ").append(newlineCount).append(".\n");
				sb.append("Characters: ").append(charCount).append(".");
		}
		return sb.toString();
	}

	@Override
	public String help() {
		String s;
		String general  = "\nwc - wordcount. \nThis program counts all words for a given file. " +
				"\nWhitespace is used as the delimiter. Empty lines will not be counted." +
				"\nFlags:\n-l\t\tCount all lines.\n-w\t\tCount all words.\n-c\t\tCount all 16-bit Unicode characters.\nDefault (no flags) shows all of these counts.\n\nFor example, to count the lines in file xyz.txt run:\n\t\t\twc -l xyz.txt";

		s = message.isEmpty() ?  message + general : general;
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
		try {
			count();
		} catch (Exception e) {
			return message;
		}
		if (!done) {
			return help();
		}
		return format();
	}


	private enum State {
		CHARS, WORDS, LINES, ALL
	}
}
