import intecmd.CurrentDirectory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CurrentDirectoryTest {

    private final String VALID_PATH = System.getProperty("user.dir") + CurrentDirectory.SEPARATOR + "src";
    private CurrentDirectory currentDirectory;

    @Before
    public void initialize() {
        currentDirectory = new CurrentDirectory();
    }

    @Test
    public void getCurrentDirectoryAsString() {
        assertTrue(currentDirectory.toString() instanceof String);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCurrentDirectoryAsNullShouldThrowIllegalArgumentException() {
        currentDirectory.setCurrentDirectory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCurrentDirectoryAsHigherThanRootShouldThrowIllegalArgumentException() {
        if(System.getProperty("os.name").startsWith("Windows"))
            currentDirectory.setCurrentDirectory(CurrentDirectory.SEPARATOR);
        else
            currentDirectory.setCurrentDirectory("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCurrentDirectoryWithEmptyStringShouldThrowIllegalArgumentException() {
        currentDirectory.setCurrentDirectory("");
    }

    @Test
    public void setCurrentDirectoryShouldAcceptValidPath() {
        currentDirectory.setCurrentDirectory(VALID_PATH);
    }
}
