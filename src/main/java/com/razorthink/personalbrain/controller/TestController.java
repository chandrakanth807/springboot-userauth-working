package com.razorthink.personalbrain.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest" )
public class TestController extends AbstractWebappController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);


    @RequestMapping(value = "/test" , method = RequestMethod.GET)
    public ResponseEntity test() {
        String hello = "Hello World!";
        return buildResponse(hello);
    }
}
