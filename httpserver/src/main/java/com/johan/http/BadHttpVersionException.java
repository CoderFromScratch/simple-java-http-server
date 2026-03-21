package com.johan.http;

public class BadHttpVersionException extends Exception {
    public BadHttpVersionException() {
        super("Unsupported or malformed HTTP version");
    }

    public BadHttpVersionException(String message) {
        super(message);
    }
}
