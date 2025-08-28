package com.eazybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "Response",
        description = "Schema to hold response details"
)
@Data
@NoArgsConstructor
public class ResponseDto {
    @Schema(description = "Status code of the response")
    private String statusCode;

    @Schema(description = "Status message of the response")
    private String statusMsg;  // Changed from statusMessage to statusMsg

    public ResponseDto(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }
}
