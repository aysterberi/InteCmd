import java.util.ArrayList;

public class MockRoot {

    protected String rootName;
    protected ArrayList<MockDirectory> directories = new ArrayList<>();

    public MockRoot(String rootName) {
        if(rootNameIsValid(rootName))
            this.rootName = rootName;
        else
            throw new IllegalArgumentException("Root name is invalid.");
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

    public String toString() {
        return rootName;
    }
}
