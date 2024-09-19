package com.coderfromscratch.http;

public class HttpResponse extends HttpMessage {

    private final String CRLF = "\r\n";

    // status-line = HTTP-version SP status-code SP reason-phrase CRLF
    private String httpVersion;

    private HttpStatusCode statusCode;

    private String reasonPhrase = null;

    private HttpResponse() {
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        if (reasonPhrase == null && statusCode!=null) {
            return statusCode.MESSAGE;
        }
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public byte[] getResponseBytes() {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(httpVersion)
                .append(" ")
                .append(statusCode.STATUS_CODE)
                .append(" ")
                .append(getReasonPhrase())
                .append(CRLF);

        for (String headerName: getHeaderNames()) {
            responseBuilder.append(headerName)
                    .append(": ")
                    .append(getHeader(headerName))
                    .append(CRLF);
        }

        responseBuilder.append(CRLF);

        byte[] responseBytes = responseBuilder.toString().getBytes();

        if (getMessageBody().length == 0)
            return responseBytes;

        byte[] responseWithBody = new byte[responseBytes.length + getMessageBody().length];
        System.arraycopy(responseBytes, 0, responseWithBody, 0, responseBytes.length);
        System.arraycopy(getMessageBody(), 0, responseWithBody, responseBytes.length, getMessageBody().length);

        return responseWithBody;
    }

    public static class Builder {

        private HttpResponse response = new HttpResponse();

        public Builder httpVersion ( String httpVersion) {
            response.setHttpVersion(httpVersion);
            return this;
        }

        public Builder statusCode(HttpStatusCode statusCode) {
            response.setStatusCode(statusCode);
            return this;
        }

        public Builder reasonPhrase(String reasonPhrase) {
            response.setReasonPhrase(reasonPhrase);
            return this;
        }

        public Builder addHeader(String headerName, String headerField) {
            response.addHeader(headerName, headerField);
            return this;
        }

        public Builder messageBody(byte[] messageBody) {
            response.setMessageBody(messageBody);
            return this;
        }

        public HttpResponse build() {
            return response;
        }

    }
}
