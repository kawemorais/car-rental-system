package br.com.system.carrental.exception;

import br.com.system.carrental.dtos.exceptionsDTO.ExceptionDTO;
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

    @ExceptionHandler(value= {UserNotFoundExeption.class})
    public ResponseEntity<Object> userNotFoundExceptionHandler(UserNotFoundExeption error){

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

    @ExceptionHandler(value = {UsernameAlreadyInUseException.class})
    public ResponseEntity<Object> usernameAlreadyInUseExceptionHandler(UsernameAlreadyInUseException error){
        HttpStatus conflict = HttpStatus.CONFLICT;

        ExceptionDTO exceptionDTO = new ExceptionDTO(
                ZonedDateTime.now(ZoneId.of("-3")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                conflict,
                error.getMessage()
        );

        return new ResponseEntity<>(exceptionDTO, conflict);
    }
}
