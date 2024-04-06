package org.client.exceptions;

public class MaxScriptDepthReachedException extends Exception {
    public MaxScriptDepthReachedException(int depth) {
        super("Maximum script depth reached.");
    }
}
