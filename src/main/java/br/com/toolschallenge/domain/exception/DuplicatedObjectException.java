package br.com.toolschallenge.domain.exception;

public class DuplicatedObjectException extends RuntimeException{

    public DuplicatedObjectException(String msg) {
        super(msg);
    }
}
