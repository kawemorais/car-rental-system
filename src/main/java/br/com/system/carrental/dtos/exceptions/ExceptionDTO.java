package br.com.system.carrental.dtos.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ExceptionDTO {

    private String time;

    private HttpStatus httpStatus;

    private String message;

    private List<String> errorFields;

    public ExceptionDTO(String time, HttpStatus httpStatus, String message) {
        this.time = time;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ExceptionDTO(String time, HttpStatus httpStatus, String message, List<String> errorFields) {
        this.time = time;
        this.httpStatus = httpStatus;
        this.message = message;
        this.errorFields = errorFields;
    }
}
