package com.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class BitcoinData {

    public BitcoinData() {
        //Adding default constructor for hibernate instantiation
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    LocalDateTime date;

    @Column
    double price;
}
