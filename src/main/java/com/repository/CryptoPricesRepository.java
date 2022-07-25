package com.repository;

import com.entity.BitcoinData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CryptoPricesRepository extends PagingAndSortingRepository<BitcoinData, Long> {
    List<BitcoinData> findAllByDateBetween(LocalDateTime start, LocalDateTime end, Pageable paging);
    List<BitcoinData> findAllByDateBetween(LocalDateTime start, LocalDateTime end);

}