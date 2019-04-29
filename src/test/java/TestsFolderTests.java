import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(Parameterized.class)
public class TestsFolderTests {
    private File expected;
    private File input;

    /**
     * Constructor for the test
     * @param input the input file
     * @param expected the file which contains the expected output
     */
    public TestsFolderTests(File input, File expected){
        this.expected = expected;
        this.input = input;
    }

    /**
     * testsFolderFinder finds all folders and puts their path into a string array, which JUnit then passes to our test runner
     * @return a list of arrays of Files, representing the inputs for our tests
     */
    @Parameterized.Parameters(name = "{index}: runTest({0})={1}")
    public static Collection<File[]> testsFolderFinder(){
        File folder = new File("tests");
        System.out.println(folder.getAbsolutePath());
        File[] listOfTestFolders = folder.listFiles();
        Collection<File[]> output = new ArrayList<>();
        // this matches a filename with the necessary file endings
        Pattern filename = Pattern.compile("test\\.(txt|out|err)");
        // make sure that the tests folder is there and there are folders in it
        if (listOfTestFolders != null) {
            // look through all the folders inside test
            for (File testFolder : listOfTestFolders) {
                File[] listOfFiles = testFolder.listFiles();
                //prepare an array so we know which files are which later
                File[] inputFiles = new File[2];
                if (listOfFiles != null) {
                    // look through all the files inside each folder
                    for (File file : listOfFiles) {
                        Matcher matcher = filename.matcher(file.getName());
                        if (matcher.find()) {
                            // we want to know where the input is and where the output is.
                            if (matcher.group(1).equals("txt")) {
                                inputFiles[0] = file;
                            }
                            // we don't really care if it's a .err or a .out because we compare them the same way.
                            else {
                                inputFiles[1] = file;
                            }
                        } else {
                            System.out.println("unknown file in test folder: " + testFolder.getName());
                        }

                    }
                }
//                runTest(inputFiles[0].getAbsolutePath(), inputFiles[1].getAbsolutePath());
                output.add(new File[]{inputFiles[0], inputFiles[1]});
            }
        }
        return output;
    }

    /**
     * Test runner for JUnit.
     */
    @Test
    public void runTest(){
        // load up our readers
        TestReader input = new TestReader(this.input);
        TestReader result = new TestReader(this.expected);
        // prepare our calculator parser
        Parser p = new Parser(input.read());
        List<String> output = new ArrayList<>();
        List<String> targetResult = result.read();
        // if it runs we add the output to the list
        try {
            output.add(Integer.toString(p.process()));
            // if it fails to run, capture and add the failed output to the list
        } catch (MalformedError e) {
            output.add(String.valueOf(e.getMessage()));
        }
//       compare the expected output and the output we got
        for(int i = 0; i < output.size(); i++){
            // if they aren't the same, fail
            assertEquals(output.get(i), targetResult.get(i));
        }

    }
}
