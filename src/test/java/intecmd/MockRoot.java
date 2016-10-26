package intecmd;

import intecmd.MockDirectory;

import java.util.ArrayList;

public class MockRoot {

    protected String rootName;
    protected ArrayList<MockDirectory> directories = new ArrayList<>();

    public MockRoot(String rootName) {
        this.rootName = rootName;
    }

    public String toString() {
        return rootName;
    }
}
