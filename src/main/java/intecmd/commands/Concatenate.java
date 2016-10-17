/**
 * A simple Java implementation of the cat command.
 *
 * Concatenates two or more input files
 * and writes them to to standard output.
 *
 *
 * Accepts flag '-u' for unbuffered
 * processing. Preserves bytes.
 *
 *
 */

package intecmd.commands;

import intecmd.CommandInterface;

public class Concatenate implements CommandInterface {
	@Override
	public void in(String[] data) {

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
