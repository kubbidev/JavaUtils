package com.kubbidev.java.util.parser;

/**
 * An exception class representing syntax errors that may occur during mathematical expression evaluation.
 * This exception is thrown when there is a problem with the syntax of the expression or when unknown functions are used.
 */
public class SyntaxErrorException extends Exception {

    /**
     * Constructs a new SyntaxErrorException with the specified error message.
     *
     * @param message The error message describing the syntax error.
     */
    public SyntaxErrorException(String message) {
        super(message);
    }
}

