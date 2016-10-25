import java.util.ArrayList;

public class MockDirectory {

    protected ArrayList<MockDirectory> directories = new ArrayList<>();
    protected String name;

    public MockDirectory(String name) {
        if(directoryNameIsValid(name))
            this.name = name;
        else
            throw new IllegalArgumentException("Directory name is invalid.");
    }

    private boolean directoryNameIsValid(String name) {
        return System.getProperty("os.name").startsWith("Windows") ? name.matches(ChangeDirectory.PATTERN_WINDOWS_DIRECTORY) : name.matches(ChangeDirectory.PATTERN_UNIX_DIRECTORY);
    }

    public String toString() {
        return name;
    }
}
