import java.io.File;
import java.io.FilenameFilter;

public class LSCommand{
    private File file;
    private File[] directories, files;

    public LSCommand(String pathToFile) {
        this.file = new File(pathToFile);
        setDirectories();
        setFiles();
    }

    public void setDirectories (){
        directories = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
    }

    public void setFiles () {
        files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isFile();
            }
        });
    }

    public File[] getFiles() {
        return files;
    }

    public File getFile() {
        return file;
    }

    public File[] getDirectories() {
        if (directories == null || directories.length == 0){
            throw new NullPointerException();
        }else{
            return directories;
        }
    }
}
