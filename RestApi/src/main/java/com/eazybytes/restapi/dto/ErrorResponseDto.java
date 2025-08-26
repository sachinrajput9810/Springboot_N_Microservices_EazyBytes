package com.eazybytes.restapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "Error Response" ,
        description = "Schema to hold error response details"
)
public class ErrorResponseDto {
    @Schema(description = "API Path invoked by client" , example = "/api/v1/accounts")
    private String apiPath;

    @Schema(description = "Error Code" , example = "404")
    private HttpStatus errorCode ;

    @Schema(description = "Error Message" , example = "Not Found")
    private String errorMessage;

    @Schema(description = "Error Time" , example = "2022-01-01T00:00:00")
    private LocalDateTime errorTime;
}
