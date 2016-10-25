package intecmd;

import intecmd.CurrentDirectory;
import intecmd.commands.MkdirCommand;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;


public class MkdirCommandTest {

    private MkdirCommand mkdir;
    private CurrentDirectory currentDirectory;

    private final String USER_DIR = System.getProperty("user.dir");
    private static final String DIR_NAME_1 = "Directory1";
    private static final String DIR_NAME_2 = "Directory2";
    private static final String DIR_NAME_3 = "Directory3";
    private static final String MKDIR = "mkdir";
    private static final String P = "-p";
    private static final String TEST_STR = "Directory1 Directory2 Directory3";

    private String[] mkdirArray = {MKDIR, DIR_NAME_1, DIR_NAME_2, DIR_NAME_3};
    private String[] arrParent = {MKDIR, P, DIR_NAME_1, DIR_NAME_2, DIR_NAME_3};



    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();


    @Before
    public void initMkdir() {
        mkdir = new MkdirCommand("");
        currentDirectory = new CurrentDirectory();
        currentDirectory.setCurrentDirectory(USER_DIR);
    }


    @Before
    public void init(){
        File dir1 = new File(tempDir.getRoot(), DIR_NAME_1);
        File dir2 = new File(tempDir.getRoot(), DIR_NAME_2);
        File dir3 = new File(tempDir.getRoot(), DIR_NAME_3);
        assertFalse(dir1.exists());
        assertFalse(dir2.exists());
        assertFalse(dir3.exists());

    }


    @Test
    public void temporaryDirectoryTest() throws IOException {
        File dir1 = tempDir.newFolder(DIR_NAME_1);
        File dir2 = tempDir.newFolder(DIR_NAME_2);
        File dir3 = tempDir.newFolder(DIR_NAME_3);
        assertTrue(dir1.exists());
        assertTrue(dir2.exists());
        assertTrue(dir3.exists());

    }

    @Test
    public void testConstructor(){
        mkdir = new MkdirCommand(mkdirArray);
    }

    @Test
    public void testConstructorWithFlag(){
        mkdir = new MkdirCommand(arrParent);
    }


    @Test(expected = NullPointerException.class)
    public void theDirectoryShouldNotBeNull() {
        mkdir = new MkdirCommand("");
        fail(mkdir.getDirectory().getName());
    }


    @Test
    public void theDirectoryAlreadyExists() throws IOException {
        File dir1 = tempDir.newFolder(DIR_NAME_1);
        mkdir = new MkdirCommand(dir1.getPath());
        assertTrue(mkdir.getDirectory().exists());
    }


    @Test
    public void directoryCreated() throws IOException {
        File dir1 = tempDir.newFolder(DIR_NAME_1);
        mkdir = new MkdirCommand(dir1.getPath());
        assertEquals(dir1.getName(), mkdir.getDirectory().getName());

    }


    @Test
    public void parentDirectoryCreated() throws IOException {
        File dir3 = tempDir.newFolder(DIR_NAME_1, DIR_NAME_2, DIR_NAME_3);
        mkdir = new MkdirCommand(dir3.getPath());
        assertEquals(dir3.getPath(), mkdir.getDirectory().getPath());

    }


    @Test
    public void multipleDirectoriesAreCreated() throws IOException {
        File dir1 = tempDir.newFolder(DIR_NAME_1);
        File dir2 = tempDir.newFolder(DIR_NAME_2);
        File dir3 = tempDir.newFolder(DIR_NAME_3);
        mkdir = new MkdirCommand(dir1.getPath());
        mkdir = new MkdirCommand(dir2.getPath());
        mkdir = new MkdirCommand(dir3.getPath());
        assertTrue(dir1.exists());
        assertTrue(dir2.exists());
        assertTrue(dir3.exists());

    }

    @Test
    public void multiplePArentDirectoriesAreCreated() throws IOException {
        File dir1 = tempDir.newFolder(DIR_NAME_1, DIR_NAME_2, DIR_NAME_3);
        File dir2 = tempDir.newFolder(DIR_NAME_3, DIR_NAME_2, DIR_NAME_1);
        File dir3 = tempDir.newFolder(DIR_NAME_2, DIR_NAME_3, DIR_NAME_1);
        mkdir = new MkdirCommand(dir1.getPath());
        mkdir = new MkdirCommand(dir2.getPath());
        mkdir = new MkdirCommand(dir3.getPath());
        assertTrue(dir1.exists());
        assertTrue(dir2.exists());
        assertTrue(dir3.exists());

    }


    @Test
    public void convertTokensToString() {
        String str = mkdir.getLine(mkdirArray);
        assertEquals(TEST_STR, str);
    }


    @Test
    public void convertTokensToParentString() {
        String str = mkdir.getParentLine(arrParent).trim();
        assertEquals(TEST_STR, str);

    }

    @Test
    public void theStringShouldSplitAtEachSpaceCharacter()  {
        String[] str = mkdir.getLine(mkdirArray).split(" ");
        assertEquals(3, str.length);

    }

    @Test
    public void theStringShouldSplitAtEachSpaceCharacterParentLine() {
        String[] str = mkdir.getParentLine(arrParent).trim().split(" ");
        assertEquals(3, str.length);

    }


    @Test
    public void theNameWithoutFlagCannotContainBackslash() {
        assertTrue(mkdir.getLineWithoutBackslash(mkdirArray));

    }


}


