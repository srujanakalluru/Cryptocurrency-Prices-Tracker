package com.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GlobalError {
    private int errorCode;
    private HttpStatus status;
    private String errorReason;
    private LocalDateTime timeStamp;
}
