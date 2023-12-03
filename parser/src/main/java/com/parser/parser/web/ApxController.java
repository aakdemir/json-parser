package com.parser.parser.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parser.parser.models.ResponseApi;
import com.parser.parser.service.ApxService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/apx")
public class ApxController {

    ApxService apxService;

    @GetMapping("/parse")
    ResponseEntity<ResponseApi> getParsedApxData(){
        return new ResponseEntity<>(apxService.parseApx(), HttpStatus.OK);
    }

}