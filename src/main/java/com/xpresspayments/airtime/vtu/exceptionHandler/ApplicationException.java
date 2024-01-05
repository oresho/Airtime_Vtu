package com.xpresspayments.airtime.vtu.exceptionHandler;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApplicationException extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public ApplicationException(){
        this("Error Processing Request!");
    }

    public ApplicationException(String message){
        super(message);
    }

    public ApplicationException(String message, HttpStatus status) {
        this(message);
        this.status = status;
    }

}
