import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChangeDirectoryTest {

    private final String USER_DIRECTORY = System.getProperty("user.dir");
    private final String HOME_DIRECTORY = System.getProperty("user.home");

    private ChangeDirectory cd;

    @Before
    public void initialize() {
        cd = new ChangeDirectory();
    }

    @Test
    public void initiateChangeDirectory() {
        new ChangeDirectory();
    }

    @Test
    public void getCurrentDirectory() {
        assertEquals(cd.currentDirectory(), USER_DIRECTORY);
    }

    @Test
    public void changeCurrentDirectoryToHomeDirectory() {
        assertEquals(cd.homeDirectory(), HOME_DIRECTORY);
    }
}
