package com.client;

import com.configuration.AlertConfig;
import com.configuration.RestTemplateConfig;
import com.dto.Bitcoin;
import com.dto.CryptoPricesOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinGeckoRestApiTest {
    @Mock
    RestTemplate restTemplate;

    @Mock
    AlertConfig alertConfig;

    @Mock
    RestTemplateConfig restTemplateConfig;

    @InjectMocks
    CoinGeckoRestApi coinGeckoRestApi;

    @Test
    void getCryptoDetailsTest_Success() {
        //given
        when(restTemplateConfig.externalUrl()).thenReturn("http://dummy/");
        when(alertConfig.coinId()).thenReturn("bitcoin");
        when(alertConfig.valueCurrency()).thenReturn("USD");

        //when
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(),ArgumentMatchers.<Class<CryptoPricesOutput>>any())).thenReturn(getCryptoPricesOutputResponseEntity());

        //then
        assertDoesNotThrow(() -> coinGeckoRestApi.getCryptoDetails());
        verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), ArgumentMatchers.<Class<CryptoPricesOutput>>any());
    }

    private static ResponseEntity<CryptoPricesOutput> getCryptoPricesOutputResponseEntity() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        Bitcoin bitcoin = new Bitcoin();
        bitcoin.setUsd(19667.66);
        CryptoPricesOutput cryptoPricesOutput = new CryptoPricesOutput();
        cryptoPricesOutput.setBitcoin(bitcoin);
        return new ResponseEntity<>(cryptoPricesOutput, header, HttpStatus.OK);
    }

}