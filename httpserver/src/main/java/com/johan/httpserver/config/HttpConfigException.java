package com.johan.httpserver.config;

public class HttpConfigException extends RuntimeException{
    public HttpConfigException() {
    }

    public HttpConfigException(String message) {
        super(message);
    }

    public HttpConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpConfigException(Throwable cause) {
        super(cause);
    }
}
