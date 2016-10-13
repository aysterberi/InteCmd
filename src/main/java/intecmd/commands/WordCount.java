package intecmd.commands;

import intecmd.CmdInterface;

import java.io.File;
import java.util.Arrays;

public class WordCount implements CmdInterface {

	private String separator = ""; //whitespace
	private File file;

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
		//TODO:
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
