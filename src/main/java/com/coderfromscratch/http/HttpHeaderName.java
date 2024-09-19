package com.coderfromscratch.http;

public enum HttpHeaderName {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");

    public final String headerName;

    HttpHeaderName(String headerName) {
        this.headerName = headerName;
    }
}
