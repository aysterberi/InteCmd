import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 * Created by Mattin on 2016-10-11.
 */
public class CmdDirectoryTest {
    private CmdLS cmdLS;
    private String defaultPath;

    @Before
    public void setUpTests() {
        defaultPath = "/Program Files/";
    }

    @Test
    public void testIfFileEmpty() {
        cmdLS = new CmdLS("");
        assertEquals(cmdLS.getFile().length(), 0);
    }

    @Test
    public void testIfFileNotNull() {
        cmdLS = new CmdLS("//");
        assertNotNull(cmdLS.getFile());
    }


    @Test
    public void testIfDirectoryListIsNull() {
        cmdLS = new CmdLS(defaultPath);
        assertEquals(cmdLS.getDirectories(), null);
//        cmdLS = new CmdLS("");
//        assertEquals(cmdLS.getDirectories(), ExpectedException.none());
    }

    @Test
    public void testIfDirectoryListIsEmpty() {
        cmdLS = new CmdLS("//");
        assertEquals(cmdLS.getDirectories().length, 0);
    }

    @Test
    public void testIfFilesListIsNull() {
        cmdLS = new CmdLS("/Program Files/");
        assertNotNull(cmdLS.getFiles());
    }

}
