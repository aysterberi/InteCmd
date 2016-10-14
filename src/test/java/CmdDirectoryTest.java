import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class CmdDirectoryTest {
    private LSCommand cmdLS;
    private String defaultPath;
    private File mockedFile;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUpTests() {
        System.setOut(new PrintStream(outContent));

//        File mockedFile = Mockito.mock(File.class);
//        Mockito.when(mockedFile.exists()).thenReturn(true);
    }

    @Test (expected = NullPointerException.class)
    public void theFileShouldNotBeEmpty() {
        cmdLS = new LSCommand("");
        cmdLS.getFile().length();
    }

    @Test (expected = NullPointerException.class)
    public void theFileShouldNotBeNull() {
        cmdLS = new LSCommand("");
        cmdLS.getFile();
    }

    @Test(expected = NullPointerException.class)
    public void theDirectoryListShouldNotBeNull() {
        cmdLS = new LSCommand("");
        cmdLS.getDirectories();
    }

    @Test(expected = NullPointerException.class)
    public void theDirectoryListShouldNotBeEmpty() {
        cmdLS = new LSCommand("");
        cmdLS.getDirectories();
    }

    @Test(expected = NullPointerException.class)
    public void theFileListShouldNotBeNull() {
        cmdLS = new LSCommand("");
        cmdLS.getFiles();
    }

    @Test
    public void theFileListShouldFindAllFiles () throws IOException {
        final File file1 = temporaryFolder.newFile("file1.txt");
        final File file2 = temporaryFolder.newFile("file2.txt");
        final File file3 = temporaryFolder.newFile("file3.txt");
        cmdLS = new LSCommand(temporaryFolder.getRoot().getPath());
        assertEquals(3,cmdLS.getFiles().size());
    }

    @Test
    public void theDirectoryListShouldFindAllDirectories () throws IOException {
        final File dir1 = temporaryFolder.newFolder("directory1");
        final File dir2 = temporaryFolder.newFolder("directory2");
        final File dir3 = temporaryFolder.newFolder("directory3");
        cmdLS = new LSCommand(temporaryFolder.getRoot().getPath());
        assertEquals(3,cmdLS.getDirectories().size());
    }

    @Test
    public void theFileAndDirectoryListShouldFindAll () throws IOException {
        final File file1 = temporaryFolder.newFile("file1.txt");
        final File file2 = temporaryFolder.newFile("file2.txt");
        final File file3 = temporaryFolder.newFile("file3.txt");
        final File dir1 = temporaryFolder.newFolder("directory1");
        final File dir2 = temporaryFolder.newFolder("directory2");
        final File dir3 = temporaryFolder.newFolder("directory3");
        cmdLS = new LSCommand(temporaryFolder.getRoot().getPath());
        assertEquals(6,cmdLS.getDirectories().size() + cmdLS.getFiles().size());
    }

    @Test
    public void theLFlagShouldListAllContent () throws IOException {
        String command = "ls -l";
        String expectedOutput = "Directories\r\ndirectory1\r\ndirectory2\r\ndirectory3\r\nFiles\r\nfile1.txt" +
                "\r\nfile2.txt\r\nfile3.txt";
        final File file1 = temporaryFolder.newFile("file1.txt");
        final File file2 = temporaryFolder.newFile("file2.txt");
        final File file3 = temporaryFolder.newFile("file3.txt");
        final File dir1 = temporaryFolder.newFolder("directory1");
        final File dir2 = temporaryFolder.newFolder("directory2");
        final File dir3 = temporaryFolder.newFolder("directory3");
        cmdLS = new LSCommand(command.split(" ") , temporaryFolder.getRoot().getPath());
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
}