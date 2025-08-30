package com.eazybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(
        name = "Loans" ,
        description = "Schema to hold loans details"
)
public class LoansDto {

    @Schema(description = "Mobile number of the customer" , example = "1234567890")
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber ;

    @NotEmpty(message = "Loan number cannot be null or empty")
    @Schema(description = "Loan number of the customer" , example = "123456789012")
    @Column(unique = true)
    private String loanNumber ;

    @NotEmpty(message = "Loan type cannot be null or empty")
    @Schema(description = "Loan type of the customer" , example = "Personal Loan")
    private String loanType ;

    @NotNull(message = "Total loan cannot be null")
    @Positive(message = "Total loan amount must be greater than zero")
    @Schema(description = "Total loan amount of the customer" , example = "10000")
    private Integer totalLoan ;

    @NotNull(message = "Amount paid cannot be null")
    @PositiveOrZero(message = "Amount paid cannot be negative")
    @Schema(description = "Amount paid of the customer" , example = "1000")
    private Integer amountPaid ;

    @NotNull(message = "Outstanding amount cannot be null")
    @PositiveOrZero(message = "Outstanding amount cannot be negative")
    @Schema(description = "Outstanding amount of the customer" , example = "9000")
    private Integer outstandingAmount ;
}
