package com.eazybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse" ,
        description = "Schema to hold error response details"
)
@AllArgsConstructor
@Data
public class ErrorResponseDto {
    @Schema(description = "API Path that was called")
    private String apiPath;
    @Schema(description = "Error Code representing the error")
    private HttpStatus errorCode ;
    @Schema(description = "Error Message representing what happened")
    private String errorMessage;
    @Schema(description = "Error Time representing the time of error")
    private LocalDateTime errorTime;
}
