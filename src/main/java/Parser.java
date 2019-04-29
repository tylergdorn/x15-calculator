import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Parser{

    private List<String> text;
    private Map<String, Integer> valueMap;

    /**
     * Constructor for the parser
     * @param text a list of Strings representing the lines of the program
     */
    Parser(List<String> text) {
        this.text = text;
        this.valueMap = new HashMap<>();
    }

    /**
     *  The 'worker' of the Parser. takes internal list of Strings and goes through and parses all values.
     * @throws MalformedError if "script" is incorrectly made
     */
    int process() throws MalformedError {
        // A list of lines and their symbols
        List<List<String>> symbols = new ArrayList<>();
        for(int i = 0; i < this.text.size(); i++){
            symbols.add(verify(this.text.get(i), i));
        }
        // we know the variable is good, because we checked it when we parsed the line.
        for(List<String> line: symbols) {
            // the case where the assignment is "atomic", i.e. a = 1;
            if (line.size() == 2) {
                // the case where the thing that it equals is a number
                if (isValue(line.get(1))) {
                    valueMap.put(line.get(0), Integer.parseInt(line.get(1)));
                }
                // otherwise it's another variable
                else {
                    valueMap.put(line.get(0), processVariable(line.get(1)));
                }
            }
            // the case where the assignment is more complex
            else {
                // identifier is the variable that the line is being assigned to
                String identifier = line.get(0);
                line.remove(0);

                Stack<Integer> lastVariable = new Stack<>();
                while (!line.isEmpty()) {

                    int[] values = {0, 0};

                    // if there is no stored last variable
                    if (lastVariable.empty()){
                        String variable = line.get(0);
                        line.remove(0);
                        values[0] = processVariable(variable);
                    }

                    else {
                        values[0] = lastVariable.pop();
                    }

                    String operator = line.get(0);
                    values[1] = processVariable(line.get(1));
                    // does this do what I want?
                    line.remove(0);
                    line.remove(0);

                    int result = applyOperation(values[0], operator, values[1]);
                    // the result is added back to the list to be factored in to the next operation
                    lastVariable.push(result);
                }
                valueMap.put(identifier, lastVariable.pop());
            }
        }
        if(!valueMap.containsKey("result")){
            throw new MalformedError("Variable \"result\" not found");
        } else {
            return valueMap.get("result");
        }
    }

    /**
     * Takes in a variable and returns an int of it's value (either from the dictionary or by parsing the int)
     * @param variable is the string representation of a variable to be parsed.
     * @return int that the variable is or it's representation in the dictionary
     * @throws MalformedError when the string given cannot be parsed
     */
    private int processVariable(String variable) throws MalformedError {
        if(valueMap.containsKey(variable)) {
            return valueMap.get(variable);
        }
        // if it's not in the dictionary it may be a value
        else {
            if(isValue(variable)){
                return Integer.parseInt(variable);
            }
            // if it's not a value or a variable something is wrong
            else {
                throw new MalformedError("Unknown symbol: " + variable);
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
           Collections.addAll(parsed, tempArray);
//           for(String token : tempArray){
//               parsed.add(token);
//           }
        }
        else{
            // line numbers don't start at 0
            throw new MalformedError("Malformed statement on line " + (lineNumber + 1));
        }
        return parsed;
    }


    /**
     * Checks if a string is a value as defined by our regex (no leading 0's)
     * @param value a string which may contain a value
     * @return true
     */
    private boolean isValue(String value){
        return value.matches("[1-9][0-9]*");
    }

    /**
     * applyOperation does two things: parses the operator passed, and applies the operation
     * @param valueOne is the first integer variable
     * @param operator is the operation to be done. Must be in [+, -, *, /, ^]
     * @param valueTwo is the second integer variable
     * @return int of the value after the operation has finished i.e. applyOperation(1, "+", 1) would return 2
     * @throws MalformedError when the operator is not found in the above list
     */
    private int applyOperation(int valueOne, String operator, int valueTwo) throws MalformedError {
        switch(operator) {
            case "+":
                return valueOne + valueTwo;
            case "-":
                return valueOne - valueTwo;
            case "*":
                return valueOne * valueTwo;
            case "/":
                // java so we don"t need to worry about this returning decimals
                return valueOne / valueTwo;
            case "^":
                return (int) Math.pow(valueOne, valueTwo);
            default:
                throw new MalformedError("No such operator: " + operator);
        }
    }

}

