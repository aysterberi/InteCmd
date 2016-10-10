import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito.*;

import static org.junit.Assert.*;


public class CmdTest {

    private Cmd cmd;

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
}
