import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class TestReader {
    String filename;

    /**
     *
     * @param filename
     */
    public TestReader(String filename) {
        this.filename = filename;
    }

    /**
     * @return
     */
    public List<String> read(){
        List<String> list = new ArrayList<String>();
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
