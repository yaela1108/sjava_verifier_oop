package oop.ex6.parser;

import oop.ex6.executer.Executor;

import java.util.function.ToIntFunction;

/**
 * This class represents a code line in an s-java file.
 */
public class CodeLine {

    /*----=   Instance Data Members  =----*/

    private final ToIntFunction<Executor> command;
    private final int lineNumber;

    /*----=   Constructor  =----*/

    /**
     * A constructor for a code line which receives this code line's command and line number.
     * @param command - this code line's command.
     * @param lineNumber - this code line's line number.
     */
    CodeLine(ToIntFunction<Executor> command, int lineNumber){
        this.command = command;
        this.lineNumber = lineNumber;
    }

    /*----=   Instance Methods  =----*/

    /**
     * @return this code line's command.
     */
    public ToIntFunction<Executor> getCommand() {
        return this.command;
    }

    /**
     * @return this code line's line number.
     */
    public int getLineNumber() {
        return this.lineNumber;
    }
}
