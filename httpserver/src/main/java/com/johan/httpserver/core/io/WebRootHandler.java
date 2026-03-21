package com.johan.httpserver.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

public class WebRootHandler {
    private File webRoot;

    public WebRootHandler(String webRootPath) throws WebRootNotFoundException{
        webRoot = new File(webRootPath);
        if(!webRoot.exists() || !webRoot.isDirectory()){
            throw new WebRootNotFoundException();
        }
    }

    private boolean CheckIfEndsWithSlash(String relativePath){
        return relativePath.endsWith("/");
    }

    private boolean CheckIfRelativePathExists(String relativePath){
        File file = new File(webRoot, relativePath);
        if(!file.exists()) {
            return false;
        }
        try {
            if (!file.getCanonicalPath().startsWith(webRoot.getCanonicalPath())) {
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    public String GetFileType(String relativePath) throws FileNotFoundException{
        if (CheckIfEndsWithSlash(relativePath)) {
            relativePath += "Index.html";
        }
        if(CheckIfRelativePathExists(relativePath)) {
            throw new FileNotFoundException("File not found: "+relativePath);
        }
        File file = new File(webRoot, relativePath);;
        String mimeType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());
        if(mimeType == null){
            return "application/octet-stream";
        }
        return mimeType;
    }

    public byte[] GetFileByteArrayData(String relativePath) throws IOException {
        if (CheckIfEndsWithSlash(relativePath)) {
            relativePath += "Index.html";
        }
        if(CheckIfRelativePathExists(relativePath)) {
            throw new FileNotFoundException("File not found: "+relativePath);
        }
        File file = new File(webRoot, relativePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] fileBytes = new byte[(int)file.length()];
        try{
            fis.read(fileBytes);
            fis.close();
        }catch (IOException e){
            throw new IOException();
        }
        return fileBytes;
    }
}
