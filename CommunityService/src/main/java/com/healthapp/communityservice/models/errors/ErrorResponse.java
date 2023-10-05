package com.healthapp.communityservice.models.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String exception;
    private String operation;
    private String type;
    private String message;
    private String status;
    private Date timeStamp;
    private String apiPath;
}
