package com.eazybytes.loans.controller;

import com.eazybytes.loans.constants.LoansConstant;
import com.eazybytes.loans.dto.ErrorResponseDto;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.ResponseDto;
import com.eazybytes.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@AllArgsConstructor
@RestController
@Tag(
        name = "CRUD Operations for Loans" ,
        description = "Rest API for loans in Eazy Bank having operations for create , fetch , update and delete"
)
@RequestMapping(path = "/api"  , produces = MediaType.APPLICATION_JSON_VALUE)
public class LoansController {


    private ILoansService loansService ;

    @PostMapping(path = "/create")
    @Operation(
            summary = "Create a new loan for a customer",
            description = "Create a new loan for a customer by providing mobile number"
    )
    @ApiResponses({
           @ApiResponse(
                   responseCode = "201" ,
                   description = "Http Status 201 created"
           ) ,
            @ApiResponse(
                    responseCode = "500" ,
                    description = "Http Status 500 internal server error" ,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    public ResponseEntity<ResponseDto> createLoan(@RequestParam @Pattern(regexp = "^\\d{10}$" ,
                                                    message = "Invalid Mobile Number") String mobileNumber)
    {
        loansService.createLoan(mobileNumber) ;
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstant.STATUS_201 , LoansConstant.MESSAGE_201)) ;
    }


    @Operation(
            summary = "Fetch a loan details for a customer" ,
            description = "Fetch a loan details for a customer by providing mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200" ,
                    description = "Http Status 200 ok"
            ) ,
            @ApiResponse(
                    responseCode = "500" ,
                    description = "Http Status 500 internal server error" ,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam @Pattern(regexp = "^\\d{10}$" ,
                                                               message = "Invalid Mobile No. ")String mobileNumber ){
        LoansDto loansDto = loansService.fetchLoan(mobileNumber) ;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansDto) ;
    }

    @Operation(
            summary = "Update a loan details for a customer" ,
            description = "Update a loan details for a customer by providing loan details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200" ,
                    description = "Http Status 200 ok"
            ) ,
            @ApiResponse(
                    responseCode = "417" ,
                    description = "Http Status 417 expectation failed"
            ) ,
            @ApiResponse(
                    responseCode = "500" ,
                    description = "Http Status 500 internal server error" ,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto){
        boolean isUpdated = loansService.updateLoan(loansDto) ;
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstant.STATUS_200 , LoansConstant.MESSAGE_200)) ;
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstant.STATUS_417 , LoansConstant.MESSAGE_417_UPDATE)) ;
        }
    }

    @Operation(
            summary = "Delete a loan details for a customer" ,
            description = "Delete a loan details for a customer by providing mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200" ,
                    description = "Http Status 200 ok"
            ) ,
            @ApiResponse(
                    responseCode = "417" ,
                    description = "Http Status 417 expectation failed"
            ) ,
            @ApiResponse(
                    responseCode = "500" ,
                    description = "Http Status 500 internal server error" ,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )

    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam @Pattern(regexp = "^\\d{10}$" , message = "Invalid Mobile No. ")String mobileNumber){
        boolean isDeleted = loansService.deleteLoan(mobileNumber) ;
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstant.STATUS_200 , LoansConstant.MESSAGE_200)) ;
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstant.STATUS_417 , LoansConstant.MESSAGE_417_DELETE)) ;
        }
    }

}
