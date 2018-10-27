package oop.ex6.main;

import oop.ex6.executer.Executor;
import oop.ex6.parser.Parser;
import oop.ex6.parser.ParserIOException;
import oop.ex6.parser.ParserSyntaxException;

/**
 * This class represents an s-java compiler and contains the main method.
 */
public class Sjavac {

    /*----=   Constants  =----*/

    private final static int IO_EXCEPTION = 2;
    private final static int ILLEGAL_CODE = 1;

    /**
     * The main method: receives a path to an s-java source file and prints:
     * 0 - if the code is legal.
     * 1 - if the code is illegal. In this case it also prints out an informative message.
     * 2 - if the method catches an IO exception.
     * @param args - the given argument.
     */
    public static void main(String[] args){
        try {
            Parser parser = new Parser();
            Executor executor = new Executor(parser.getCommands(args));
            System.out.println(executor.execute());
        }
        catch (ParserSyntaxException e){
            System.err.println(e.getMessage());
            System.out.println(ILLEGAL_CODE);
        }
        catch (ParserIOException e){
            System.err.println(e.getMessage());
            System.out.println(IO_EXCEPTION);
        }
    }
}
