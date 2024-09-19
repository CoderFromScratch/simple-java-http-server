package com.coderfromscratch.httpserver.core;

import com.coderfromscratch.httpserver.core.io.WebRootHandler;
import com.coderfromscratch.httpserver.core.io.WebRootNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    private WebRootHandler webRootHandler;

    public ServerListenerThread(int port, String webroot) throws IOException, WebRootNotFoundException {
        this.port = port;
        this.webroot = webroot;
        this.webRootHandler = new WebRootHandler(webroot);
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {

        try {

            while ( serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                LOGGER.info(" * Connection accepted: " + socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket, webRootHandler);
                workerThread.start();

            }

        } catch (IOException e) {
            LOGGER.error("Problem with setting socket", e);
        } finally {
            if (serverSocket!=null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {}
            }
        }

    }
}
