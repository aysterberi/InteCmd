package intecmd;

import intecmd.commands.GrepCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GrepTest {

    private static final String USER_DIRECTORY = System.getProperty("user.dir");
    private static final String[] ANOTHER_CORRECT_TOKEN_ARRAY = {"grep", "another", "anotherTest.txt"};
    private static final String[] CORRECT_TOKEN_ARRAY = {"grep", "test", "test.txt"};
    private static final String[] HELP_TOKEN_ARRAY = {"grep", "help"};
    private static final String[] TOO_LONG_TOKEN_ARRAY = {"grep", "test", "test.txt", "extra"};
    private static final String[] TOO_SHORT_TOKEN_ARRAY = {"grep", "test"};
    private static final String[] SENTENCE_ARRAY = {"grep", "\"a longer\"", "testSentence.txt"};
    private static final String[] NON_EXISTING_FILE_ARRAY = {"grep", "test", "doesNotExist.txt"};
    private static final String[] MULTIPLE_HITS_ARRAY = {"grep", "multiple", "*.txt"};
    private static final String[] SAME_FILE_MULTIPLE_HITS_ARRAY = {"grep", "two", "twoHits.txt"};
    private static final String[] UNSUPPORTED_ARRAY = {"grep", "test", "unsupported.exe"};
    private CurrentDirectory currentDirectory;
    private GrepCommand grep;

    @Before
    public void setUp() {
        currentDirectory = new CurrentDirectory();
        currentDirectory.setCurrentDirectory(USER_DIRECTORY);
        try {
            String testFile = "this\nis\na\ntest\ndocument";
            String anotherTestFile = "this\nis\nanother\ntest\ndocument";
            String testSentence = "search\nfor\n\"a longer\"\nword";
            String testMultipleHitsOne = "multiple\nhits\none";
            String testMultipleHitsTwo = "multiple\nhits\ntwo";
            String multipleHitsSameFile = "two\nhits\ntwo";

            PrintWriter printWriter = new PrintWriter("test.txt", "utf-8");
            printWriter.write(testFile);
            printWriter.close();
            printWriter = new PrintWriter("anotherTest.txt", "utf-8");
            printWriter.write(anotherTestFile);
            printWriter.close();
            printWriter = new PrintWriter("testSentence.txt", "utf-8");
            printWriter.write(testSentence);
            printWriter.close();
            printWriter = new PrintWriter("mulOne.txt", "utf-8");
            printWriter.write(testMultipleHitsOne);
            printWriter.close();
            printWriter = new PrintWriter("mulTwo.txt", "utf-8");
            printWriter.write(testMultipleHitsTwo);
            printWriter.close();
            printWriter = new PrintWriter("twoHits.txt", "utf-8");
            printWriter.write(multipleHitsSameFile);
            printWriter.close();
            File unsupportedFile = new File("unsupported.exe");
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
            Files.deleteIfExists(Paths.get("mulOne.txt"));
            Files.deleteIfExists(Paths.get("mulTwo.txt"));
            Files.deleteIfExists(Paths.get("twoHits.txt"));
            Files.deleteIfExists(Paths.get("unsupported.exe"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void theGrepCommandShouldNotBeNull() {
        grep = new GrepCommand(CORRECT_TOKEN_ARRAY);
        assertNotNull(grep);
    }

    @Test
    public void theTokenArrayShouldContainThreeEntries() {
        grep = new GrepCommand(CORRECT_TOKEN_ARRAY);
        assertEquals(3, grep.tokens.length);
    }

    @Test
    public void ifTokenArrayContainsTooManyEntriesDoNotExecuteCommand() {
        grep = new GrepCommand(TOO_LONG_TOKEN_ARRAY);
        assertEquals("Too many arguments, try again", grep.tooManyArguments());
    }

    @Test
    public void ifTokenArrayContainsTooFewArgumentsReturnErrorMessage() {
        grep = new GrepCommand(TOO_SHORT_TOKEN_ARRAY);
        assertEquals("Too few arguments, try again", grep.executeSearch(TOO_SHORT_TOKEN_ARRAY));
    }

    @Test
    public void grepCommandFollowedByHelpCommandShouldReturnTheCorrectString() {
        grep = new GrepCommand(HELP_TOKEN_ARRAY);
        assertEquals("test help", grep.help());
    }

    @Test
    public void executeSearchShouldReturnTheCorrectStringWhenSearchingForTest() {
        grep = new GrepCommand(CORRECT_TOKEN_ARRAY);
        assertEquals("test", grep.executeSearch(CORRECT_TOKEN_ARRAY));
    }

    @Test
    public void executeSearchShouldReturnTheCorrectStringWhenSearchingForAnotherTest() {
        grep = new GrepCommand(ANOTHER_CORRECT_TOKEN_ARRAY);
        assertEquals("another", grep.executeSearch(ANOTHER_CORRECT_TOKEN_ARRAY));
    }

    @Test
    public void anErrorMessageShouldBeReturnedIfNoMatchWasFound() {
        grep = new GrepCommand(CORRECT_TOKEN_ARRAY);
        assertEquals("error message", grep.executeSearch(ANOTHER_CORRECT_TOKEN_ARRAY));
    }

    @Test
    public void executeSearchForSentenceInQuotationsShouldReturnTheSentence() {
        grep = new GrepCommand(SENTENCE_ARRAY);
        assertEquals("a longer", grep.executeSearch(SENTENCE_ARRAY));
    }

    @Test
    public void searchingForNonExistingFileShouldReturnErrorMessage() {
        grep = new GrepCommand(NON_EXISTING_FILE_ARRAY);
        assertEquals("No files with that name", grep.executeSearch(NON_EXISTING_FILE_ARRAY));
    }

    @Test
    public void searchingInAllFilesShouldReturnMultipleHits() {
        grep = new GrepCommand(MULTIPLE_HITS_ARRAY);
        assertEquals("multiplemultiple", grep.executeSearch(MULTIPLE_HITS_ARRAY));
    }

    @Test
    public void multipleMatchesInOneFileShouldReturnAllHits() {
        grep = new GrepCommand(SAME_FILE_MULTIPLE_HITS_ARRAY);
        assertEquals("twotwo", grep.executeSearch(SAME_FILE_MULTIPLE_HITS_ARRAY));
    }

    @Test
    public void unsupportedFileEndingShouldReturnErrorMessage() {
        grep = new GrepCommand(UNSUPPORTED_ARRAY);
        assertEquals("Unsupported file format", grep.executeSearch(UNSUPPORTED_ARRAY));
    }
}
