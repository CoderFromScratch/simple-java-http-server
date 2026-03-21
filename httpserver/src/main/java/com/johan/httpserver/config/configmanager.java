package com.johan.httpserver.config;

import com.johan.httpserver.util.json;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class configmanager {
    private static configmanager myconfigmanager;
    private static configuration mycurrentconfig;
    
    private configmanager(){
        
    }

    public static configmanager getInstance(){
        if(myconfigmanager==null)
            myconfigmanager = new configmanager();
        return myconfigmanager;
    }

    //method for loading a config file by the path provided
    public void loadConfigFile(String filePath) {
        FileReader fr = null;
        try {
            fr = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ((i = fr.read())!=-1){
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigException(e);
        }
        JsonNode conf = null;
        try {
            conf = json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigException("Error parsing configuration file", e);
        }
        try {
            mycurrentconfig = json.fromJson(conf, configuration.class);
        } catch (IOException e) {
            throw new HttpConfigException("Error parsing configuration file, internal", e);
        }
    }

    //method for returning currently loaded config
    public configuration getCurrentConfig(){
        if (mycurrentconfig == null){
            throw new HttpConfigException("No Current Configuration Set");
        }
        return mycurrentconfig;
    }
}
