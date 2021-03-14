package com.trimeo.Broadcastservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ExceptionHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable throwable) {
        // wrong payload in Message consumed from queue
        log.info(throwable.getCause().getMessage());
        if(throwable.getCause() instanceof ConstraintViolationException ){
            ConstraintViolationException constraintViolationException =
                    (ConstraintViolationException) throwable.getCause();

            List<String> errorList = new ArrayList<>();
            // fetch violations and add to errorlist
            for(ConstraintViolation<?> violation :
                    constraintViolationException.getConstraintViolations()){
                errorList.add(violation.getPropertyPath().toString().split("\\.")[2] + " : " +violation.getMessage());
            }

            log.info("Error in payload fetched from queue :: " +errorList.toString());
        }
    }
}
