package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.AccountType;
import com.example.cryptoservice.domain.dto.DepositDto;
import com.example.cryptoservice.domain.dto.TransferDto;
import com.example.cryptoservice.exception_resolver.NotEnoughMoneyException;
import com.example.cryptoservice.exception_resolver.NotEqualCurrencyException;
import com.example.cryptoservice.exception_resolver.UnsupportedOperationByAccountTypeException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransactionValidator {
    public void validateTransfer(TransferDto transfer, Account accFrom, Account accTo) {
        check(accFrom, accTo, transfer);
        if (!Objects.equals(accFrom.getCurrencyCode(), accTo.getCurrencyCode())) {
            throw new NotEqualCurrencyException("Account with id: " + accFrom.getId() + " has a different currency code compared to" + accTo.getId());
        }
    }

    public void validateDeposit(DepositDto deposit, Account depAcc) {
        if (!Objects.equals(depAcc.getAccountType(), AccountType.DEPOSIT)) {
            throw new UnsupportedOperationByAccountTypeException("Account with id: " + depAcc.getId() + " is deposit account.");
        }
        if (depAcc.getBalance().compareTo(deposit.getAmount()) < 0) {
            throw new NotEnoughMoneyException("Account with id:" + depAcc.getId() + " does not have enough balance to transfer.");
        }
    }


    public void validateWithdraw(TransferDto withdraw, Account withdrawAcc, Account depAcc) {
        if (!Objects.equals(depAcc.getAccountType(), AccountType.DEPOSIT)) {
            throw new UnsupportedOperationByAccountTypeException("Account with id: " + depAcc.getId() + " is deposit account.");
        }
        if (depAcc.getBalance().compareTo(withdraw.getAmount()) < 0) {
            throw new NotEnoughMoneyException("Wrong amount of money");
        }
        if (!Objects.equals(withdrawAcc.getCurrencyCode(), depAcc.getCurrencyCode())) {
            throw new NotEqualCurrencyException("Account with id: " + withdrawAcc.getId() + " has a different currency code compared to" + depAcc.getId());
        }
    }

    public void validateSwap(Account accFrom, Account accTo, TransferDto swap) {
        check(accFrom, accTo, swap);
    }

    private void check(Account accFrom, Account accTo, TransferDto transfer) {
        if (Objects.equals(accFrom.getAccountType(), AccountType.DEPOSIT)) {
            throw new UnsupportedOperationByAccountTypeException("Account with id: " + accFrom.getId() + " is deposit account.");
        }
        if (Objects.equals(accTo.getAccountType(), AccountType.DEPOSIT)) {
            throw new UnsupportedOperationByAccountTypeException("Account with id: " + accTo.getId() + " is deposit account.");
        }
        if (accFrom.getBalance().compareTo(transfer
                .getAmount().add(transfer.getAmount().multiply(TransactionServiceImpl.FEE_INTEREST))) < 0) {
            throw new NotEnoughMoneyException("not enough money in the account");
        }
    }
}
