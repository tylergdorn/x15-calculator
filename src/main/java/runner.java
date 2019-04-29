import java.io.File;

import static java.lang.System.out;

public class runner {
    public static void main(String[] args){
        TestReader read = new TestReader(new File("./tests/passingtest1/test.txt"));
        Parser p = new Parser(read.read());
        try {
            out.println(p.process());
        } catch (MalformedError malformedError) {
            malformedError.printStackTrace();
        }
    }
}
