package com.controller;

import com.entity.BitcoinData;
import com.service.CryptoPricesTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
@Validated
public class CryptoPricesTrackingController {
    CryptoPricesTrackingService service;

    @Autowired
    public CryptoPricesTrackingController(CryptoPricesTrackingService service) {
        this.service = service;
    }

    @GetMapping(value = "/btc")
    public ResponseEntity<List<BitcoinData>> getPriceDetails(@Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(-)(((0)[0-9])|((1)[0-2]))(-)\\d{4}$") @RequestParam("date") String date, @RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset) {
        return ResponseEntity.ok(service.getPriceDetails(date, limit, offset));
    }
}
