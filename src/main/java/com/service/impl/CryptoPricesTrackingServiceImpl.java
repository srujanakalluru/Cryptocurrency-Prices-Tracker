package com.service.impl;

import com.client.CoinGeckoRestApi;
import com.configuration.AlertConfig;
import com.dto.CryptoPricesOutput;
import com.dto.EmailDetails;
import com.entity.BitcoinData;
import com.repository.CryptoPricesRepository;
import com.service.CryptoPricesTrackingService;
import com.service.EmailService;
import com.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${recipient.email}")
    private String recipientEmail;

    @Autowired
    public CryptoPricesTrackingServiceImpl(CoinGeckoRestApi coinGeckoRestApi, CryptoPricesRepository cryptoPricesRepository, AlertConfig alertConfig, EmailService emailService) {
        this.coinGeckoRestApi = coinGeckoRestApi;
        this.cryptoPricesRepository = cryptoPricesRepository;
        this.alertConfig = alertConfig;
        this.emailService = emailService;
    }

    public void checkAndReportPrice() {
        CryptoPricesOutput body = coinGeckoRestApi.getCryptoDetails().getBody();
        double currentValue = -1;

        if (null != body) {
            currentValue = body.getBitcoin().getUsd();
        }
        BitcoinData data = BitcoinData.builder().price(currentValue).date(LocalDateTime.now()).build();

        cryptoPricesRepository.save(data);
        if ((currentValue > alertConfig.alertPriceMax() || currentValue < alertConfig.alertPriceMin()) && currentValue != -1) {
            alertUser(currentValue);
        }

    }

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

    private void alertUser(double alertPrice) {
        log.info("New price is {} at {} ", alertPrice, java.time.LocalTime.now());
        EmailDetails emailDetails = EmailDetails.builder().recipient(recipientEmail)
                .msgBody("New price is: " + alertPrice)
                .subject("Price Change Alert")
                .build();
        emailService.sendEmail(emailDetails);
    }


}
