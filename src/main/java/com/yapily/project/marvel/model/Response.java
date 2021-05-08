package com.yapily.project.marvel.model;

import org.springframework.http.HttpStatus;

public class Response {
    private Character character;
    private HttpStatus httpStatus;
    private String message;

    public Response(Character character, HttpStatus httpStatus, String message) {
        this.character = character;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public Response(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
