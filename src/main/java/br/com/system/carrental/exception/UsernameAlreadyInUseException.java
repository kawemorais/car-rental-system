package br.com.system.carrental.exception;

public class UsernameAlreadyInUseException extends RuntimeException{
    public UsernameAlreadyInUseException(String message) {
        super(message);
    }
}
