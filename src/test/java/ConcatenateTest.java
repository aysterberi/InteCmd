import intecmd.commands.Concatenate;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
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
	public void theTwoInputsShouldConcatenate() {
		is = new ByteArrayInputStream("part1".getBytes());
		cat.cat(is, "file_1");
		is = new ByteArrayInputStream("part2".getBytes());
		cat.cat(is, "file_2");
		assertEquals("partpart2", outContent.toString().trim());
	}
}
