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
    private static final String DIR_NAME = "New_Directory";


    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();


    @Before
    public void init(){
        File dir = new File(tempDir.getRoot(), DIR_NAME);
        System.out.println("Directory: " + dir.getName() + " exists? " + dir.exists());
    }


    @Before
    public void initMkdir() {
        mkdir = new MkdirCommand("");
        currentDirectory = new CurrentDirectory();
        currentDirectory.setCurrentDirectory(USER_DIR);
    }

    @Test
    public void temporaryDirectoryTest() throws IOException {
        File dir = tempDir.newFolder(DIR_NAME);
        System.out.println("Directory: " + dir.getName() + " exists? " + dir.exists());

    }

    @Test
    public void getCurrentDirectory() {
        mkdir = new MkdirCommand(currentDirectory.toString());
        assertEquals(USER_DIR, currentDirectory.toString());

    }

    @Test
    public void theDirectoryShouldNotBeNull() throws IOException {
        File dir = tempDir.newFolder(DIR_NAME);
        mkdir = new MkdirCommand(dir.getPath());
        assertNotNull(mkdir.getDirectory());

    }

    @Test
    public void theDirectoryAlreadyExists() throws IOException {
        File dir = tempDir.newFolder(DIR_NAME);
        mkdir = new MkdirCommand(dir.getPath());
        assertTrue(mkdir.getDirectory().exists());
    }


    @Test
    public void directoryCreated() throws IOException {
        File dir = tempDir.newFolder(DIR_NAME);
        mkdir = new MkdirCommand(dir.getPath());
        assertEquals(dir.getName(), mkdir.getDirectory().getName());

    }


    @Test
    public void directoryIsCreated() throws IOException {
        File dir3 = tempDir.newFolder(DIR_NAME);
        assertTrue(dir3.exists());
    }


    @After
    public void setUp() {
        tempDir.delete();
    }

}


