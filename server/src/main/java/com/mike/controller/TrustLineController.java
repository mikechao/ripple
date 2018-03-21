package com.mike.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mike.Transaction;
import com.mike.service.TrustLineService;

@Controller
public class TrustLineController {

    private final TrustLineService service;

    private final Logger logger = LoggerFactory.getLogger(TrustLineController.class);

    @Autowired
    public TrustLineController(TrustLineService service) {
        this.service = service;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<?> send(@RequestBody Transaction transaction) {
        logger.debug("Processing {}", transaction);
        try {
            service.startTransaction(transaction);
        } catch (Exception e) {
            logger.debug("Processed " + transaction + " but with error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            //return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        logger.debug("Processed {} successfully", transaction);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
