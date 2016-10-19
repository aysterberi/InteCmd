import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class ChangeDirectoryTest {


    private final String USER_DIRECTORY = System.getProperty("user.dir");
    private final String HOME_DIRECTORY = System.getProperty("user.home");
    private final String USER_DIRECTORY_SRC = USER_DIRECTORY + CurrentDirectory.SEPARATOR + "src";
    private final String CD_COMMAND = "CD";
    private final String MOCK_PATH_WINDOWS = USER_DIRECTORY + CurrentDirectory.SEPARATOR + "src";
    private final String MOCK_PATH_WINDOWS_ROOT = "C:";
    private final String VALID_ROOT = File.listRoots()[0].toString();
    private final String INVALID_ROOT = "XE:/";

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
        currentDirectory.setCurrentDirectory(USER_DIRECTORY_SRC);
        cd = new ChangeDirectory(new String[] {"cd", ".."});
        assertEquals(USER_DIRECTORY, currentDirectory.toString());
    }

    @Test
    public void shouldNotBeAbleToMoveUpFurtherThanHighestLevel() {
        currentDirectory.setCurrentDirectory(HOME_DIRECTORY);
        for(int i = 0; i < 30; i++)
            cd = new ChangeDirectory(new String[] {"cd", ".."});
        assertEquals(VALID_ROOT, currentDirectory.toString() + CurrentDirectory.SEPARATOR);
    }

    @Test
    public void moveDownOneDirectory() {
        cd = new ChangeDirectory(new String[] {CD_COMMAND, "src"});
        assertEquals(USER_DIRECTORY_SRC, currentDirectory.toString());
    }

    @Test
    public void moveDownOneDirectoryPathIsCorrect() {
        cd.moveDown("src");
        assertEquals(USER_DIRECTORY + CurrentDirectory.SEPARATOR + "src", currentDirectory.toString());
    }

    @Test
    public void changeToValidRoot() {
        cd = new ChangeDirectory(new String[] {CD_COMMAND, VALID_ROOT});
        assertEquals(VALID_ROOT, currentDirectory.toString());
    }

    @Test
    public void shouldNotBeAbleToChangeToRootThatDoesNotExist() {
        cd = new ChangeDirectory(new String[] {CD_COMMAND, INVALID_ROOT});
        assertEquals(currentDirectory.toString(), USER_DIRECTORY);
    }
}
