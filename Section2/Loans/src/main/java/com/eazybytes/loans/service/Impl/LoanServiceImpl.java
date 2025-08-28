package com.eazybytes.loans.service.Impl;

import com.eazybytes.loans.constants.LoansConstant;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoanAlreadyExistException;
import com.eazybytes.loans.exception.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistException("Loan already exists for this mobile number " + mobileNumber) ;
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber){
        Loans loans = new Loans();
        loans.setMobileNumber(mobileNumber);
        long randomLoanNumber = 1000000000000L + (long) (Math.random() * 9000000000000L);
        loans.setLoanNumber(Long.toString(randomLoanNumber));
        loans.setLoanType(LoansConstant.HOME_LOAN);
        loans.setTotalLoan(LoansConstant.NEW_LOAN_LIMIT);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(LoansConstant.NEW_LOAN_LIMIT);
        return loans;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan not found for this mobile number " + mobileNumber)
        ) ;
        return LoansMapper.mapToLoansDto(loans , new LoansDto()) ;
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loan = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan not found for this mobile number " + loansDto.getMobileNumber())
        ) ;
        LoansMapper.mapToLoans(loansDto , loan) ;
        loansRepository.save(loan) ;
        return true ;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan not found for this mobile number " + mobileNumber)
        ) ;
        loansRepository.deleteById(loan.getLoanId());
        return true ;
    }
}
