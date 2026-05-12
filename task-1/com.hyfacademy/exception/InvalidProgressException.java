package com.hyfacademy.exception;

public class InvalidProgressException extends EnrolmentException{
    private final int attemptedValue;

    public InvalidProgressException(int attemptedValue){
        super(String.format("Progress must be between 0 and 100, but received: %d", attemptedValue));
        this.attemptedValue = attemptedValue;
    }

    public int getAttemptedValue(){return attemptedValue;}


}