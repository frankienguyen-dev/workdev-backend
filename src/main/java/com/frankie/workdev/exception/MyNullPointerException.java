package com.frankie.workdev.exception;

public class MyNullPointerException extends RuntimeException{
    private String resourceName;
    ;
    private String fieldName;
    private String fieldValue;

    public MyNullPointerException(String resourceName,
                                     String fieldName, String fieldValue) {
        super(String.format("%s does not belong to %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
