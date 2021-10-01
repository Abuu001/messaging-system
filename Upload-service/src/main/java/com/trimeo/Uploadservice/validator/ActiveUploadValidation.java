package com.trimeo.Uploadservice.validator;

import org.springframework.stereotype.Component;

@Component
public class ActiveUploadValidation implements com.trimeo.Uploadservice.interfaces.ActiveUploadValidation {


    @Override
    public  boolean isUploadActive(Integer status) {

        if(status == 0) return false;

        return true;
    }
}
