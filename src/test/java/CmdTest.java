import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import static org.junit.Assert.*;


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
    public void theCmdShouldSplitAtEachSpaceCharacter() {
        assertEquals(2, cmd.tokenizeString("cd ..").length);
    }

    @Test
    public void theExitCommandShouldCloseTheApplication() {
        expectedSystemExit.expectSystemExit();
        cmd.exitCommand();
    }
}
