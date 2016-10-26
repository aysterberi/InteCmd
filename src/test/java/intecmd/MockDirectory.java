package intecmd;

import java.util.ArrayList;

public class MockDirectory {

    protected ArrayList<MockDirectory> directories = new ArrayList<>();
    protected String name;

    public MockDirectory(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
