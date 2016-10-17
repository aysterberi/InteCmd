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
	public void theTwoInputsShouldConcatenate() throws IOException {
		is = new ByteArrayInputStream("part1".getBytes("UTF-8"));
		cat.cat(is, "file_1");
		is = new ByteArrayInputStream("part2".getBytes("UTF-8"));
		cat.cat(is, "file_2");
		assertEquals("part1part2".trim(), outContent.toString().trim());
	}

	@Test
	public void theSingleInputShouldBePrinted() throws IOException {
		is = new ByteArrayInputStream("this should appear".getBytes());
		cat.cat(is, "file a");
		assertEquals("this should appear", outContent.toString().trim());
	}

	@After
	public void tearDownTests() throws IOException {
		System.setOut(oldOut);
		is.close();
	}
}
