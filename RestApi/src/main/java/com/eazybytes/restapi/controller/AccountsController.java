package com.eazybytes.restapi.controller;

import com.eazybytes.restapi.constants.AccountsConstant;
import com.eazybytes.restapi.dto.CustomerDto;
import com.eazybytes.restapi.dto.ErrorResponseDto;
import com.eazybytes.restapi.dto.ResponseDto;
import com.eazybytes.restapi.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@RestController
@Validated
@RequestMapping(path = "/api" , produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(
        name = "CRUD Rest Api for the Accounts in EazyBank" ,
        description = "CRUD Rest Api for the Accounts having CREATE , READ , UPDATE and DELETE operations in EazyBank"
)
public class AccountsController {

    @Autowired
    private IAccountService accountService ;

    @Operation(
            summary = "Create Account Rest API" ,
            description = "Rest API to create a new Customer and his Account in EazyBank"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201" ,
                            description = "Http Status 201 created"
                    ) ,
                    @ApiResponse(
                            responseCode = "500" ,
                            description = "Http Status 500 Internal Server Error" ,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        accountService.createAccount(customerDto) ;
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstant.STATUS_201 , AccountsConstant.MESSAGE_201)) ;

    }

    @Operation(
            summary = "Fetch Account Rest API" ,
            description = "Rest APi to fetch Account details of a Customer in EazyBank"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201" ,
                            description = "Http Status 201 created"
                    ) ,
                    @ApiResponse(
                            responseCode = "500" ,
                            description = "Http Status 500 Internal Server Error" ,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> getAccountDetails(@Pattern(regexp = "^\\d{10}$" , message = "Mobile number should be valid")
                                                             @RequestParam String mobileNumber){
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }


    @Operation(
            summary = "Update Account Rest API" ,
            description = "Rest API to update an existing Customer and his Account in EazyBank"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200" ,
                            description = "Http Status 200 OK"
                    ) ,
                    @ApiResponse(
                            responseCode = "500" ,
                            description = "Http Status 500 Internal Server Error" ,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ) ,
                    @ApiResponse(
                            responseCode = "417" ,
                            description = "Http Status 417 Expectation Failed"
                    )
            }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = accountService.updateAccount(customerDto) ;
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstant.STATUS_200 , AccountsConstant.MESSAGE_200)) ;
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstant.STATUS_417 , AccountsConstant.MESSAGE_417_UPDATE)) ;
        }
    }

    @Operation(
            summary = "Delete Account Rest API" ,
            description = "Rest API to delete an existing Customer and his Account in EazyBank"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200" ,
                            description = "Http Status 200 OK"
                    ) ,
                    @ApiResponse(
                            responseCode = "500" ,
                            description = "Http Status 500 Internal Server Error"
                    ) ,
                    @ApiResponse(
                            responseCode = "417" ,
                            description = "Http Status 417 Expectation Failed"
                    )
            }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@Pattern(regexp = "^\\d{10}$" , message = "Mobile number should be valid")
                                                         @RequestParam String mobileNumber){
        boolean isDeleted = accountService.deleteAccount(mobileNumber) ;
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstant.STATUS_200 , AccountsConstant.MESSAGE_200)) ;
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstant.STATUS_417 , AccountsConstant.MESSAGE_417_DELETE)) ;
        }
    }

}
