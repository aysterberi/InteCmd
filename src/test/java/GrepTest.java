import intecmd.CurrentDirectory;
import intecmd.commands.GrepCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GrepTest {

    private static final String USER_DIRECTORY = System.getProperty("user.dir");
    private GrepCommand grep;
    private CurrentDirectory currentDirectory;
    private String[] anotherCorrectTokenArray = {"grep", "another", "anotherTest.txt"};
    private String[] correctTokenArray = {"grep", "test", "test.txt"};
    private String[] helpTokenArray = {"grep", "help"};
    private String[] tooLongTokenArray = {"grep", "test", "test.txt", "extra"};
    private String[] sentenceArray = {"grep", "\"a longer\"", "testSentence.txt"};

    @Before
    public void setUp() {
        currentDirectory = new CurrentDirectory();
        currentDirectory.setCurrentDirectory(USER_DIRECTORY);
        try {
            String testFile = "this\nis\na\ntest\ndocument";
            String anotherTestFile = "this\nis\nanother\ntest\ndocument";
            String testSentence = "search\nfor\n\"a longer\"\nword";
            PrintWriter printWriter = new PrintWriter("test.txt", "utf-8");
            printWriter.write(testFile);
            printWriter.close();
            printWriter = new PrintWriter("anotherTest.txt", "utf-8");
            printWriter.write(anotherTestFile);
            printWriter.close();
            printWriter = new PrintWriter("testSentence.txt", "utf-8");
            printWriter.write(testSentence);
            printWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @After
    public void shutDown() {
        try {
            Files.deleteIfExists(Paths.get("test.txt"));
            Files.deleteIfExists(Paths.get("anotherTest.txt"));
            Files.deleteIfExists(Paths.get("testSentence.txt"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void theGrepCommandShouldNotBeNull() {
        grep = new GrepCommand(correctTokenArray);
        assertNotNull(grep);
    }

    @Test
    public void theTokenArrayShouldContainThreeEntries() {
        grep = new GrepCommand(correctTokenArray);
        assertEquals(3, grep.tokens.length);
    }

    @Test
    public void ifTokenArrayContainsTooManyEntriesDoNotExecuteCommand() {
        grep = new GrepCommand(tooLongTokenArray);
        assertEquals("Too many arguments, try again", grep.tooManyArguments());
    }

    @Test
    public void grepCommandFollowedByHelpCommandShouldReturnTheCorrectString() {
        grep = new GrepCommand(helpTokenArray);
        assertEquals("test help", grep.help());
    }

    @Test
    public void executeSearchShouldReturnTheCorrectStringWhenSearchingForTest() {
        grep = new GrepCommand(correctTokenArray);
        assertEquals("test", grep.executeSearch(correctTokenArray));
    }

    @Test
    public void executeSearchShouldReturnTheCorrectStringWhenSearchingForAnotherTest() {
        grep = new GrepCommand(anotherCorrectTokenArray);
        assertEquals("another", grep.executeSearch(anotherCorrectTokenArray));
    }

    @Test
    public void anErrorMessageShouldBeReturnedIfNoMatchWasFound() {
        grep = new GrepCommand(correctTokenArray);
        assertEquals("error message", grep.executeSearch(anotherCorrectTokenArray));
    }

    @Test
    public void executeSearchForSentenceInQuotationsShouldReturnTheSentence() {
        grep = new GrepCommand(sentenceArray);
        assertEquals("a longer", grep.executeSearch(sentenceArray));
    }
}