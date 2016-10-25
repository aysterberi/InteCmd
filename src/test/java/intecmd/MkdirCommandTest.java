package intecmd;

import intecmd.CurrentDirectory;
import intecmd.commands.MkdirCommand;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class MkdirCommandTest {

    private MkdirCommand mkdir;
    private CurrentDirectory currentDirectory;

    private final String USER_DIR = System.getProperty("user.dir");
    private static final String DIR_NAME_1 = "Directory1";
    private static final String DIR_NAME_2 = "Directory2";
    private static final String DIR_NAME_3 = "Directory3";
    private static final String MKDIR = "mkdir";
    private static final String P = "-p";

    private String[] mkdirArray = {MKDIR, DIR_NAME_1, DIR_NAME_2, DIR_NAME_3};
    private String[] arrParent = {MKDIR, P, DIR_NAME_1, DIR_NAME_2};



    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();


    @Before
    public void init(){
        File dir1 = new File(tempDir.getRoot(), DIR_NAME_1);
        File dir2 = new File(tempDir.getRoot(), DIR_NAME_2);
        File dir3 = new File(tempDir.getRoot(), DIR_NAME_3);
        System.out.println("Directory: " + dir1.getName() + " exists? " + dir1.exists());
        System.out.println("Directory: " + dir2.getName() + " exists? " + dir2.exists());
        System.out.println("Directory: " + dir3.getName() + " exists? " + dir3.exists());

    }


    @Before
    public void initMkdir() {
        mkdir = new MkdirCommand("");
        currentDirectory = new CurrentDirectory();
        currentDirectory.setCurrentDirectory(USER_DIR);
    }


    @Test
    public void temporaryDirectoryTest() throws IOException {
        File dir1 = tempDir.newFolder(DIR_NAME_1);
        File dir2 = tempDir.newFolder(DIR_NAME_2);
        File dir3 = tempDir.newFolder(DIR_NAME_3);
        System.out.println("Directory: " + dir1.getName() + " exists? " + dir1.exists());
        System.out.println("Directory: " + dir2.getName() + " exists? " + dir2.exists());
        System.out.println("Directory: " + dir3.getName() + " exists? " + dir3.exists());


    }


    @Test
    public void getCurrentDirectory() {
        mkdir = new MkdirCommand(currentDirectory.toString());
        assertEquals(USER_DIR, currentDirectory.toString());

    }

    @Test
    public void theDirectoryShouldNotBeNull() throws IOException {
        File dir1 = tempDir.newFolder(DIR_NAME_1);
        mkdir = new MkdirCommand(dir1.getPath());
        assertNotNull(mkdir.getDirectory());

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
    public void directoryIsCreated() throws IOException {
        File dir1 = tempDir.newFolder(DIR_NAME_1);
        assertTrue(dir1.exists());
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
    public void createParentDirectory() throws IOException {
        File dir3 = tempDir.newFolder(DIR_NAME_1, DIR_NAME_2, DIR_NAME_3);
        mkdir = new MkdirCommand(dir3.getPath());
        assertEquals(dir3.getPath(), mkdir.getDirectory().getAbsolutePath());
        System.out.println(mkdir.getDirectory().getAbsolutePath());

    }

    @Test
    public void convertTokensToString() {
        String str = mkdir.getLine(mkdirArray);
        System.out.println(str);

    }

    @Test
    public void convertTokensToParentString() {
        String str = mkdir.getParentLine(arrParent);
        System.out.println(str);

    }

    @Test
    public void theStringShouldSplitAtEachSpaceCharacter()  {
        String[] str = mkdir.getLine(mkdirArray).split(" ");
        assertEquals(3, str.length);
        System.out.println(str.length);

    }

    @Test
    public void theStringShouldSplitAtEachSpaceCharacterParentLine() {
        String[] str = mkdir.getParentLine(arrParent).trim().split(" ");
        assertEquals(2, str.length);
        System.out.println(str.length);

    }


    @Test
    public void theNameWithoutFlagCannotContainBackslash() {
        mkdir.getLineWithoutBackslash(mkdirArray);
        assertTrue(mkdir.getLineWithoutBackslash(mkdirArray));

    }


    @After
    public void setUp() {
        tempDir.delete();
    }

}


