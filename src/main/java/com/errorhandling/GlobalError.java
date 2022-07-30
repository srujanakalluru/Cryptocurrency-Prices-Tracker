package com.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GlobalError {
    private String message;
    private String errorReason;
    private LocalDateTime timeStamp;
}
