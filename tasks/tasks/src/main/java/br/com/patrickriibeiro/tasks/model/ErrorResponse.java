package br.com.patrickriibeiro.tasks.model;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

public class ErrorResponse {

    private int status;
    private String message;

    public ErrorResponse(Builder builder) {
        this.message = builder.message;
        this.status = builder.status;
    }

    public ErrorResponse() {
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static Builder builderFrom(ErrorResponse response){
        return new Builder(response);
    }

    public static ErrorResponse internalError(RuntimeException exception) {
        return ErrorResponse.builder()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withMessage(exception.getMessage())
                .build();

    }

    public static ErrorResponse invalidArgumentsError(FieldError fieldError) {
        return ErrorResponse.builder()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(fieldError.getDefaultMessage())
                .build();
    }

    public static class Builder {

        private int status;
        private String message;

        public Builder(ErrorResponse response) {
            this.message = response.message;
            this.status = response.status;
        }

        public Builder() {
        }

        public Builder withStatus(int status) {
            this.status = status;
            return this;
        }
        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponse build(){
            return new ErrorResponse(this);
        }

    }


}
