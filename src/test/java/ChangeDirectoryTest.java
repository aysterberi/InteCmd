import intecmd.commands.ChangeDirectory;
import intecmd.CurrentDirectory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChangeDirectoryTest {

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

    //@Test
    //public void moveUpOneDirectory() {
    //    cd.moveUp();
    //    assertEquals("D:\\development", currentDirectory.toString());
    //}

    //@Test
    //public void shouldNotBeAbleToMoveUpFurtherThanHighestLevel() {
    //    for(int i = 0; i < 10; i++)
    //        cd.moveUp();
    //    assertEquals("D:", currentDirectory.toString());
    //}

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
