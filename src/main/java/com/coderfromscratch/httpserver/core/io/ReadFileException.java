package com.coderfromscratch.httpserver.core.io;

public class ReadFileException extends Throwable {
    public ReadFileException() {
    }

    public ReadFileException(String message) {
        super(message);
    }

    public ReadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadFileException(Throwable cause) {
        super(cause);
    }

    public ReadFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
