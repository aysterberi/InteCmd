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

import java.io.*;

public class Concatenate implements CommandInterface {
	private boolean isBuffered = true;
	@Override
	public void in(String[] data) {

		for(String s : data) {
			if (s.equals("-u"))
			{
				isBuffered = false;
				continue;
			} else if (s.equals("help")) {
				System.out.println(help());
				return; //exit
			} else {
				if(s.equals("cat"))
				{
					continue; //do nothing
				}
				try {
					FileInputStream fis = new FileInputStream(s);
					cat(fis, s);
					fis.close();
				} catch (java.io.IOException e) {
					System.out.println("Could not open " + s);
				}
			}
		}

	}

	public void cat(InputStream is, String fileName) {

		byte[] buf = new byte[8192]; //8KB
		int r;
		try {
		if(isBuffered){
			while((r = is.read(buf)) != -1){
				try {
					System.out.write(buf, 0, r);
				} catch (Exception e) {
					System.out.println("Error writing " + fileName);
				}
			}
		} else if (!isBuffered) {
			while((r = is.read()) != -1) {
				System.out.write(r);
			}
		}  } catch (IOException e) {
			System.out.println("Error reading from " + fileName);
		}
	}
	@Override
	public String help() {
		String h = "cat - concatenate two more files and send to standard out.\n" +
				"Flags: -u\t\tDo not buffer stream.";
		return h;
	}
}
