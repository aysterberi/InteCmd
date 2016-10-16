package intecmd.commands;

import intecmd.CommandInterface;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class WordCount implements CommandInterface {

	private Logger log;
	private State currentState = State.ALL;
	private String message = "";
	private File file;
	private Long charCount;
	private Long wordCount;
	private Long newlineCount;
	private Scanner scanner;
	private boolean done;

	public WordCount() {
		log = Logger.getLogger(WordCount.class.getName());
	}

	@Override
	public void in(String[] data) {
		parseArguments(data);
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
					count();
			}
		}
	}

	private void tryOpenFile(String s) {
		file = new File(s);
		try {
			scanner = new Scanner(file);

		} catch (IOException e) {
			System.out.printf("Could not open {0}.", file);
		}

	}

	private void count() {
		boolean whitespace = false;
		while (scanner.hasNextLine()) {
			String s = scanner.nextLine();
			for (Character c : s.toCharArray()
					) {
				if (Character.isWhitespace(c)) {
					whitespace = true;
					continue;
				}
				if ((whitespace) && !Character.isWhitespace(c))
				{
					wordCount++;
					whitespace = false;
					continue;
				}

			}
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

	@Override
	public String help() {
		String s;
		s = message + "\nwc - wordcount. \nThis program counts all words for a given input. \nWhitespace is used as the default delimiter.";
		return s;
	}


	private enum State {
		CHARS, WORDS, LINES, ALL
	}
}
