package oop.ex6.scopes;

import oop.ex6.executer.ExecutorException;
import oop.ex6.members.Parameter;
import oop.ex6.members.Value;
import oop.ex6.members.Variable;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * This class extends Scope and represents a global scope inside an s-java file.
 */
public class GlobalScope extends Scope {

    /*----=   Instance Data Members  =----*/

    private Hashtable<String, ArrayList<Parameter>> methods;

    /*----=   Constructor  =----*/

    /**
     * The default constructor.
     */
    public GlobalScope(){
        this.variables = new Hashtable<>();
        this.methods = new Hashtable<>();
    }

    /**
     * Retrieves a variable with the given variable name from the closest accessible scope.
     * @param variableName - the given variable name.
     * @return - the retrieved variable.
     * @throws ExecutorException - if no variable with the given name exists in this scope's accessible
     * variables.
     */
    protected Variable getVariable(String variableName) throws ExecutorException {
        if (this.variables.containsKey(variableName)){
            return this.variables.get(variableName);
        }
        throw new ScopeException(VARIABLE+variableName+MISSING);
    }

    /**
     * Gets the variable type of a given value.
     * @param value - the given value.
     * @return - the variable type.
     * @throws ExecutorException - if the variable type is null i.e. the value referenced an uninitialized
     * variable; or if the value referenced a variable which does not exist in this scope's accessible
     * variables.
     */
    protected Variable.Type getValueType(Value value) throws ExecutorException {
        if (value.getValueType() != null){
            return value.getValueType();
        }
        Variable.Type valueType = this.getVariable(value.getValueName()).getValueType();
        if (valueType == null){
            throw new ScopeException(ILLEGAL_METHOD_CALL+value.getValueName()+UNINITIALIZED_VALUE);
        }
        return valueType;
    }

    /**
     * Define a new variable with the given parameters in this scope.
     * @param isFinal - the variable's final modifier.
     * @param variableType - the variable's type.
     * @param variableName - the variable's name.
     * @param value - the variable's value.
     * @throws ExecutorException - if a variable of the same name already exists in this scope; or if
     * the variable's value references a missing or uninitialized variable; or if the variable construction
     * was illegal.
     */
    public void defineVariable
            (boolean isFinal, Variable.Type variableType, String variableName, Value value)
            throws ExecutorException {
        if (this.variables.containsKey(variableName)){
            throw new ScopeException(VARIABLE_OVERFLOW);
        }
        Variable.Type valueType = null;
        if (value != null){
            valueType = this.getValueType(value);
        }
        Variable variable = new Variable(isFinal, variableType, valueType);
        this.variables.put(variableName, variable);
    }

    /**
     * Assign a new value to an existing variable in this scope's accessible variables.
     * @param variableName - the variable's name.
     * @param value - the value.
     * @throws ExecutorException - if the variable doesn't exist in this scope's accessible variables;
     * or if the value references a missing or uninitialized variable;
     * or if the variable assignment was illegal.
     */
    public void assignValue(String variableName, Value value) throws ExecutorException {
        Variable.Type valueType = this.getValueType(value);
        this.assignType(variableName, valueType);
    }

    /**
     * Assign a new value to existing variable in the scope's accessible variables.
     * @param variableName - the variable's name.
     * @param valueType - the value's variable type.
     * @throws ExecutorException - if the variable doesn't exist in this scope's accessible variables;
     * or if variable assignment was illegal.
     */
    protected void assignType(String variableName, Variable.Type valueType) throws ExecutorException {
        if (this.variables.containsKey(variableName)){
            Variable variable = this.variables.get(variableName);
            variable.assignVariable(valueType);
        }
        else {
            throw new ScopeException(VARIABLE+variableName+MISSING);
        }
    }

    /**
     * Declare a new method with the given parameters.
     * @param methodName - the method's name.
     * @param parameters - the given parameters.
     * @throws ExecutorException - if a method of the same name already exists; if two parameters have the
     * same name; if the method was declared from inside a method.
     */
    public void declareMethod(String methodName, ArrayList<Parameter> parameters) throws ExecutorException {
        if (this.methods.containsKey(methodName)){
            throw new ScopeException(METHOD_OVERFLOW+methodName);
        }
        this.verifyMethodParameters(parameters);
        this.methods.put(methodName, parameters);
    }

