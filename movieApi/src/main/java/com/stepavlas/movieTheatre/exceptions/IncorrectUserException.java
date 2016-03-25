package com.stepavlas.movieTheatre.exceptions;

/**
 * Created by Степан on 19.03.2016.
 */
public class IncorrectUserException extends Exception {
    public IncorrectUserException() {
    }

    public IncorrectUserException(String message) {
        super(message);
    }
}
