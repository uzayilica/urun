package com.uzay.urun.exception;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String errorStatusCode;
    private LocalDateTime timestamp;
}
