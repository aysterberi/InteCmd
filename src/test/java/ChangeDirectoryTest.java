import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ChangeDirectoryTest {


    private final String USER_DIRECTORY = System.getProperty("user.dir");
    private final String HOME_DIRECTORY = System.getProperty("user.home");
    private final String USER_DIRECTORY_SRC = USER_DIRECTORY + CurrentDirectory.SEPARATOR + "src";
    private final String USER_DIRECTORY_CHILD = USER_DIRECTORY + CurrentDirectory.SEPARATOR + "ChildTempDirectory";
    private final String CD_COMMAND = "CD";
    private final String MOCK_PATH_WINDOWS = USER_DIRECTORY + CurrentDirectory.SEPARATOR + "src";
    private final String MOCK_PATH_WINDOWS_ROOT = "C:";
    private final String VALID_ROOT = File.listRoots()[0].toString();
    private final String INVALID_ROOT = "XE:/";

    private ChangeDirectory cd;
    private CurrentDirectory currentDirectory;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

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

    private final String MOCK_FULL_PATH = "M:" + CurrentDirectory.SEPARATOR + "Parent" + CurrentDirectory.SEPARATOR + "Child";
    private final String MOCK_SHORT_PATH = "M:" + CurrentDirectory.SEPARATOR + "Parent";
    private final String MOCK_WINDOWS_ROOT = "M:";
    private final String MOCK_UNIX_ROOT = "/";
    private MockRoot mockRoot;
    private MockDirectory mockDirectory1, mockDirectory2;

    private void setupMockSystem() {
        mockRoot = new MockRoot("M:");
        mockDirectory1 = new MockDirectory("Parent");
        mockDirectory2 = new MockDirectory("Child");
        mockRoot.directories.add(mockDirectory1);
        mockDirectory1.directories.add(mockDirectory2);

    }

    /**
     * Requires mock up.
     */
    @Test
    public void moveUpOneDirectory() {
        setupMockSystem();
        currentDirectory.setCurrentDirectory(MOCK_FULL_PATH);
        cd = new ChangeDirectory(new String[] {"cd", ".."});
        assertEquals(MOCK_SHORT_PATH, currentDirectory.toString());
    }

    /**
     * Requires mock up.
     */
    @Test
    public void shouldNotBeAbleToMoveUpFurtherThanHighestLevel() {
        setupMockSystem();
        currentDirectory.setCurrentDirectory(MOCK_FULL_PATH);
        for(int i = 0; i < 30; i++)
            cd = new ChangeDirectory(new String[] {"cd", ".."});
        if (System.getProperty("os.name").startsWith("Windows"))
            assertEquals(MOCK_WINDOWS_ROOT, currentDirectory.toString());
        else
            assertEquals(MOCK_UNIX_ROOT, currentDirectory.toString());
    }

    @Test
    public void moveDownOneDirectory() {
        cd = new ChangeDirectory(new String[] {CD_COMMAND, "src"});
        assertEquals(USER_DIRECTORY_SRC, currentDirectory.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveDownOneDirectoryPathDoesNotExistShouldThorIllegalArgumentException() {
        cd.moveDown("jansdljasdlkjakd");
    }

    @Test
    public void changeToValidRoot() {
        cd = new ChangeDirectory(new String[] {CD_COMMAND, VALID_ROOT});
        assertEquals(VALID_ROOT, currentDirectory.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToChangeToRootThatDoesNotExist() {
        cd.changeRoot(INVALID_ROOT);
    }

    @Test
    public void invalidCommandShouldPrintMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        cd = new ChangeDirectory(new String[] {CD_COMMAND, INVALID_ROOT});
        assertEquals("No such file or directory.", outContent.toString().trim());
        System.setOut(null);
    }

    /**
     * Requires mock up.
     */
    @Test
    public void fileNameHasWhiteSpaces() {
        cd = new ChangeDirectory(new String[] {CD_COMMAND, "Test", "Test", "Test"});
        assertEquals(USER_DIRECTORY + CurrentDirectory.SEPARATOR + "Test Test Test", currentDirectory.toString());
    }
}
