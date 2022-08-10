package com.service;

import com.client.CoinGeckoRestApi;
import com.configuration.AlertConfig;
import com.dto.Bitcoin;
import com.dto.CryptoPricesOutput;
import com.entity.BitcoinData;
import com.repository.CryptoPricesRepository;
import com.service.impl.CryptoPricesTrackingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptoPricesTrackingServiceTest {

    @InjectMocks
    private CryptoPricesTrackingServiceImpl cryptoPricesTrackingServiceImpl;

    @Mock
    CoinGeckoRestApi coinGeckoRestApi;

    @Mock
    ResponseEntity<CryptoPricesOutput> cryptoDetails;

    @Mock
    CryptoPricesRepository cryptoPricesRepository;

    @Mock
    EmailService emailService;

    @Mock
    AlertConfig alertConfig;


    @Test
    void testCheckAndReportPrice_GreaterThanMax() {
        //given
        CryptoPricesOutput body = new CryptoPricesOutput();
        Bitcoin bitcoin = new Bitcoin();
        bitcoin.setUsd(30000d);
        body.setBitcoin(bitcoin);

        //when
        when(cryptoDetails.getBody()).thenReturn(body);
        when(coinGeckoRestApi.getCryptoDetails()).thenReturn(cryptoDetails);
        when(alertConfig.alertPriceMax()).thenReturn(28000d);
        doNothing().when(emailService).sendEmailAlert(anyDouble());

        //then
        Assertions.assertDoesNotThrow(()-> cryptoPricesTrackingServiceImpl.checkAndReportPrice());
        verify(cryptoPricesRepository,times(1)).save(any(BitcoinData.class));
        verify(emailService, times(1)).sendEmailAlert(anyDouble());
    }

    @Test
    void testCheckAndReportPrice_LessThanMin() {
        //given
        CryptoPricesOutput body = new CryptoPricesOutput();
        Bitcoin bitcoin = new Bitcoin();
        bitcoin.setUsd(20000d);
        body.setBitcoin(bitcoin);

        //when
        when(cryptoDetails.getBody()).thenReturn(body);
        when(coinGeckoRestApi.getCryptoDetails()).thenReturn(cryptoDetails);
        when(alertConfig.alertPriceMax()).thenReturn(28000d);
        when(alertConfig.alertPriceMin()).thenReturn(25000d);
        doNothing().when(emailService).sendEmailAlert(anyDouble());

        //then
        Assertions.assertDoesNotThrow(()-> cryptoPricesTrackingServiceImpl.checkAndReportPrice());
        verify(cryptoPricesRepository,times(1)).save(any(BitcoinData.class));
        verify(emailService, times(1)).sendEmailAlert(anyDouble());
    }

    @Test
    void testCheckAndReportPrice_WithinRange() {
        //given
        CryptoPricesOutput body = new CryptoPricesOutput();
        Bitcoin bitcoin = new Bitcoin();
        bitcoin.setUsd(26000d);
        body.setBitcoin(bitcoin);

        //when
        when(cryptoDetails.getBody()).thenReturn(body);
        when(coinGeckoRestApi.getCryptoDetails()).thenReturn(cryptoDetails);
        when(alertConfig.alertPriceMax()).thenReturn(28000d);
        when(alertConfig.alertPriceMin()).thenReturn(25000d);

        //then
        Assertions.assertDoesNotThrow(()-> cryptoPricesTrackingServiceImpl.checkAndReportPrice());
        verify(cryptoPricesRepository,times(1)).save(any(BitcoinData.class));
    }

    @Test
    void testCheckAndReportPrice_nullResponseFromCoinGecko() {
        //given
        CryptoPricesOutput body = null;

        //when
        when(cryptoDetails.getBody()).thenReturn(body);
        when(coinGeckoRestApi.getCryptoDetails()).thenReturn(cryptoDetails);

        //then
        Assertions.assertDoesNotThrow(()-> cryptoPricesTrackingServiceImpl.checkAndReportPrice());
        verify(cryptoPricesRepository,times(0)).save(any(BitcoinData.class));
    }

    @Test
    void getPriceDetails_WithOnlyDate() {
        //given
        String date = "10-08-2022";
        Integer limit = null;
        Integer offset = null;

        //when
        when(cryptoPricesRepository
                .findAllByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        //then
        Assertions.assertDoesNotThrow(()-> cryptoPricesTrackingServiceImpl.getPriceDetails(date, limit, offset));
        verify(cryptoPricesRepository,times(1))
                .findAllByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class));

    }

    @Test
    void getPriceDetails_WithDateLimitOffset() {
        //given
        String date = "10-08-2022";
        Integer limit = 1;
        Integer offset = 2;

        //when
        when(cryptoPricesRepository
                .findAllByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class))).thenReturn(null);

        //then
        Assertions.assertDoesNotThrow(()-> cryptoPricesTrackingServiceImpl.getPriceDetails(date, limit, offset));
        verify(cryptoPricesRepository,times(1))
                .findAllByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class));

    }

    @Test
    void getPriceDetails_WithDateLimitAndNoOffset() {
        //given
        String date = "10-08-2022";
        Integer limit = 1;
        Integer offset = null;

        //when
        when(cryptoPricesRepository
                .findAllByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        //then
        Assertions.assertDoesNotThrow(()-> cryptoPricesTrackingServiceImpl.getPriceDetails(date, limit, offset));
        verify(cryptoPricesRepository,times(1))
                .findAllByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

}
