import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

class TestReader {
    private String filename;

    /**
     * The constructor which reads in a testfile. I did this to make it so I didn't have to see this.
     * @param filename the filename of what file to read.
     */
    TestReader(String filename) {
        this.filename = filename;
    }

    /**
     * @return A list of Strings representing the lines in a the testfile the object was initialized with.
     */
    List<String> read(){
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
            while(reader.ready()){
                list.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
