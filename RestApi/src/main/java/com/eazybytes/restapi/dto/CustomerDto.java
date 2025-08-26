package com.eazybytes.restapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Customer" ,
        description = "Schema to hold customer and account details"
)
public class CustomerDto {

    @Schema(description = "Name of the customer" , example = "John Doe")
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5 , max = 25 , message = "Name should be between 5 and 25 characters")
    private String name;

    @Schema(description = "Email of the customer" , example = "john.doe@example.com")
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Mobile number of the customer" , example = "1234567890")
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^\\d{10}$" , message = "Mobile number should be valid")
    private String mobileNumber;

    @Schema(description = "Account details of the customer" )
    private AccountDto accountDto;

}
