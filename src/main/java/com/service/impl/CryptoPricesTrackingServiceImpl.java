package com.service.impl;

import com.client.CoinGeckoRestApi;
import com.configuration.AlertConfig;
import com.configuration.EmailConfig;
import com.dto.CryptoPricesOutput;
import com.entity.BitcoinData;
import com.repository.CryptoPricesRepository;
import com.service.CryptoPricesTrackingService;
import com.service.EmailService;
import com.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CryptoPricesTrackingServiceImpl implements CryptoPricesTrackingService {
    private final CoinGeckoRestApi coinGeckoRestApi;
    private final CryptoPricesRepository cryptoPricesRepository;
    private final AlertConfig alertConfig;
    private final EmailService emailService;

    @Autowired
    public CryptoPricesTrackingServiceImpl(CoinGeckoRestApi coinGeckoRestApi, CryptoPricesRepository cryptoPricesRepository, EmailConfig emailConfig, AlertConfig alertConfig, EmailService emailService) {
        this.coinGeckoRestApi = coinGeckoRestApi;
        this.cryptoPricesRepository = cryptoPricesRepository;
        this.alertConfig = alertConfig;
        this.emailService = emailService;
    }

    /**
     * Method to check the bitcoin price, persist in the database and alert the user via email if the price
     * falls outside the configurable range
     */
    public void checkAndReportPrice() {
        CryptoPricesOutput body = coinGeckoRestApi.getCryptoDetails().getBody();

        if (null != body) {
            double currentValue = body.getBitcoin().getUsd();
            BitcoinData data = BitcoinData.builder().price(currentValue).date(LocalDateTime.now()).build();
            cryptoPricesRepository.save(data);

            if ((currentValue > alertConfig.alertPriceMax() || currentValue < alertConfig.alertPriceMin())) {
                emailService.sendEmailAlert(currentValue);
            }
        }
    }

    /**
     * @param date
     * @param limit
     * @param offset
     * @return Lit<BitcoinData>
     */
    @Override
    public List<BitcoinData> getPriceDetails(String date, Integer limit, Integer offset) {
        LocalDateTime from = DateUtils.getDate(date);
        LocalDateTime to = DateUtils.getNextDate(date);

        if (null != limit && null != offset) {
            Pageable paging = PageRequest.of(offset, limit);
            return cryptoPricesRepository.findAllByDateBetween(from, to, paging);
        } else {
            return cryptoPricesRepository.findAllByDateBetween(from, to);
        }
    }

}
