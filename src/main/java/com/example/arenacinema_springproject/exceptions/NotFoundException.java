package com.example.arenacinema_springproject.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg){
        super(msg);
    }
}
