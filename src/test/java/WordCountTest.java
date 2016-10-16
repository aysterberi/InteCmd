import intecmd.commands.WordCount;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class WordCountTest {

	private WordCount wordCount;

	@Before
	public void setUpTests() {
		wordCount = new WordCount();
	}

	@Test
	public void theEmptyStringShouldCountAsZero() throws Exception {
		wordCount.in(new String[]{"wc", "-w", "empty.txt"});
		assertEquals("Words: 0.", wordCount.call());
	}
	@Test
	public void theCounterShouldCountLines() throws Exception {
		InputStream testStream = new ByteArrayInputStream("this\r\nis\r\nfour\r\nlines.\r\n".getBytes());
		long[] longs = wordCount.processStream(testStream);
		assertEquals(4L, longs[2]); //fetch lines
	}
	@Test
	public void theCounterShouldCountWords() throws Exception {
		InputStream testStream = new ByteArrayInputStream("this is now five words.".getBytes());
		long[] longs = wordCount.processStream(testStream);
		assertEquals(5L, longs[0]); //fetch words
	}

}
