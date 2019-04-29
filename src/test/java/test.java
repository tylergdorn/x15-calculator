import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    @Test
    public void testsFolderTests(){
        File folder = new File("tests");
        System.out.println(folder.getAbsolutePath());
        File[] listOfTestFolders = folder.listFiles();
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
                    for (int i = 0; i < listOfFiles.length; i++) {
                        Matcher matcher = filename.matcher(listOfFiles[i].getName());
                        if (matcher.find()) {
                            // we want to know where the input is and where the output is.
                            if (matcher.group(1).equals("txt")) {
                                inputFiles[0] = listOfFiles[i];
                            }
                            // we don't really care if it's a .err or a .out because we compare them the same way.
                            else {
                                inputFiles[1] = listOfFiles[i];
                            }
                        } else {
                            System.out.println("unknown file in test folder: " + testFolder.getName());
                        }

                    }
                }
                runTest(inputFiles[0].getAbsolutePath(), inputFiles[1].getAbsolutePath());
            }
        }
    }

    private void runTest(String inputFileName, String resultFileName){
        // load up our readers
        TestReader input = new TestReader(inputFileName);
        TestReader result = new TestReader(resultFileName);
        // prepare our calculator parser
        Parser p = new Parser(input.read());
        List<String> output = new ArrayList<>();
        List<String> targetResult = result.read();
        // if it runs we add the output to the list
        try {
            output.add(Integer.toString(p.process()));
            // if it fails to run, capture and add the failed output to the list
        } catch (MalformedError e) {
            output.add(String.valueOf(e.getCause()));
        }
//       compare the expected output and the output we got
        for(int i = 0; i < output.size(); i++){
            // if they aren't the same, fail
            assertEquals(output.get(i), targetResult.get(i));
        }

    }
}
