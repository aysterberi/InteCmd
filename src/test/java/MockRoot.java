import java.util.ArrayList;

/**
 * Created by Niklas on 2016-10-25.
 */
public class MockRoot {

    protected String rootName;
    protected ArrayList<MockDirectory> directories;

    public MockRoot(String rootName) {
        if(rootNameIsValid(rootName))
            this.rootName = rootName;
        else
            throw new IllegalArgumentException("Root name is invalid.");
        directories = new ArrayList<>();
    }

    /**
     Checks if the current system runs any of the later version of Windows,
     otherwise makes an assumption that the system uses Unix. If rootName
     matches regex according to patterns given through class ChangeDirectory.

     @return a boolean which returns true if the system is Windows and checks
     with the Windows pattern, true if the system is Unix and checks with the
     Unix pattern. If none of them are true, return false.
     */
    private boolean rootNameIsValid(String rootName) {
        return System.getProperty("os.name").startsWith("Windows") ? rootName.matches(ChangeDirectory.PATTERN_ROOT_WINDOWS) : rootName.matches(ChangeDirectory.PATTERN_ROOT_UNIX);
    }
}
