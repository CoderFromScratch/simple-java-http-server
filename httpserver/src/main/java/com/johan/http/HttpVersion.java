package com.johan.http;

public enum HttpVersion {

    HTTP_1_1("HTTP/1.1", 1, 1),
    HTTP_1_0("HTTP/1.0",1,0);

    public final String literal;
    public final int major;
    public final int minor;

    HttpVersion(String literal, int major, int minor){
        this.literal = literal;
        this.major = major;
        this.minor = minor;
    }

    public static HttpVersion getBestCompatibleVersion(String version) throws BadHttpVersionException {
        if(version == null){
            throw new BadHttpVersionException("Version NULL");
        }
        for(HttpVersion v : HttpVersion.values()){
            if(v.literal.equals(version)){
                return v;
            }
        }
        return null;
    }
}