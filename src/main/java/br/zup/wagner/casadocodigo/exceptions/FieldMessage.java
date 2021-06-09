package br.zup.wagner.casadocodigo.exceptions;

public class FieldMessage {

    private String fieldName;
    private String message;


    @Deprecated
    public FieldMessage() {

    }


    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }


    public String getFieldName() {
        return fieldName;
    }


    public String getMessage() {
        return message;
    }
}
