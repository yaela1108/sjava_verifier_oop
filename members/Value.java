package oop.ex6.members;

/**
 * This class represents a value in an s-java file, which is either a legal input for an s-java type or a
 * reference to an initialized variable.
 */
public class Value {

    /*----=   Instance Data Members  =----*/

    private final Variable.Type valueType;
    private final String valueName;

    /*----=   Constructor  =----*/

    /**
     * A constructor for a value which is a legal input for an s-java type.
     * @param valueType - the s-java type corresponding to this value.
     */
    public Value(Variable.Type valueType){
        this.valueType = valueType;
        this.valueName = null;
    }

    /**
     * A constructor for a value which is a reference to an initialized variable.
     * @param valueName - the variable's name.
     */
    public Value(String valueName){
        this.valueType = null;
        this.valueName = valueName;
    }

    /*----=   Instance Methods  =----*/

    /**
     * @return the s-java type of this value if it is an s-java type input; null otherwise.
     */
    public Variable.Type getValueType(){
        return this.valueType;
    }

    /**
     * @return the name of this value if it is a reference to a variable; null otherwise.
     */
    public String getValueName(){
        return this.valueName;
    }
}
