import intecmd.commands.WordCount;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class WordCountTest {

	private WordCount wordCount;
	private File f;

	@Before
	public void setUpTests() {
		wordCount = new WordCount();
		System.out.println(System.getProperty("user.dir"));
		f = new File("test.txt");

	}

	@Test
	public void theIncorrectPathShouldTriggerHelpMessage() throws Exception {
		wordCount.in(new String[]{"wc", "blatantly false file name.txt"});
		assertEquals("Could not open blatantly false file name.txt.\n", wordCount.call());
	}

	@Test
	public void theEmptyStringShouldCountAsZero() throws Exception {
		wordCount.in(new String[]{"wc", "-w", "empty.txt"});
		assertEquals("Words: 0.", wordCount.call());
	}

	@Test
	public void theWordCountShouldBe15() throws Exception {
		wordCount.in(new String[]{"wc", "-w", f.toString()});
		assertEquals("Words: 15.", wordCount.call());
	}
	@Test
	public void thisBigFileShouldWork() throws Exception {
		wordCount.in(new String[] {"bigfile.txt"});
		assertEquals("Words: 2097152.\nLines: 1048576.\nCharacters: 0.", wordCount.call());
	}

	@Test
	public void theLineCountShouldBe3() throws Exception {
		wordCount.in(new String[]{"wc", "-l", f.toString()});
		assertEquals("Lines: 3.", wordCount.call());
	}

	@Test
	public void theTabCharacterShouldCountAsZero() throws Exception{
		wordCount.in(new String[]{"wc", "-w", "tab.txt"});
		assertEquals("Words: 0.", wordCount.call());
	}

	@Test
	public void theNullStringShouldTriggerHelpMessage() throws Exception {
		wordCount.in(new String[]{"wc", ""});
		assertEquals(wordCount.help(), wordCount.call());
	}
}
