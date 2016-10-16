import intecmd.commands.WordCount;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class WordCountTest {

	private WordCount wordCount;
	private InputStream testStream;

	@Before
	public void setUpTests() {
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
		testStream = new ByteArrayInputStream("\u00A0".getBytes());
		long[] longs = wordCount.processStream(testStream);
		assertEquals(1L, longs[1]);
	}
	@Test
	public void theCounterShouldCountWords() throws Exception {
		testStream = new ByteArrayInputStream("this is now five words.".getBytes());
		long[] longs = wordCount.processStream(testStream);
		assertEquals(5L, longs[0]); //fetch words
	}

}
