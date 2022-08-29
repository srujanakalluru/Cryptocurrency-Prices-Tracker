package com.controller;

import com.entity.BitcoinData;
import com.errorhandling.CryptoPricesTrackingException;
import com.service.CryptoPricesTrackingService;
import com.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoPricesTrackingControllerTest {

    @Mock
    CryptoPricesTrackingService cryptoPricesTrackingService;
    @Mock
    CryptoPricesTrackingException cryptoPricesTrackingException;
    @InjectMocks
    private CryptoPricesTrackingController cryptoPricesTrackingController;

    @Test
    void testGetPriceDetails_success() {
        //given
        List<BitcoinData> dataList = Collections.singletonList(BitcoinData.builder().id(2L).price(20000D).date(DateUtils.getDate("09-08-2022")).build());

        //when
        when(cryptoPricesTrackingService.getPriceDetails(any(String.class), any(), any())).thenReturn(dataList);

        //then
        ResponseEntity<List<BitcoinData>> response = Assertions.assertDoesNotThrow(() -> cryptoPricesTrackingController.getPriceDetails("09-08-2022", null, null));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetPriceDetails_failure() {
        //given and when
        when(cryptoPricesTrackingException.getMessage()).thenReturn("Custom cryptoPricesTrackingException");
        when(cryptoPricesTrackingService.getPriceDetails(any(String.class), any(), any())).thenThrow(cryptoPricesTrackingException);

        //then
        CryptoPricesTrackingException e = Assertions.assertThrows(CryptoPricesTrackingException.class, () -> cryptoPricesTrackingController.getPriceDetails("09/08/2022", null, null));
        Assertions.assertEquals("Custom cryptoPricesTrackingException", e.getMessage());
    }

}
