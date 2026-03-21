package com.johan.http;

import java.util.HashMap;
import java.util.Set;

public class HttpRequest {

    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion;
    private HttpVersion getBestCompatibleVersion;
    private HashMap<String, String> headers = new HashMap<>();

    HttpRequest() {
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public HttpVersion getBestCompatibleVersion() { return getBestCompatibleVersion; }

    public String getOriginalHttpVersion() { return  originalHttpVersion; }

    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    public String  getHeader(String headerName) {
        return headers.get(headerName.toLowerCase());
    }

    void setMethod(String methodName) throws HttpParsingException {
        for (HttpMethod method: HttpMethod.values()) {
            if(methodName.equals(method.name())){
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }

    public void setRequestTarget(String requestTarget) throws  HttpParsingException {
        if (requestTarget == null || requestTarget.isEmpty()){
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQ);
        }
        this.requestTarget = requestTarget;
    }

    public void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.getBestCompatibleVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if(this.getBestCompatibleVersion == null){
            throw new BadHttpVersionException();
        }
    }

    void addHeader(String headerName, String HeaderField){
        headers.put(headerName.toLowerCase(), HeaderField);
    }
}
