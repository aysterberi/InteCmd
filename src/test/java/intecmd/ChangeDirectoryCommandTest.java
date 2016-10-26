package intecmd;


import intecmd.commands.ChangeDirectoryCommand;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ChangeDirectoryCommandTest {


    private final String USER_SYSTEM = System.getProperty("os.name");
    private final String USER_DIRECTORY = System.getProperty("user.dir");
    private final String HOME_DIRECTORY = System.getProperty("user.home");
    private final String USER_DIRECTORY_SRC = USER_DIRECTORY + CurrentDirectory.SEPARATOR + "src";
    private final String CD_COMMAND = "CD";
    private final String VALID_ROOT = File.listRoots()[0].toString();
    private final String INVALID_ROOT = "XE:/";
    private final String INVALID_COMMAND = "-teasd";
    private final String MOCK_FULL_PATH = "M:" + CurrentDirectory.SEPARATOR + "Parent" + CurrentDirectory.SEPARATOR + "Child";
    private final String MOCK_SHORT_PATH = "M:" + CurrentDirectory.SEPARATOR + "Parent";
    private final String MOCK_WINDOWS_ROOT = "M:";
    private final String MOCK_UNIX_ROOT = "/";

    private CurrentDirectory currentDirectory;
    private ChangeDirectoryCommand cd;
    private MockRoot mockRoot;
    private MockDirectory mockDirectory0, mockDirectory1, mockDirectory2;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void initialize() {
        cd = new ChangeDirectoryCommand();
        currentDirectory = new CurrentDirectory();
        currentDirectory.setCurrentDirectory(USER_DIRECTORY);
    }

    @Test
    public void initiateChangeDirectory() {
        new ChangeDirectoryCommand();
    }

    @Test
    public void getCurrentDirectory() {
        assertEquals(USER_DIRECTORY, currentDirectory.toString());
    }

    @Test
    public void changeCurrentDirectoryToHomeDirectory() {
        cd = new ChangeDirectoryCommand(new String[] {CD_COMMAND, "~"});
        assertEquals(HOME_DIRECTORY, currentDirectory.toString());
    }

    /**
     * Requires mock up.
     */
    @Test
    public void moveUpOneDirectory() {
        if (USER_SYSTEM.startsWith("Windows"))
            setupMockWindowsSystem();
        else
            setupMockUnixSystem();
        cd = new ChangeDirectoryCommand(new String[] {"cd", ".."});
        if (USER_SYSTEM.startsWith("Windows"))
            assertEquals(MOCK_SHORT_PATH, currentDirectory.toString());
        else
            assertEquals(MOCK_UNIX_ROOT + MOCK_SHORT_PATH, currentDirectory.toString());
    }

    /**
     * Requires mock up.
     */
    @Test
    public void shouldNotBeAbleToMoveUpFurtherThanHighestLevel() {
        if (USER_SYSTEM.startsWith("Windows"))
            setupMockWindowsSystem();
        else
            setupMockUnixSystem();
        for(int i = 0; i < 30; i++)
            cd = new ChangeDirectoryCommand(new String[] {"cd", ".."});
        if (USER_SYSTEM.startsWith("Windows"))
            assertEquals(MOCK_WINDOWS_ROOT, currentDirectory.toString());
        else
            assertEquals(MOCK_UNIX_ROOT, currentDirectory.toString());
    }

    @Test
    public void moveDownOneDirectory() {
        cd = new ChangeDirectoryCommand(new String[] {CD_COMMAND, "src"});
        assertEquals(USER_DIRECTORY_SRC, currentDirectory.toString());
    }

    @Test
    public void moveDownOneDirectoryPathDoesNotExistShouldPrintMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        new ChangeDirectoryCommand(new String[] {CD_COMMAND, "jansdljasdlkjakd"});
        assertEquals("No such file or directory.", outContent.toString().trim());
        System.setOut(null);
    }

    @Test
    public void changeToValidRoot() {
        cd = new ChangeDirectoryCommand(new String[] {CD_COMMAND, VALID_ROOT});
        assertEquals(VALID_ROOT, currentDirectory.toString());
    }

    @Test
    public void changingToRootThatDoesNotExistShouldPrintMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        cd = new ChangeDirectoryCommand(new String[] {CD_COMMAND, INVALID_ROOT});
        assertEquals("No such file or directory.", outContent.toString().trim());
        System.setOut(null);
    }

    @Test
    public void invalidCommandShouldPrintMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        cd = new ChangeDirectoryCommand(new String[] {CD_COMMAND, INVALID_COMMAND});
        assertEquals("No such file or directory.", outContent.toString().trim());
        System.setOut(null);
    }

    /**
     * Requires mock up.
     */
    @Test
    public void fileNameHasWhiteSpaces() {
        try {
            File directory = temporaryFolder.newFolder("Test Test Test");
            String path = directory.getAbsolutePath();
            currentDirectory.setCurrentDirectory(path);
            new ChangeDirectoryCommand(new String[] {CD_COMMAND, ".."});
            cd = new ChangeDirectoryCommand(new String[] {CD_COMMAND, "Test", "Test", "Test"});
            assertEquals(directory.getAbsolutePath(), currentDirectory.toString());
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void setupMockWindowsSystem() {
        mockRoot = new MockRoot("M:");
        mockDirectory1 = new MockDirectory("Parent");
        mockDirectory2 = new MockDirectory("Child");
        mockRoot.directories.add(mockDirectory1);
        mockDirectory1.directories.add(mockDirectory2);
        currentDirectory.setCurrentDirectory(MOCK_FULL_PATH);
    }

    private void setupMockUnixSystem() {
        mockRoot = new MockRoot(MOCK_UNIX_ROOT);
        mockDirectory0 = new MockDirectory("M:");
        mockDirectory1 = new MockDirectory("Parent");
        mockDirectory2 = new MockDirectory("Child");
        mockRoot.directories.add(mockDirectory1);
        mockDirectory1.directories.add(mockDirectory2);
        currentDirectory.setCurrentDirectory(MOCK_UNIX_ROOT + MOCK_FULL_PATH);
    }
}
