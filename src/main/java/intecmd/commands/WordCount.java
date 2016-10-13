package intecmd.commands;

import intecmd.CommandInterface;

import java.io.File;
import java.util.Arrays;

public class WordCount implements CommandInterface {

	private String separator = ""; //whitespace
	private String message = "";
	private File file;
	private String currentLine;
	private Long count = 0l;
	private boolean done;

	@Override
	public void in(String[] data) {
		parseArguments(data);
	}

	private void parseArguments(String[] data) {
		Arrays.stream(data).forEachOrdered(s ->
				//do something to handle flags
		{
			if (s.codePointAt(0) == '-') {
				parseFlag(s);
				return;
			}
			if (s.codePointAt(0) == ' ') {
				//return;
			}
			try {
				file = new File(s);
			} catch (Exception e) {
				e.printStackTrace();
				message = "Incorrect file or input.";
			}

		});

		//TODO: Write code handling file

		//invoke count
		count();
	}

	private void parseFlag(String s) {
		//TODO: Write code handling flags
	}


	private void count() {
		//TODO: Implement tokenizer

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
		return count.toString() + ".";
	}


	@Override
	public String help() {
		String s;
		s = message +"\nwc \nWord counter, written and maintained by @chrysophylax. \nThis program counts all words for a given input. \nWhitespace is used as the default delimiter.";
		return s;
	}
}
