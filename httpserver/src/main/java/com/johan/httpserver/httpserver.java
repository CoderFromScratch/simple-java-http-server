package com.johan.httpserver;

import com.johan.httpserver.config.configuration;
import com.johan.httpserver.config.configmanager;
import com.johan.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class httpserver {

    private final static Logger LOGGER = LoggerFactory.getLogger(httpserver.class);
    public static void main(String[] args)
    {
        LOGGER.info("Server Starting");

        System.out.println("Server starting...");

        configmanager.getInstance().loadConfigFile(("httpserver/src/main/resources/http.json"));
        configuration conf = configmanager.getInstance().getCurrentConfig();

        LOGGER.info("Using Port: "+conf.getPort());
        LOGGER.info("Using WebRoot: "+ conf.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}