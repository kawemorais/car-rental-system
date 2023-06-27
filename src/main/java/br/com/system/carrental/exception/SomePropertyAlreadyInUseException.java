package br.com.system.carrental.exception;

public class SomePropertyAlreadyInUseException extends RuntimeException{
    public SomePropertyAlreadyInUseException(String message) {
        super(message);
    }
}
