import intecmd.Cmd;
import intecmd.commands.WordCount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WordCountTest {

	private WordCount wordCount;

	@Before
	public void setUpTests() {
		wordCount = new WordCount();

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
	public void theNullStringShouldTriggerErrorMessage() {
		wordCount.in(new String[] {"wc "});
		assertEquals(wordCount.help(), wordCount.out());
	}
}
