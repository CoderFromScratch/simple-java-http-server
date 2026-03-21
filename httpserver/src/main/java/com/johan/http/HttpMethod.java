package com.johan.http;

public enum HttpMethod {
    GET, HEAD;
    public static final int MAX_LENGTH;

    //this checks all the valid METHODS declared in the server and sets max_length as the biggest header
    static{
        int tempMaxLength = -1;
        for (HttpMethod method : HttpMethod.values()){
            if(method.name().length()>tempMaxLength){
                tempMaxLength = method.name().length();
            }
        }
        MAX_LENGTH = tempMaxLength;
    }
}
