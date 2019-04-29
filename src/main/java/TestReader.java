import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

class TestReader {
    private File file;

    /**
     * The constructor which reads in a testfile. I did this to make it so I didn't have to see this.
     * @param file the file to read.
     */
    TestReader(File file) {
        this.file = file;
    }

    /**
     * read reads the file specified in the constructor and returns it as a List of Strings
     * @return A list of Strings representing the lines in a the testfile the object was initialized with.
     */
    List<String> read(){
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.file));
            while(reader.ready()){
                list.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
