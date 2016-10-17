import intecmd.commands.Concatenate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class ConcatenateTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream oldOut = System.out;
	Concatenate cat;
	InputStream is;

	@Before
	public void setUpTests() {
		System.setOut(new PrintStream(outContent));
		cat = new Concatenate();
	}

	@Test
	public void helpShouldDisplay() {
		cat.in(new String[]{"help"});
		assertEquals("cat - concatenate two more files and send to standard out.\r\nFlags: -u\t\tDo not buffer stream.\r\n", outContent.toString());
	}
	@Test
	public void theTwoInputsShouldConcatenate() throws IOException {
		is = new ByteArrayInputStream("part1".getBytes());
		cat.cat(is, "file_1");
		is = new ByteArrayInputStream("part2".getBytes());
		cat.cat(is, "file_2");
		assertEquals("part1part2", outContent.toString());
	}

	@Test
	public void theSingleInputShouldBePrinted() throws IOException {
		is = new ByteArrayInputStream("this should appear".getBytes());
		cat.cat(is, "file a");
		assertEquals("this should appear", outContent.toString());
	}

	@After
	public void tearDownTests() throws IOException {
		System.setOut(oldOut);
		is.close();
	}
}
