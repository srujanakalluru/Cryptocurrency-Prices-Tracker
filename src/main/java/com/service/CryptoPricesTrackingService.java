package com.service;

import com.entity.BitcoinData;

import java.util.List;

public interface CryptoPricesTrackingService {
    void checkAndReportPrice();
    List<BitcoinData> getPriceDetails(String date, Integer limit, Integer offset);
}
