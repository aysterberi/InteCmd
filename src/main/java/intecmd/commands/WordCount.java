package intecmd.commands;

import intecmd.CmdInterface;

import java.io.File;
import java.util.Arrays;

public class WordCount implements CmdInterface {

	private String separator = ""; //whitespace
	private File file;
	private String currentLine;
	private Long count = 0l;

	@Override
	public void in(String[] data) {
		parseArguments(data);
	}

	private void parseArguments(String[] data) {
		//TODO: Write code handling flags
		Arrays.stream(data).forEachOrdered(s ->
				//do something to handle flags
				System.out.println(s)
		);
		if(data.length == 1)
		{
			System.out.print(help());
		}
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
		we have reached a new word
		then we increment the counter
		update last character
		and take a step forward
		thus reading a new character

		 */
	}

	@Override
	public String out() {
		return count.toString()+".";
	}

	@Override
	public String help() {
		String s;
		s = "wc \nWord counter, written by Billy, 2016, counts all words in a given input. \nWhitespace is used as the default delimiter.";
		return s;
	}
}
