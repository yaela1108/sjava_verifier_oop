package oop.ex6.executer;

/**
 * This abstract class signals that an executor has encountered a logical error while executing its commands.
 */
public abstract class ExecutorException extends Exception {

    /**
     * @return this exception's header and detail message.
     */
    public abstract String getMessage();
}
