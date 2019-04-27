import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Parser {
    List<String> text;
    public Parser(List<String> text) {
        this.text = text;
    }
    public void process(){
        Map symbols = new HashMap<Character, Integer>();
        for(int i = 0; i < this.text.size(); i++){
            try {
                System.out.println(verify(this.text.get(i), i));
            } catch (MalformedError malformedError) {
                malformedError.printStackTrace();
            }
        }

    }

    /**
     * Takes in a line and processes it. Throws MalformedError if the line isn't correct.
     * @return A List<String> where the first thing is the identifier, then all the symbols in the expression
     */
    private List<String> verify(String line, int lineNumber) throws MalformedError{
        List<String> parsed = new ArrayList<>();
        String regex = "^(result|[a-zA-Z]) = (.*);$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()){
           parsed.add(matcher.group(1));
           String[] tempArray = matcher.group(2).split(" ", 0);
           for(String token : tempArray){
               parsed.add(token);
           }
        }
        else{
            // line numbers don't start at 0
            throw new MalformedError("Malformed statement on line " + (lineNumber + 1));
        }
        return parsed;
    }

}

