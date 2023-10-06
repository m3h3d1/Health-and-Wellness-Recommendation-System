package com.healthapp.dataanalysisservice.network;

public class InternalCommunicationException extends Exception {
    public InternalCommunicationException(){
        super("Internal communication failure");
    }
}
