import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChangeDirectoryTest {

    private final String MOCK_PATH_WINDOWS = "C:\\Users\\Test User\\AppData";
    private final String MOCK_PATH_WINDOWS_ROOT = "C:";
    private final String MOCK_PATH_WINDOWS_UP_ONE_LEVEL = "C:\\Users\\Test User";
    private final String USER_DIRECTORY = System.getProperty("user.dir");
    private final String HOME_DIRECTORY = System.getProperty("user.home");

    private ChangeDirectory cd;
    private CurrentDirectory currentDirectory;

    @Before
    public void initialize() {
        cd = new ChangeDirectory();
        currentDirectory = new CurrentDirectory();
        currentDirectory.setCurrentDirectory(USER_DIRECTORY);
    }

    @Test
    public void initiateChangeDirectory() {
        new ChangeDirectory();
    }

    @Test
    public void getCurrentDirectory() {
        assertEquals(USER_DIRECTORY, currentDirectory.toString());
    }

    @Test
    public void changeCurrentDirectoryToHomeDirectory() {
        cd.homeDirectory();
        assertEquals(HOME_DIRECTORY, currentDirectory.toString());
    }

    @Test
    public void moveUpOneDirectory() {
        cd.moveUp(MOCK_PATH_WINDOWS);
        assertEquals(MOCK_PATH_WINDOWS_UP_ONE_LEVEL, currentDirectory.toString());
    }

    @Test
    public void shouldNotBeAbleToMoveUpFurtherThanHighestLevel() {
        currentDirectory.setCurrentDirectory(MOCK_PATH_WINDOWS);
        for(int i = 0; i < 10; i++)
            cd.moveUp(currentDirectory.toString());
        assertEquals(MOCK_PATH_WINDOWS_ROOT, currentDirectory.toString());
    }

    @Test
    public void moveDownOneDirectory() {
        assertTrue(cd.moveDown("src"));
    }

    @Test
    public void moveDownOneDirectoryPathIsCorrect() {
        cd.moveDown("src");
        assertEquals(USER_DIRECTORY + "\\src", currentDirectory.toString());
    }
}
