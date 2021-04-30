package com.trimeo.creditservice.interfaces;

public interface validationService {

    boolean clientHasEnoughCredits(String clientCode);
    boolean clientExists(String clientCode);
}
