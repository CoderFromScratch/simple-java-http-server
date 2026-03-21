package com.johan.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass(){
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpReq() {
        HttpRequest request = null;
        try {
            request = HttpParser.parseHttpReq(generateValidTestCase());
        } catch (HttpParsingException e){
            fail(e);
        }
        assertNotNull(request);
    }

    @Test
    void parseHttpReqBadMethod() {
        try {
            HttpRequest request = HttpParser.parseHttpReq(generateValidTestCaseBadMethod());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpReqBadMethod2() {
        try {
            HttpRequest request = HttpParser.parseHttpReq(generateValidTestCaseBadMethod2());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpReqReqLineInvalidItems() {
        try {
            HttpRequest request = HttpParser.parseHttpReq(generateValidTestCaseReqLineInvalidItems());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQ);
        }
    }

    @Test
    void parseHttpReqEmptyReq() {
        try {
            HttpRequest request = HttpParser.parseHttpReq(generateValidTestCaseEmptyReqLine());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQ);
        }
    }


    private InputStream generateValidTestCase(){
        String rawData="GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "sec-ch-ua: \"Not:A-Brand\";v=\"99\", \"Google Chrome\";v=\"145\", \"Chromium\";v=\"145\"\r\n" +
                "sec-ch-ua-mobile: ?0\r\n" +
                "sec-ch-ua-platform: \"Windows\"\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "Accept-Language: en-US,en;q=0.9,fr;q=0.8\r\n"+
                "\r\n";
        InputStream ipStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return ipStream;
    }

    private InputStream generateValidTestCaseBadMethod(){
        String rawData="TF / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9,fr;q=0.8\r\n"+
                "\r\n";
        InputStream ipStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return ipStream;
    }

    private InputStream generateValidTestCaseBadMethod2(){
        String rawData="GETTTT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9,fr;q=0.8\r\n"+
                "\r\n";
        InputStream ipStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return ipStream;
    }

    private InputStream generateValidTestCaseReqLineInvalidItems(){
        String rawData="GET / AAAAA HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9,fr;q=0.8\r\n"+
                "\r\n";
        InputStream ipStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return ipStream;
    }

    private InputStream generateValidTestCaseEmptyReqLine(){
        String rawData="\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9,fr;q=0.8\r\n"+
                "\r\n";
        InputStream ipStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return ipStream;
    }
}