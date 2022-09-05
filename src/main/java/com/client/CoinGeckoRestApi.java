package com.client;

import com.configuration.AlertConfig;
import com.configuration.RestTemplateConfig;
import com.dto.CryptoPricesOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
@Slf4j
public class CoinGeckoRestApi {
    private final RestTemplate restTemplate;
    private final AlertConfig alertConfig;
    private final RestTemplateConfig restTemplateConfig;


    @Autowired
    public CoinGeckoRestApi(RestTemplate restTemplate, AlertConfig alertConfig, RestTemplateConfig restTemplateConfig) {
        this.restTemplate = restTemplate;
        this.alertConfig = alertConfig;
        this.restTemplateConfig = restTemplateConfig;
    }

    public ResponseEntity<CryptoPricesOutput> getCryptoDetails() {
        String url = restTemplateConfig.externalUrl() + "?ids=" + alertConfig.coinId() + "&vs_currencies=" + alertConfig.valueCurrency();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, CryptoPricesOutput.class);
    }


}
