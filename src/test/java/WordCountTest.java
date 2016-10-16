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
	public void nullInputShouldTriggerErrorMessage() {
		wordCount.in(null);
		assertEquals("No input detected. Run wc help for more information.", outContent.toString().trim());
	}

	@Test
	public void fileOpenShouldFailGracefully() {
		String s = "false file";
		InputStream is = wordCount.openFile(s);
		//trim because OS adds \n
		assertEquals(null, is);
		assertEquals("Could not open false file.", outContent.toString().trim());
	}

	@Test
	public void helpShouldReturnCorrectly() throws Exception {
		wordCount.in(new String[]{"help"});
		String s = "\nwc - wordcount. \nThis program counts all words for a given file. " +
				"\nWhitespace is used as the delimiter. Empty lines will not be counted." +
				"\nFlags:\n-l\t\tCount all lines.\n-w\t\tCount all words.\n-c\t\tCount all 16-bit Unicode characters.\nDefault (no flags) shows all of these counts.\n\nFor example, to count the lines in file xyz.txt run:\n\t\t\twc -l xyz.txt";
		System.out.print(wordCount.call()); //emulate Cmd call
		assertEquals(s.trim(), outContent.toString().trim());
	}

	@Test
	public void theLineCountAlternativeShouldWork() throws Exception {
		wordCount.in(new String[]{"-l"}); //count only lines
		testStream = new ByteArrayInputStream("this\r\nis\r\nfour\r\nlines.\r\n".getBytes());
		long[] longs = wordCount.processStream(testStream);
		assertEquals(4L, longs[2]); //fetch lines

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
	@Test
	public void countShouldFailGracefully() throws Exception {
		wordCount.in(new String[] {"-l", "-w", "-c", "faux file trololol"});
		//normalize EOL (should fix Travis CI breaking)
		String s = outContent.toString().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\\n");
		assertEquals("Could not open faux file trololol.\nAn error occurred when processing the stream.\n", s);
	}
	@Test
	public void nonsenseInput() throws Exception {
		wordCount.in(new String[] {"wc", "", "-q"});
		System.out.println(wordCount.call());
		assertEquals("No valid input.", outContent.toString().trim());

	}
	@Test
	public void formatShouldCorrectlyPrintAllThreeOptions() throws Exception{
		wordCount.in(new String[] {"-l", "-w", "-c"}); //set flags
		wordCount.updateCounts(new long[] {3, 12, 2});
		assertEquals("Words: 3.\nCharacters: 12.\nLines: 2.\n", wordCount.call().toString());
	}
	@After
	public void cleanUpTests() {
		System.setOut(oldOut);
	}

}
