package com.eazybytes.restapi.service.Impl;

import com.eazybytes.restapi.constants.AccountsConstant;
import com.eazybytes.restapi.dto.AccountDto;
import com.eazybytes.restapi.dto.CustomerDto;
import com.eazybytes.restapi.entity.Accounts;
import com.eazybytes.restapi.entity.Customer;
import com.eazybytes.restapi.exception.CustomerAlreadyExistException;
import com.eazybytes.restapi.exception.ResourceNotFoundException;
import com.eazybytes.restapi.mapper.AccountsMapper;
import com.eazybytes.restapi.mapper.CustomerMapper;
import com.eazybytes.restapi.repository.AccountsRepository;
import com.eazybytes.restapi.repository.CustomerRepository;
import com.eazybytes.restapi.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository ;
    private CustomerRepository customerRepository ;
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto , new Customer()) ;
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistException("Customer Already Exist with registered Mobile no " + customer.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer) ;
        accountsRepository.save(createNewAccount(savedCustomer)) ;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer" , "MobileNumber" , mobileNumber)
        ) ;
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts" , "CustomerId" , customer.getCustomerId() + "")
        ) ;

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer , new CustomerDto()) ;
        customerDto.setAccountDto(AccountsMapper.mapToAccountDto(accounts , new AccountDto()));
        return customerDto;
    }


    private Accounts createNewAccount(Customer customer){
        Accounts account = new Accounts() ;
        account.setCustomerId(customer.getCustomerId());
        Long randomAccountNumber = 10000000000L + new Random().nextInt(9000000) ;
        account.setAccountNumber(randomAccountNumber);
        account.setAccountType(AccountsConstant.SAVINGS);
        account.setBranchAddress(AccountsConstant.ADDRESS);
        return account;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false ;
        AccountDto accountDto = customerDto.getAccountDto() ;
        if(accountDto != null){
            Accounts account = accountsRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Accounts" , "AccountNumber"  , accountDto.getAccountNumber() + "")
            ) ;
            AccountsMapper.mapToAccount(accountDto , account) ;
            accountsRepository.save(account) ;

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer" , "CustomerId" , customerId + "")
            ) ;
            CustomerMapper.mapToCustomer(customerDto , customer) ;
            customerRepository.save(customer) ;
            isUpdated = true ;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer" , "MobileNumber" , mobileNumber + "")
        ) ;
        Long customerId = customer.getCustomerId();
        Accounts account = accountsRepository.findByCustomerId(customerId).get() ;
        customerRepository.deleteById(customerId);
        accountsRepository.deleteByCustomerId(customerId);
        return true;
    }

}
