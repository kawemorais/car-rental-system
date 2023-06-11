package br.com.system.carrental.exception;

public class EntityNotFoundExeption extends RuntimeException{
    public EntityNotFoundExeption(String message) {
        super(message);
    }
}
