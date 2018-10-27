package oop.ex6.parser;

/**
 * This class signals that a parser has found an IO exception while parsing an s-java file.
 */
public class ParserIOException extends Exception {

    /*----=   Instance Data Members  =----*/

    private final String detail_message;
    private final static String detail_message_header = "IO exception: ";

    /*----=   Constructor  =----*/

    /**
     * A constructor which receives a detail message.
     * @param message - the given detail message.
     */
    public ParserIOException(String message){
        this.detail_message = message+"\n";
    }

    /*----=   Instance Methods  =----*/

    /**
     * @return this exception's header and detail messages.
     */
    public String getMessage(){
        return detail_message_header+this.detail_message;
    }
}
