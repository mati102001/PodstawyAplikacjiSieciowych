package com.example.pas22Front.restClients.exceptions;

public class GeneralBadRequestException extends Exception{
    private int status = 400;
    public GeneralBadRequestException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public GeneralBadRequestException(String errorMessage, Throwable err, int status) {
        super(errorMessage, err);
        this.status = status;
    }
    public GeneralBadRequestException(String errorMessage, int status) {
        super(errorMessage);
        this.status = status;
    }
    public GeneralBadRequestException(String errorMessage) {
        super(errorMessage);
    }
    public GeneralBadRequestException(int status) {
        super("Bad request");
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}
