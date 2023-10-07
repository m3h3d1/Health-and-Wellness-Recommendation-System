package com.healthapp.recommendationservicemanual.networks;

public class InternalCommunicationException extends Exception {
    public InternalCommunicationException(){
        super("Internal communication failure");
    }
}