import intecmd.Cmd;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WordCountTest {

	private Cmd cmd;

	@Before
	public void setUpTests() {
		cmd = new Cmd();
	}

	@Test
	public void theEmptyStringShouldCountAsZero() {
		assertEquals(cmd.tokenizeString("wc \"\" "), "");
	}
}
