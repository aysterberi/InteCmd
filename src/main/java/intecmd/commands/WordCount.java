package intecmd.commands;

import intecmd.CommandInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class WordCount implements CommandInterface, Callable {

	private Logger log;
	private State currentState = State.ALL;
	private File file;
	private String message;
	private Long charCount = 0l;
	private Long wordCount = 0l;
	private Long newlineCount = 0l;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private boolean done;

	public WordCount() {
		log = Logger.getLogger(WordCount.class.getName());
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
				case " ":
					break; //whitespace
				case "-c":
					currentState = State.CHARS;
					break;
				case "-l":
					currentState = State.LINES;
					break;
				case "-w":
					currentState = State.WORDS;
					break;
				default:
					tryOpenFile(data[i]);
			}
		}
	}

	private void tryOpenFile(String s) {
		try {
			fileReader = new FileReader(s);
			bufferedReader = new BufferedReader(fileReader);

		} catch (IOException e) {
			message = "Could not open " + s + ".\n";
			System.out.printf("Could not open %s.%n", file.toString());
		}

	}

	private void count() throws Exception {
		boolean whitespace = false;
		StringBuilder sb = new StringBuilder();
		String s;
		while ((s = bufferedReader.readLine()) != null) {
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
			done = true;
		}
		/*
		So, we read in a line using scanner
		and as long as we have another line
		->
		We walk the string as a char array
		If the last character was a space
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
				sb.append("Words: ").append(wordCount).append(".");
				sb.append("Lines: ").append(newlineCount).append(".");
				sb.append("Characters: ").append(charCount).append(".");
		}
		return sb.toString();
	}

	@Override
	public String help() {
		String s;
		String message = "";
		s = message + "\nwc - wordcount. \nThis program counts all words for a given input. \nWhitespace is used as the default delimiter.";
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
			return message;
		}
		return format();
	}


	private enum State {
		CHARS, WORDS, LINES, ALL
	}
}
