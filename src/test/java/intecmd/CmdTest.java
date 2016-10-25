package intecmd;

import intecmd.commands.LSCommand;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CmdTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private Cmd cmd;
    private String[] grepCommandArray = {"grep", "nonExistantString", "nonExistantFile.txt"};
    private String[] lsCommandArray = {"ls", "-help"};
    private String[] wcCommandArray = {"wc", "-l","pom.xml"};
    private String[] helpCommandArray = {"help"};


    @Rule
    public final ExpectedSystemExit expectedSystemExit = ExpectedSystemExit.none();

    @Before
    public void setUpTests() {
        System.setOut(new PrintStream(outContent));
        cmd = new Cmd();
    }

    @Test
    public void theCmdShouldNotBeNull() {
        assertNotNull(cmd);
    }

    @Test
    public void theTokenizerShouldSplitAtEachSpaceCharacter() {
        assertEquals(2, cmd.tokenizeString("cd ..").length);
    }

    @Test
    public void theEchoCommandShouldReturnTheCorrectString() {
        String[] arr = {"echo", "test", "the", "echo", "command"};
        assertEquals("test the echo command", cmd.echoCommand(arr));
    }

    @Test
    public void theTokenizerShouldNotSplitBetweenQuotationMarks() {
        assertEquals(3, cmd.tokenizeString("this \"should have a length of\" three").length);
    }

    @Test
    public void theTokenizerShouldHandleSingleWordQuotes() {
        assertEquals(6, cmd.tokenizeString("this strings \"length\" should be six").length);
    }

    @Test
    public void theExitCommandShouldCloseTheApplication() {
        expectedSystemExit.expectSystemExit();
        cmd.exitCommand();
    }

    @Test
    public void theGrepCommandShouldBeExecuted() {
        cmd.commandSwitch(grepCommandArray);
        assertEquals("No files with that name", outContent.toString().trim());
    }

    @Test
    public void theLsCommandShouldBeExecuted() {
        cmd.commandSwitch(lsCommandArray);
        String expectedOutput = "-l lists all directories and files in the current folder " +
                "\n-f show all files in the current folder" +
                "\n-fl lists all files in the current folder" +
                "\n-d show all directories in the current folder" +
                "\n-ld lists all directories in the current folder";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void theWcCommandShouldExecute() {
        cmd.commandSwitch(wcCommandArray);
        assertEquals("Lines: 59.", outContent.toString().trim());
    }
}
