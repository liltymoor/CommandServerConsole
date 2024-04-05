package org.client.exceptions;

public class BadLogicWereEaten extends RuntimeException{
    public BadLogicWereEaten() {
        super("Something bad were eaten by program");
    }
}
