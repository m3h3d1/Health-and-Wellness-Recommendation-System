package com.healthapp.dataanalysisservice.network;


import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() > 399) {
            return new InternalCommunicationException();
        }
        return FeignException.errorStatus(methodKey, response);
    }
}
