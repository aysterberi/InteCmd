import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CmdDirectoryTest {
    private LSCommand cmdLS;
    private String defaultPath;

    @Before
    public void setUpTests() {
        defaultPath = "/Program Files/";
    }

    @Test
    public void theFileCanBeEmpty() {
        cmdLS = new LSCommand("");
        assertEquals(cmdLS.getFile().length(), 0);
    }

    @Test
    public void theFileShouldNotBeNull() {
        cmdLS = new LSCommand("//");
        assertNotNull(cmdLS.getFile());
    }


    @Test(expected = NullPointerException.class) @Ignore
    public void theDirectoryListShouldNotBeNull() {
        cmdLS = new LSCommand(defaultPath);
    }

    @Test(expected = NullPointerException.class)
    public void theDirectoryListShouldNotBeEmpty() {
        cmdLS = new LSCommand("//");
        assertEquals(cmdLS.getDirectories().length, 0);
    }

    @Test
    public void theFileListShouldNotBeNull() {
        cmdLS = new LSCommand("/Program Files/");
        assertNotNull(cmdLS.getFiles());
    }

}
