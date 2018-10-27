package oop.ex6.executer;

import oop.ex6.parser.CodeLine;
import oop.ex6.members.*;
import oop.ex6.scopes.*;

import java.util.ArrayList;
import java.util.function.ToIntFunction;

/**
 * This class represents an executor which executes the commands of an s-java file.
 */
public class Executor {

    /*----=   Constants  =----*/

    private final static String EXCEPTION_MESSAGE_SUFFIX = "Warning in line ";
    private final static int ILLEGAL_CODE = 1;
    private final static int LEGAL_CODE = 0;

    /*----=   Instance Data Members  =----*/

    private final ArrayList<CodeLine> codeLines;
    private Scope currentScope = new GlobalScope();
    private int lineNumber = 0;

    /*----=   Constructor  =----*/

    /**
     * A constructor for an executor which receives an array of commands to execute.
     * @param codeLines - the commands to execute.
     */
    public Executor(ArrayList<CodeLine> codeLines){
        this.codeLines = codeLines;
        this.currentScope = new GlobalScope();
    }

    /**
     * Execute this executor's commands consecutively.
     * @return - 1 if executor encountered a logical error in the commands; 0 otherwise.
     */
    public int execute(){
        ToIntFunction<Executor> command;
        for (CodeLine codeLine: this.codeLines){
            this.lineNumber = codeLine.getLineNumber();
            command = codeLine.getCommand();
            if (command.applyAsInt(this)==ILLEGAL_CODE){
                return ILLEGAL_CODE;
            }
        }
        return this.endCode();
    }

    /**
     * Define a new variable with the given parameters inside the current scope.
     * @param isFinal - the variable's final modifier.
     * @param variableType - the variable's type.
     * @param variableName - the variable's name.
     * @param value - the variable's input value.
     * @return - 1 if the executor encountered an error while performing the command; 0 otherwise.
     */
    public int defineVariable(boolean isFinal, Variable.Type variableType, String variableName, Value value){
        try {
            this.currentScope.defineVariable(isFinal, variableType, variableName, value);
        }
        catch (ExecutorException e){
            System.err.println(EXCEPTION_MESSAGE_SUFFIX+this.lineNumber+" - "+e.getMessage());
            return ILLEGAL_CODE;
        }
        return LEGAL_CODE;
    }

    /**
     * Assign a new value to an existing variable.
     * @param variableName - the variable's name.
     * @param value - the new value.
     * @return - 1 if the executor encountered an error while performing the command; 0 otherwise.
     */
    public int assignValue(String variableName, Value value){
        try {
            this.currentScope.assignValue(variableName, value);
        }
        catch (ExecutorException e){
            System.err.println(EXCEPTION_MESSAGE_SUFFIX+this.lineNumber+" - "+e.getMessage());
            return ILLEGAL_CODE;
        }
        return LEGAL_CODE;
    }

    /**
     * Declare a new method..
     * @param methodName - the method's name.
     * @param parameters - the method's parameters.
     * @return - 1 if the executor encountered an error while performing the command; 0 otherwise.
     */
    public int declareMethod(String methodName, ArrayList<Parameter> parameters){
        try {
            this.currentScope.declareMethod(methodName, parameters);
        }
        catch (ExecutorException e){
            System.err.println(EXCEPTION_MESSAGE_SUFFIX+this.lineNumber+" - "+e.getMessage());
            return ILLEGAL_CODE;
        }
        return LEGAL_CODE;
    }

    /**
     * Open a new method scope. This method assumes that the method has already been declared.
     * @param methodName - the method's name.
     * @return - 1 if the executor encountered an error while performing the command; 0 otherwise.
     */
    public int openMethodScope(String methodName){
        try {
            this.currentScope = this.currentScope.openMethodScope(methodName);
        }
        catch (ExecutorException e){
            System.err.println(EXCEPTION_MESSAGE_SUFFIX+this.lineNumber+" - "+e.getMessage());
            return ILLEGAL_CODE;
        }
        return LEGAL_CODE;
    }

    /**
     * Open a new if/while scope.
     * @param conditions - the if/while statement's conditions.
     * @return - 1 if the executor encountered an error while performing the command; 0 otherwise.
     */
    public int openIfWhileScope(ArrayList<Value> conditions){
        try {
            this.currentScope = this.currentScope.openIfWhileScope(conditions);
        }
        catch (ExecutorException e){
            System.err.println(EXCEPTION_MESSAGE_SUFFIX+this.lineNumber+" - "+e.getMessage());
            return ILLEGAL_CODE;
        }
        return LEGAL_CODE;
    }

    /**
     * Perform a method call to the given method, with the given values.
     * @param methodName - the method's name.
     * @param values - the given values.
     * @return - 1 if the executor encountered an error while performing the command; 0 otherwise.
     */
    public int callMethod(String methodName, ArrayList<Value> values){
        try {
            this.currentScope.callMethod(methodName, values);
        }
        catch (ExecutorException e){
            System.err.println(EXCEPTION_MESSAGE_SUFFIX+this.lineNumber+" - "+e.getMessage());
            return ILLEGAL_CODE;
        }
        return LEGAL_CODE;
    }

    /**
     * Send a return statement to the current scope.
     * @return - 1 if the executor encountered an error while performing the command; 0 otherwise.
     */
    public int sendReturnStatement(){
        try {
            this.currentScope.sendReturnStatement();
        }
        catch (ExecutorException e){
            System.err.println(EXCEPTION_MESSAGE_SUFFIX+this.lineNumber+" - "+e.getMessage());
            return ILLEGAL_CODE;
        }
        return LEGAL_CODE;
    }

    /**
     * Close the current scope.
     * @return - 1 if the executor encountered an error while performing the command; 0 otherwise.
     */
    public int closeScope(){
        try{
            this.currentScope = this.currentScope.closeScope();
        }
        catch (ExecutorException e){
            System.err.println(EXCEPTION_MESSAGE_SUFFIX+this.lineNumber+" - "+e.getMessage());
            return ILLEGAL_CODE;
        }
        return LEGAL_CODE;
    }

    /**
     * End the code - verify that all local scopes have been closed.
     * @return - 1 if the executor encountered an error while performing the command; 0 otherwise.
     */
    public int endCode(){
        try {
            this.currentScope.endCode();
        }
        catch (ExecutorException e){
            System.err.println(EXCEPTION_MESSAGE_SUFFIX+this.lineNumber+" - "+e.getMessage());
            return ILLEGAL_CODE;
        }
        return LEGAL_CODE;
    }


}
