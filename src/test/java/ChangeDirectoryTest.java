import org.junit.Test;
import static org.junit.Assert.*;

public class ChangeDirectoryTest {

    private final String USER_DIRECTORY = System.getProperty("user.dir");

    @Test
    public void initiateChangeDirectory() {
        new ChangeDirectory();
    }

    @Test
    public void getCurrentDirectory() {
        ChangeDirectory cd = new ChangeDirectory();
        assertEquals(cd.currentDirectory(), USER_DIRECTORY);
    }
}
