package com.coderdot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDTO {
    private Long id;
    private String message;
    private LocalDate timestamp;
    private boolean isRead;
    private Long recipient;


}
