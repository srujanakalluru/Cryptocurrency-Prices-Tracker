package com.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.validation.ConstraintViolationException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CryptoPricesTrackingControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("This test should run successfully for a valid date format as input")
    void testGetPriceDetailsWithDate_success() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/prices/btc").param("date", "09-08-2022")).andExpect(status().isOk()).andReturn();
        System.out.println(result);
    }

    @Test
    @DisplayName("This test should fail for any date pattern other than dd-MM-yyyy")
    void testGetPriceDetailsWithDate_invalidDateFormat() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/prices/btc").param("date", "09/08/2022")).andExpect(status().isBadRequest()).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ConstraintViolationException);

    }

}
