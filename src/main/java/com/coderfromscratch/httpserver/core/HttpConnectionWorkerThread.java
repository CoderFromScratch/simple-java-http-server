package com.coderfromscratch.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private final Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try(
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream()
        ) {

            String html = "<html><head><title>Simple Java HTTP Server</title></head>" +
                    "<body><h1>This page was served using my Simple Java HTTP Server</h1></body></html>";

            final String CRLF = "\r\n"; // 13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line  :   HTTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());

            LOGGER.info(" * Connection Processing Finished.");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            if (socket!= null) {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }
}
