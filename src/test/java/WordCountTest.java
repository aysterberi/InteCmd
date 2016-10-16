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
	public void theEmptyStringShouldCountAsZero() {
		wordCount.in(new String[]{"wc", "\"\""});
		assertEquals("0.", wordCount.out());
	}

	@Test
	public void theStringShouldCountAsFive() {
		wordCount.in(new String[]{"wc", "\"this is a test sentence.\""});
		assertEquals("5.", wordCount.out());
	}

	@Test
	public void theTabCharacterShouldCountAsZero() {
		wordCount.in(new String[]{"wc", "\"\t\t\t\t\""});
		assertEquals("0.", wordCount.out());
	}

	@Test
	public void theNullStringShouldTriggerHelpMessage() {
		wordCount.in(new String[]{"wc", ""});
		assertEquals(wordCount.help(), wordCount.out());
	}
}
