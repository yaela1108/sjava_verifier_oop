package oop.ex6.members;

import oop.ex6.executer.ExecutorException;

/**
 * This class extends ExecutorException and signals that a variable has received an illegal command.
 */
public class VariableException extends ExecutorException {

    /*----=   Instance Data Members  =----*/

    private final String detail_message;
    private final static String detail_message_header = "Variable exception: ";

    /*----=   Constructor  =----*/

    /**
     * A constructor which receives a detail message.
     * @param message - the given detail message.
     */
    public VariableException(String message){
        this.detail_message = message+"\n";
    }

    /*----=   Instance Methods  =----*/

    /**
     * @return this exception's header and detail message.
     */
    public String getMessage(){
        return detail_message_header+this.detail_message;
    }


}
