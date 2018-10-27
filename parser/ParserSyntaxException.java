package oop.ex6.parser;

/**
 * This class signals that a parser has found a syntax error in an s-java file.
 */
public class ParserSyntaxException extends Exception {

    /*----=   Instance Data Members  =----*/
    private final String detail_message;
    private final int lineNumber;
    private final static String detail_message_header = "Syntax exception: ";
    private final static String warning_header = "Warning in line ";

    /**
     * A constructor which receives a detail message.
     * @param message - the given detail message.
     */
    public ParserSyntaxException(String message, int lineNumber){
        this.detail_message = message+"\n";
        this.lineNumber = lineNumber;
    }

    /*----=   Instance Methods  =----*/

    /**
     * @return this exception's header and detail messages.
     */
    public String getMessage(){
        return warning_header+lineNumber+" - "+detail_message_header+this.detail_message;
    }

}
