package com.razorthink.personalbrain.controller;


import com.razorthink.personalbrain.bean.OperationStatus;
import com.razorthink.personalbrain.bean.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AbstractController {


    protected <T> ResponseEntity<Response<T>> buildResponse(T t )
    {
        Response<T> appResponse = new Response<>(t);
        appResponse.setStatus(OperationStatus.SUCCESS);
        appResponse.setHttpStatus(HttpStatus.OK);
        appResponse.setHttpStatusCode(appResponse.getHttpStatus().value());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    protected ResponseEntity<Response<String>> buildErrorResponse( Exception e )
    {
        Response<String> appResponse = new Response<>();
        appResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        appResponse.setResult(e.getMessage());
        appResponse.setStatus(OperationStatus.ERROR);
        appResponse.setHttpStatusCode(appResponse.getHttpStatus().value());
        return new ResponseEntity<>(appResponse, appResponse.getHttpStatus());
    }

    protected ResponseEntity<Response<String>> buildErrorResponse( Exception e, HttpStatus status, String message )
    {
        Response<String> appResponse = new Response<>();
        appResponse.setResult(message);
        appResponse.setStatus(OperationStatus.ERROR);
        appResponse.setHttpStatusCode(status.value());
        appResponse.setHttpStatus(status);
        return new ResponseEntity<>(appResponse, appResponse.getHttpStatus());
    }
}
