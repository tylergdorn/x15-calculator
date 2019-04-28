/**
 * Malformed error is the error you get when something about the script is broken.
 * We use it to signify to main that it should stop executing the script and return the error.
 */
class MalformedError extends Exception {
    /**
     * A script reading error
     * @param message is the message that will get printed to the user
     */
    MalformedError(String message){
        super(message);
    }
}
