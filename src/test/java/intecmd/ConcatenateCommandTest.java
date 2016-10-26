package intecmd;

import intecmd.commands.ConcatenateCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class ConcatenateCommandTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream oldOut = System.out;
	ConcatenateCommand cat;
	InputStream is;

	@Before
	public void setUpTests() {
		System.setOut(new PrintStream(outContent));
		cat = new ConcatenateCommand();
	}

	@Test
	public void helpShouldDisplay() {
		cat.in(new String[]{"help"});
		assertEquals("cat - concatenate two more files and send to standard out.\nFlags: -u\t\tDo not buffer stream.\n", outContent.toString().replaceAll("\\r\\n", "\n"));
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
	public void thebufferedAndUnbufferedModesShouldReturnSame() throws IOException {
		String testString = "this should return the same";
		cat.in(new String[]{"-u"}); //unbuffered
		is = new ByteArrayInputStream(testString.getBytes());
		cat.cat(is, "file 1");
		String unbufOut = outContent.toString();
		outContent.reset();

		is = new ByteArrayInputStream(testString.getBytes());
		cat = new ConcatenateCommand();
		cat.cat(is, "file 2"); //8K buffered
		String bufOut = outContent.toString();
		assertEquals(unbufOut, bufOut);

	}
	@Test
	public void theSingleInputShouldBePrinted() throws IOException {
		is = new ByteArrayInputStream("this should appear".getBytes());
		cat.cat(is, "file a");
		assertEquals("this should appear", outContent.toString());
	}

	@After
	public void tearDownTests(){
		System.setOut(oldOut);
		try {
		is.close(); } catch (Exception e)
		{
			//IS was not used in a test
		}
	}
}
