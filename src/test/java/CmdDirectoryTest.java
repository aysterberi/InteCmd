import intecmd.commands.LSCommand;
import org.junit.Before;
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

    @Test (expected = NullPointerException.class)
    public void theFileShouldNotBeEmpty() {
        cmdLS = new LSCommand("");
        cmdLS.getFile().length();
    }

    @Test (expected = NullPointerException.class)
    public void theFileShouldNotBeNull() {
        cmdLS = new LSCommand("");
        cmdLS.getFile();
    }

    @Test(expected = NullPointerException.class)
    public void theDirectoryListShouldNotBeNull() {
        cmdLS = new LSCommand("");
        cmdLS.getDirectories();
    }

    @Test(expected = NullPointerException.class)
    public void theDirectoryListShouldNotBeEmpty() {
        cmdLS = new LSCommand("");
        cmdLS.getDirectories();
    }

    @Test(expected = NullPointerException.class)
    public void theFileListShouldNotBeNull() {
        cmdLS = new LSCommand("");
        cmdLS.getFiles();
    }

}
