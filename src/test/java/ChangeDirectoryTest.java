import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ChangeDirectoryTest {


    private final String USER_DIRECTORY = System.getProperty("user.dir");
    private final String HOME_DIRECTORY = System.getProperty("user.home");
    private final String CD_COMMAND = "CD";
    private final String MOCK_PATH_WINDOWS = USER_DIRECTORY + CurrentDirectory.SEPARATOR + "src";
    private final String MOCK_PATH_WINDOWS_ROOT = "C:";

    private String validRoot1, validRoot2;
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
        currentDirectory.setCurrentDirectory(MOCK_PATH_WINDOWS);
        cd = new ChangeDirectory(new String[] {"cd", ".."});
        assertEquals(USER_DIRECTORY, currentDirectory.toString());
    }

    @Test
    public void shouldNotBeAbleToMoveUpFurtherThanHighestLevel() {
        currentDirectory.setCurrentDirectory(HOME_DIRECTORY);
        for(int i = 0; i < 10; i++)
            cd = new ChangeDirectory(new String[] {"cd", ".."});
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

    @Test
    public void changeToValidRoot() {
        validRoot1 = File.listRoots()[0].toString();
        cd = new ChangeDirectory(new String[] {CD_COMMAND, validRoot1});
        assertEquals(validRoot1, currentDirectory.toString());
    }

    @Test
    public void changeFromRoot1ToRoot2IfSystemHasTwoOrMoreRoots() {
        validRoot1 = File.listRoots()[0].toString();
        if(systemHasTwoOrMoreRoots()) {
            validRoot2 = File.listRoots()[0].toString();
            cd = new ChangeDirectory(new String[] {CD_COMMAND, validRoot2});
        }
        cd = new ChangeDirectory(new String[] {CD_COMMAND, validRoot1});
        assertEquals(validRoot1, currentDirectory.toString());
    }

    private boolean systemHasTwoOrMoreRoots() {
        return File.listRoots().length > 1;
    }
}
