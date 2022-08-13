package com.controller;

import com.entity.BitcoinData;
import com.service.CryptoPricesTrackingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
@Validated
@Api(tags = "discovery")
public class CryptoPricesTrackingController {
    CryptoPricesTrackingService service;

    @Autowired
    public CryptoPricesTrackingController(CryptoPricesTrackingService service) {
        this.service = service;
    }

    @ApiOperation("Retrieve current and historical bitcoin price information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK Successfully fetched hospital data", response = List.class),
            @ApiResponse(code = 400, message = "Request is not well-formed, syntactically incorrect, or violates schema.", response = HttpClientErrorException.BadRequest.class),
            @ApiResponse(code = 500, message = "An internal server error has occured", response = InternalError.class)
    })
    @GetMapping(value = "/btc", produces = {"application/json"})
    public ResponseEntity<List<BitcoinData>> getPriceDetails(@Pattern(regexp = "^([0-2]\\d|(3)[0-1])(-)(((0)\\d)|((1)[0-2]))(-)\\d{4}$", message = "must match the pattern: dd-MM-yyyy") @RequestParam("date") String date, @RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset) {
        return ResponseEntity.ok(service.getPriceDetails(date, limit, offset));
    }
}
