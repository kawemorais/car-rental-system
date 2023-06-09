package br.com.system.carrental.exception;

import br.com.system.carrental.dtos.exceptions.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value= {EntityNotFoundExeption.class})
    public ResponseEntity<Object> userNotFoundExceptionHandler(EntityNotFoundExeption error){

        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ExceptionDTO exceptionDTO = new ExceptionDTO(
                ZonedDateTime.now(ZoneId.of("-3")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                notFound,
                error.getMessage()
        );

        return new ResponseEntity<>(exceptionDTO, notFound);
    }

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public ResponseEntity<Object> cannotCreateUserExceptionHandler(MethodArgumentNotValidException error){

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        List<String> fieldErrorsList = new ArrayList<>();

        error.getBindingResult().getFieldErrors().forEach(fieldError -> fieldErrorsList.add(fieldError.getDefaultMessage()));

        ExceptionDTO exceptionDTO = new ExceptionDTO(
                ZonedDateTime.now(ZoneId.of("-3")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                badRequest,
                "Erro ao criar usuário",
                fieldErrorsList
        );

        return new ResponseEntity<>(exceptionDTO, badRequest);
    }

    @ExceptionHandler(value = {SomePropertyAlreadyInUseException.class})
    public ResponseEntity<Object> usernameAlreadyInUseExceptionHandler(SomePropertyAlreadyInUseException error){
        HttpStatus conflict = HttpStatus.CONFLICT;

        ExceptionDTO exceptionDTO = new ExceptionDTO(
                ZonedDateTime.now(ZoneId.of("-3")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                conflict,
                error.getMessage()
        );

        return new ResponseEntity<>(exceptionDTO, conflict);
    }

    @ExceptionHandler(value = {InvalidFabricationYearException.class})
    public ResponseEntity<Object> invalidFabricationYearExceptionHandler(InvalidFabricationYearException error){
        HttpStatus conflict = HttpStatus.NOT_FOUND;

        ExceptionDTO exceptionDTO = new ExceptionDTO(
                ZonedDateTime.now(ZoneId.of("-3")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                conflict,
                error.getMessage()
        );

        return new ResponseEntity<>(exceptionDTO, conflict);
    }
}
