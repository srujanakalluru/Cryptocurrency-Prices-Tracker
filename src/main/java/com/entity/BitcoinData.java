package com.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class BitcoinData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    LocalDateTime date;
    @Column
    double price;
    public BitcoinData() {
        //Adding default constructor for hiberante
    }
}
