import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CurrentDirectoryTest {

    CurrentDirectory currentDirectory;

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
        currentDirectory.setCurrentDirectory("/");
    }
}
