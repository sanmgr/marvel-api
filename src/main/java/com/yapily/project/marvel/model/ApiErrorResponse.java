package com.yapily.project.marvel.model;

import org.springframework.http.HttpStatus;

public class ApiErrorResponse {
    private int httpStatusCode;
    private HttpStatus httpStatus;

    public ApiErrorResponse(int httpStatusCode, HttpStatus httpStatus) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
