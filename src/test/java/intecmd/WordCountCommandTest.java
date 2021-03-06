package intecmd;

import intecmd.commands.WordCountCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.Current;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class WordCountCommandTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream oldOut = System.out;
	private WordCountCommand wordCountCommand;
	private InputStream testStream;
	private CurrentDirectory directory = new CurrentDirectory();

	@Before
	public void setUpTests() {
		System.setOut(new PrintStream(outContent));
		wordCountCommand = new WordCountCommand();
	}

	@Test
	public void nullInputShouldTriggerErrorMessage() {
		wordCountCommand.in(null);
		assertEquals("No input detected. Run wc help for more information.", outContent.toString().trim());
	}

	@Test
	public void fileOpenShouldFailGracefully() throws IOException {
		String s = "false file";
		InputStream is = wordCountCommand.openFile(s);
		//trim because OS adds \n
		assertEquals(null, is);
		assertEquals("Could not open false file.", outContent.toString().trim());
		try {
		is.close(); } catch (Exception e ) {
			//swallow;
		}
	}

	@Test
	public void helpShouldReturnCorrectly() throws Exception {
		wordCountCommand.in(new String[]{"help"});
		String s = "\nwc - wordcount. \nThis program counts all words for a given file. " +
				"\nWhitespace is used as the delimiter. Empty lines will not be counted." +
				"\nFlags:\n-l\t\tCount all lines.\n-w\t\tCount all words.\n-c\t\tCount all 16-bit Unicode characters.\nDefault (no flags) shows all of these counts.\n\nFor example, to count the lines in file xyz.txt run:\n\t\t\twc -l xyz.txt";
		System.out.print(wordCountCommand.call()); //emulate Cmd call
		assertEquals(s.trim(), outContent.toString().trim());
	}

	@Test
	public void helpFlagShouldWork() throws Exception {
		wordCountCommand.in(new String[]{"-h"});
		String s = "\nwc - wordcount. \nThis program counts all words for a given file. " +
				"\nWhitespace is used as the delimiter. Empty lines will not be counted." +
				"\nFlags:\n-l\t\tCount all lines.\n-w\t\tCount all words.\n-c\t\tCount all 16-bit Unicode characters.\nDefault (no flags) shows all of these counts.\n\nFor example, to count the lines in file xyz.txt run:\n\t\t\twc -l xyz.txt";
		System.out.print(wordCountCommand.call()); //emulate Cmd call
		assertEquals(s.trim(), outContent.toString().trim());
	}
	@Test
	public void theLineCountAlternativeShouldWork() throws Exception {
		wordCountCommand.in(new String[]{"-l"}); //count only lines
		testStream = new ByteArrayInputStream("this\r\nis\r\nfour\r\nlines.\r\n".getBytes());
		long[] longs = wordCountCommand.processStream(testStream);
		assertEquals(4L, longs[2]); //fetch lines

	}

	@Test
	public void theEmptyStringShouldCountAsZero() throws Exception {
		testStream = new ByteArrayInputStream("".getBytes());
		long[] longs = wordCountCommand.processStream(testStream);
		assertEquals(0L, longs[2]); //fetch lines
	}

	@Test
	public void theCounterShouldCountLines() throws Exception {
		testStream = new ByteArrayInputStream("this\r\nis\r\nfour\r\nlines.\r\n".getBytes());
		long[] longs = wordCountCommand.processStream(testStream);
		assertEquals(4L, longs[2]); //fetch lines
	}

	@Test
	public void theCounterShouldCountCharacters() throws Exception {
		testStream = new ByteArrayInputStream("ῥῶ".getBytes());
		long[] longs = wordCountCommand.processStream(testStream);
		assertEquals(2L, longs[1]); //fetch chars
	}

	@Test
	public void theCounterShouldCountNonbreakingSpace() throws Exception {
		testStream = new ByteArrayInputStream("\u00A0\u00A0\u00A0".getBytes());
		long[] longs = wordCountCommand.processStream(testStream);
		assertEquals(3L, longs[1]);
	}

	@Test
	public void theCounterShouldCountWords() throws Exception {
		testStream = new ByteArrayInputStream("this is now five words.".getBytes());
		long[] longs = wordCountCommand.processStream(testStream);
		assertEquals(5L, longs[0]); //fetch words
	}
	@Test
	public void countShouldFailGracefully() throws Exception {
		wordCountCommand.in(new String[] {"-l", "-w", "-c", "faux file trololol"});
		String path = directory.toString() + CurrentDirectory.SEPARATOR;
		//normalize EOL (should fix Travis CI breaking)
		String s = outContent.toString().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\\n");
		assertEquals("Could not open "+path+"faux file trololol.\nAn error occurred when processing the stream.\n", s);
	}
	@Test
	public void nonsenseInput() throws Exception {
		wordCountCommand.in(new String[] {"wc", "", "-q"});
		System.out.println(wordCountCommand.call());
		assertEquals("No valid input. Run wc help for more information.", outContent.toString().trim());
	}
	@Test
	public void theParserShouldTreatEmptyQuotesAsInvalidInput() throws Exception {
		wordCountCommand.in(new String[] {"\"\""});
		System.out.println(wordCountCommand.call());
		assertEquals("No valid input. Run wc help for more information.", outContent.toString().trim());
	}
	@Test
	public void theCallShouldExitWhenWrongFile() throws Exception {
		wordCountCommand.in(new String[] {"-l", "fauxfile.mp3"});
		String path = directory.toString() + CurrentDirectory.SEPARATOR;
		System.out.println(wordCountCommand.call());
		assertEquals("Could not open "+path+"fauxfile.mp3.\n" +
				"An error occurred when processing the stream.\n" +
				"Exiting wc.", outContent.toString().trim().replaceAll("\\r\\n", "\n"));
	}
	@Test
	public void formatShouldCorrectlyPrintAllThreeOptions() throws Exception{
		wordCountCommand.in(new String[] {"-l", "-w", "-c"}); //set flags
		wordCountCommand.updateCounts(new long[] {3, 12, 2});
		assertEquals("Words: 3.\nCharacters: 12.\nLines: 2.\n", wordCountCommand.call().toString());
	}
	@After
	public void cleanUpTests() throws IOException {
		System.setOut(oldOut);
		try {
		testStream.close(); } catch (Exception e) {
			//swallow
		}
	}

}
