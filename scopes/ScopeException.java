package oop.ex6.scopes;

import oop.ex6.executer.ExecutorException;

/**
 * This class extends ExecutorException and signals that a scope has received an illegal command.
 */
public class ScopeException extends ExecutorException {

    /*----=   Instance Data Members  =----*/

    private String detail_message;
    private final static String detail_message_header = "Scope exception: ";

    /*----=   Constructor  =----*/

    /**
     * A constructor which receives a detail message.
     * @param message - the given detail message.
     */
    public ScopeException(String message){
        this.detail_message = message+"\n";
    }

    /*----=   Instance Methods  =----*/

    /**
     * @return this exception's header and detail message.
     */
    public String getMessage(){
        return this.detail_message;
    }

}
