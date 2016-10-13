package intecmd.commands;

import intecmd.CmdInterface;

import java.io.File;
import java.util.Arrays;

public class WordCount implements CmdInterface {

	private String separator = ""; //whitespace
	private File file;
	private Long count;

	@Override
	public void in(String[] data) {
		parseArguments(data);
	}

	private void parseArguments(String[] data) {
		//TODO: Write code handling flags
		Arrays.stream(data).forEachOrdered(s -> System.out.println(s));

		//TODO: Write code handling file

		//invoke count
		count();
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
		then we increment the counter
		update last character
		and take a step forward
		thus reading a new character

		 */
	}

	@Override
	public String out() {
		return null;
	}

	@Override
	public String help() {
		return null;
	}
}
