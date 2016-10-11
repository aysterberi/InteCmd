import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CmdTest {

    private Cmd cmd;

    @Rule
    public final ExpectedSystemExit expectedSystemExit = ExpectedSystemExit.none();

    @Before
    public void setUpTests() {
        cmd = new Cmd();
    }

    @Test
    public void theCmdShouldNotBeNull() {
        assertNotNull(cmd);
    }

    @Test
    public void theTokenizerShouldSplitAtEachSpaceCharacter() {
        assertEquals(2, cmd.tokenizeString("cd ..").length);
    }

    @Test
    public void theEchoCommandShouldReturnTheCorrectString() {
        String[] arr = {"echo", "test", "the", "echo", "command"};
        assertEquals("test the echo command", cmd.echoCommand(arr));
    }

    @Test
    public void theTokenizerShouldNotSplitBetweenQuotationMarks() {
        assertEquals(3, cmd.tokenizeString("this \"should have a length of\" three").length);
    }

    @Test
    public void theTokenizerShouldHandleSingleWordQuotes() {
        assertEquals(6, cmd.tokenizeString("this strings \"length\" should be six").length);
    }

    @Test
    public void theExitCommandShouldCloseTheApplication() {
        expectedSystemExit.expectSystemExit();
        cmd.exitCommand();
    }
}
