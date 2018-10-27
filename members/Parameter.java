package oop.ex6.members;

/**
 * This class represents a parameter in an s-java file.
 */
public class Parameter {

    /*----=   Instance Data Members  =----*/

    private final boolean isFinal;
    private final Variable.Type parameterType;
    private final String parameterName;

    /*----=   Constructor  =----*/

    /**
     * A constructor which receives this parameter's final modifier, type, and name.
     */
    public Parameter(boolean isFinal, Variable.Type parameterType, String parameterName){
        this.isFinal = isFinal;
        this.parameterType = parameterType;
        this.parameterName = parameterName;
    }

    /*----=   Instance Methods  =----*/

    /**
     * @return this parameter's final modifier;
     */
    public boolean isFinal(){
        return this.isFinal;
    }

    /**
     * @return this parameter's type;
     */
    public Variable.Type getParameterType(){
        return this.parameterType;
    }

    /**
     * @return this parameter's name;
     */
    public String getParameterName(){
        return this.parameterName;
    }
}
