package com.petition.platform.ooprequirements;

/**
 * Exception thrown when an invalid argument list is provided.
 */
public class InvalidArgumentListException extends Exception{
    /**
     * Constructs an InvalidArgumentListException with the specified error message.
     *
     * @param message The error message.
     */
    public InvalidArgumentListException(String message) {
        super(message);
    }
}