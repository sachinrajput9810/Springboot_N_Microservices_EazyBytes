package com.eazybytes.restapi.mapper;

import com.eazybytes.restapi.dto.AccountDto;
import com.eazybytes.restapi.entity.Accounts;

public class AccountsMapper {

    public static AccountDto mapToAccountDto(Accounts account , AccountDto accountDto){
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }

    public static Accounts mapToAccount(AccountDto accountDto , Accounts account){
        account.setAccountNumber(accountDto.getAccountNumber()); ;
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        return account;
    }

}
