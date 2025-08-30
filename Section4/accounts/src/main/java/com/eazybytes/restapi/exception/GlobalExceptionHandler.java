package com.eazybytes.restapi.exception;

import com.eazybytes.restapi.dto.ErrorResponseDto;
import com.eazybytes.restapi.dto.ResponseDto;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String , String> validError = new HashMap<>();
        List<ObjectError> validErrorList = ex.getBindingResult().getAllErrors() ;

        validErrorList.forEach( error -> {
            String fieldName = ((FieldError)error).getField() ;
            String validationMessage = error.getDefaultMessage() ;
            validError.put(fieldName , validationMessage);
        });
        return new ResponseEntity<>(validError , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception , WebRequest request){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false) ,
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage() ,
                LocalDateTime.now()
        ) ;
        return new ResponseEntity<>(errorResponseDto , HttpStatus.INTERNAL_SERVER_ERROR) ;
    }


    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException(CustomerAlreadyExistException exception , WebRequest request){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false) ,
                HttpStatus.BAD_REQUEST,
                exception.getMessage() ,
                LocalDateTime.now()
        ) ;
        return new ResponseEntity<>(errorResponseDto , HttpStatus.BAD_REQUEST) ;
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception , WebRequest request){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false) ,
                HttpStatus.NOT_FOUND,
                exception.getMessage() ,
                LocalDateTime.now()
        ) ;
        return new ResponseEntity<>(errorResponseDto , HttpStatus.NOT_FOUND) ;
    }

}
