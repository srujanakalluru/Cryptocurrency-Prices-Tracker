package com.controller;

import com.entity.BitcoinData;
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

    @InjectMocks
    private CryptoPricesTrackingController cryptoPricesTrackingController;

    @Mock
    CryptoPricesTrackingService cryptoPricesTrackingService;

    @Test
    void testGetPriceDetails_success() {
        BitcoinData data = BitcoinData.builder().id(2L).price(20000D).date(DateUtils.getDate("09-08-2022")).build();
        when(cryptoPricesTrackingService.getPriceDetails(any(String.class),any(),any())).thenReturn(Collections.singletonList(data));
        ResponseEntity<List<BitcoinData>> response = Assertions.assertDoesNotThrow(()->cryptoPricesTrackingController.getPriceDetails("09-08-2022",null,null));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
