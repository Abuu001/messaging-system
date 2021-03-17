package com.trimeo.Broadcastservice.domains;

public enum MessageTypes {
    TRANSACTIONAL, BROADCAST, UPLOAD;

    public static MessageTypes getDefault(){
        return TRANSACTIONAL;
    }
}
