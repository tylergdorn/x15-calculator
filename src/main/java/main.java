import static java.lang.System.out;

public class main {
    public static void main(String[] args){
        TestReader read = new TestReader("./tests/test1/test.txt");
        Parser p = new Parser(read.read());
        p.process();
    }
}
