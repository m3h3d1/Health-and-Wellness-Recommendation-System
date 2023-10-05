package com.healthapp.communityservice.networks;

public class InternalCommunicationException extends Exception {
    public InternalCommunicationException(){
        super("Internal communication failure");
    }
}