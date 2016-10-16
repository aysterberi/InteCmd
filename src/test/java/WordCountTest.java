import intecmd.Cmd;
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
		f = new File("ajfgiuwq");

	}

	@Test
	public void theIncorrectPathShouldTriggerHelpMessage(){
		wordCount.in(new String[] {"wc " + f.toPath()} );
		assertEquals(wordCount.help(), wordCount.out() );
	}
	@Test
	public void theEmptyStringShouldCountAsZero() {
		wordCount.in(new String[]{"wc \"\""});
		assertEquals("0.", wordCount.out() );
	}

	@Test
	public void theStringShouldCountAsFive() {
		wordCount.in(new String[] {"wc \"this is a test sentence.\""});
		assertEquals("5.", wordCount.out());
	}

	@Test
	public void theTabCharacterShouldCountAsZero() {
		wordCount.in(new String[] {"wc \"\t\t\t\t\""});
		assertEquals("0.", wordCount.out());
	}

	@Test
	public void theNullStringShouldTriggerHelpMessage() {
		wordCount.in(new String[] {"wc "});
		assertEquals(wordCount.help(), wordCount.out());
	}
}
