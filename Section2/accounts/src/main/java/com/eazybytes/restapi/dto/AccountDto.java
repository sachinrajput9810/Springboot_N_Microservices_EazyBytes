package com.eazybytes.restapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts" ,
        description = "Schema to hold account details"
)
public class AccountDto {

    @Schema(description = "Account number of the customer" , example = "1234567890")
    @NotEmpty(message = "Account number cannot be null or empty")
    @Pattern(regexp = "^\\d{10}$" , message = "Account number should be valid")
    private Long accountNumber;

    @Schema(description = "Account type of the customer" , example = "Savings")
    @NotEmpty(message = "Account type cannot be null or empty")
    private String accountType;

    @Schema(description = "Branch address of the customer" , example = "123_Main_Street")
    @NotEmpty(message = "Branch address cannot be null or empty")
    private String branchAddress;

}
