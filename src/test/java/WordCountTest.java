import intecmd.commands.WordCount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class WordCountTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream oldOut = System.out;
	private WordCount wordCount;
	private InputStream testStream;

	@Before
	public void setUpTests() {
		System.setOut(new PrintStream(outContent));
		wordCount = new WordCount();
	}

	@Test
	public void theEmptyStringShouldCountAsZero() throws Exception {
		testStream = new ByteArrayInputStream("".getBytes());
		long[] longs = wordCount.processStream(testStream);
		assertEquals(0L, longs[2]); //fetch lines
	}
	@Test
	public void theCounterShouldCountLines() throws Exception {
		testStream = new ByteArrayInputStream("this\r\nis\r\nfour\r\nlines.\r\n".getBytes());
		long[] longs = wordCount.processStream(testStream);
		assertEquals(4L, longs[2]); //fetch lines
	}
	@Test
	public void theCounterShouldCountCharacters() throws Exception {
		testStream = new ByteArrayInputStream("ῥῶ".getBytes());
		long[] longs = wordCount.processStream(testStream);
		assertEquals(2L, longs[1]); //fetch chars
	}
	@Test
	public void theCounterShouldCountNonbreakingSpace() throws Exception {
		testStream = new ByteArrayInputStream("\u00A0\u00A0\u00A0".getBytes());
		long[] longs = wordCount.processStream(testStream);
		assertEquals(3L, longs[1]);
	}
	@Test
	public void theCounterShouldCountWords() throws Exception {
		testStream = new ByteArrayInputStream("this is now five words.".getBytes());
		long[] longs = wordCount.processStream(testStream);
		assertEquals(5L, longs[0]); //fetch words
	}
	@After
	public void cleanUpTests() {
		System.setOut(oldOut);
	}

}