    /**
     * Verify that each of the given parameters has a different name.
     * @param parameters - the given parameters.
     * @throws ExecutorException - if the verification failed.
     */
    private void verifyMethodParameters(ArrayList<Parameter> parameters) throws ExecutorException {
        ArrayList<String> verifiedParameters = new ArrayList<>();
        for (Parameter parameter: parameters){
            if (verifiedParameters.contains(parameter.getParameterName())){
                throw new ScopeException(PARAMETER_OVERFLOW);
            }
            verifiedParameters.add(parameter.getParameterName());
        }
    }

    /**
     * Open a new method scope inside this scope.
     * @param methodName - the method's name.
     * @return - the new method scope.
     * @throws ExecutorException - if the method was opened from inside a method.
     */
    public MethodScope openMethodScope(String methodName) throws ExecutorException {
        MethodScope methodScope = new MethodScope();
        methodScope.parent = this;
        this.addParametersToMethodScope(methodName, methodScope);
        return methodScope;
    }

    /**
     * Receive the name of an already declared method, and the method scope representing it, and add each of
     * the method's parameters to that method scope as an initialized variable.
     * @param methodName - the given method's name.
     * @param methodScope - the given method scope representing the method.
     * @throws ExecutorException - if the parameters's value references a missing or uninitialized variable;
     * or if the variable construction was illegal.
     */
    private void addParametersToMethodScope(String methodName, MethodScope methodScope) throws ExecutorException {
        boolean isFinal;
        Variable.Type variableType;
        String variableName;
        Value value;
        for (Parameter parameter: this.methods.get(methodName)){
            isFinal = parameter.isFinal();
            variableType = parameter.getParameterType();
            variableName = parameter.getParameterName();
            value = new Value(variableType);
            methodScope.defineVariable(isFinal, variableType, variableName, value);
        }
    }

    /**
     * Open a new if/while scope inside this scope.
     * @param conditions - the if/while statement's conditions.
     * @return - the new if/while scope.
     * @throws ExecutorException - if the method was opened in the global scope; or if the conditions were
     * illegal i.e. not boolean or referencing a missing/uninitialized variable.
     */
    public IfWhileScope openIfWhileScope(ArrayList<Value> conditions) throws ExecutorException {
        throw new ScopeException(GLOBAL_IF_WHILE);
    }

    /**
     * Perform a method call  to the given method, with the given values.
     * @param methodName - the method's name.
     * @param values - the given values.
     * @throws ExecutorException - if the method does not exist in this file's global scope; or if one
     * of the values references a missing or uninitialized variable; or if the method's parameters don't
     * match the given values.
     */
    public void callMethod(String methodName, ArrayList<Value> values) throws ExecutorException {
        throw new ScopeException(GLOBAL_METHOD_CALL);
    }

    /**
     * Get the parameters of the given method.
     * @param methodName - the method's name.
     * @return - the method's parameters.
     * @throws ExecutorException - if the method does not exist in this file's global scope.
     */
    protected ArrayList<Parameter> getMethodParameters(String methodName) throws ExecutorException {
        if (!this.methods.containsKey(methodName)){
            throw new ScopeException(ILLEGAL_METHOD_CALL+methodName+MISSING);
        }
        return this.methods.get(methodName);
    }

    /**
     * Send a return statement to this scope.
     * @throws ExecutorException - if this return statement was sent to a global scope.
     */
    public void sendReturnStatement() throws ExecutorException {
        throw new ScopeException(GLOBAL_RETURN_STATEMENT);
    }

    /**
     * Disconnect this scope from its parent.
     * @return - this scope's parent.
     * @throws ExecutorException - if this statement was sent to a method scope without a return statement
     * immediately preceding it; if this statement was sent to a global scope.
     */
    public Scope closeScope() throws ExecutorException {
        throw new ScopeException(GLOBAL_CLOSE_STATEMENTS);
    }

    /**
     * Verify that the scope receiving this statement is global.
     * @throws ExecutorException - if this statement was sent to a local scope.
     */
    public void endCode() throws ExecutorException {
    }

}
