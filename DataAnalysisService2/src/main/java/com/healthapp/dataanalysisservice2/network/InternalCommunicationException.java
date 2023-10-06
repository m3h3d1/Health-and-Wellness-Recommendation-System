package com.healthapp.dataanalysisservice2.network;

public class InternalCommunicationException extends Exception {
    public InternalCommunicationException(){
        super("Internal communication failure");
    }
}
