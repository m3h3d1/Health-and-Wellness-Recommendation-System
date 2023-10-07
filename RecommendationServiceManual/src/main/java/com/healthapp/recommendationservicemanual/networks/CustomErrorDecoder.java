package com.healthapp.recommendationservicemanual.networks;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * CustomErrorDecoder is a Feign error decoder that handles HTTP error responses and customizes error handling.
 */
public class CustomErrorDecoder implements ErrorDecoder {
    /**
     * Decodes the HTTP error response and returns an appropriate exception based on the response status code.
     *
     * @param methodKey The Feign method key.
     * @param response  The HTTP response that resulted in an error.
     * @return An Exception representing the decoded error.
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() > 399) {
            return new InternalCommunicationException();
        }
        return FeignException.errorStatus(methodKey, response);
    }
}
