package com.eazybytes.restapi.service;

import com.eazybytes.restapi.dto.CustomerDto;

public interface IAccountService {
    /*
    *  @param customerDto  - CustomerDto object
    * */
    void createAccount(CustomerDto customerDto) ;
    CustomerDto fetchAccount(String mobileNumber) ;
    boolean updateAccount(CustomerDto customerDto) ;
    boolean deleteAccount(String mobileNumber) ;
}
