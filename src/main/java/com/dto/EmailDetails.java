package com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmailDetails {
    private String recipientEmail;
    private String recipientName;
    private String senderEmail;
    private String senderName;
    private String msgBody;
    private String subject;
}