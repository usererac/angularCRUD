package com.in50us.springboot.util;


public class CustomErrorType {

    private String errorMessage;

    public CustomErrorType(String errorMessaage){
        this.errorMessage= errorMessaage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
