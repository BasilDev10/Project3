package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.AccountDTOIn;
import com.example.project3.DTO.AccountTransferAmountDTOIn;
import com.example.project3.Model.Account;
import com.example.project3.Model.MyUser;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;

    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }

    public List<Account> getMyAccounts(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        return myUser.getCustomer().getAccounts().stream().toList();
    }

    public Account getAccountById(Integer userId,Integer accountId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null ) throw  new ApiException("Error: account not found");

        if (!account.getCustomer().getId().equals(myUser.getId())) throw new ApiException("Error: account access not allowed");

        return account;
    }

    public void addAccount(Integer userId,AccountDTOIn accountDTOIn){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        Account account = new Account();
        account.setAccountNumber(accountDTOIn.getAccountNumber());
        account.setCustomer(myUser.getCustomer());
        accountRepository.save(account);
    }
    public void updateAccount(Integer userId , AccountDTOIn accountDTOIn){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        Account account = accountRepository.findAccountById(accountDTOIn.getId());
        if (account == null ) throw  new ApiException("Error: account not found");

        if (!account.getCustomer().getId().equals(myUser.getId())) throw new ApiException("Error: account access not allowed");

        account.setAccountNumber(accountDTOIn.getAccountNumber());
    }

    public void deposit(Integer userId , Integer accountId , Double amount){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null ) throw  new ApiException("Error: account not found");

        if (!account.getCustomer().getId().equals(myUser.getId())) throw new ApiException("Error: account access not allowed");

        if (!account.getActive()) throw new ApiException("Error: account is not active");

        if (amount <= 0 ) throw new ApiException("Error: amount must be positive");

        account.setBalance(account.getBalance() + amount);
    }

    public void withdraw(Integer userId , Integer accountId , Double amount){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null ) throw  new ApiException("Error: account not found");

        if (!account.getCustomer().getId().equals(myUser.getId())) throw new ApiException("Error: account access not allowed");
        if (!account.getActive()) throw new ApiException("Error: account is not active");

        if (amount <= 0 ) throw new ApiException("Error: amount must be positive");

        if (amount > account.getBalance()) throw new ApiException("Error: amount is greater then balance account");

        account.setBalance(account.getBalance() - amount);
    }

    public void transferAmount(Integer userId , AccountTransferAmountDTOIn accountTransferAmountDTOIn){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        Account account = accountRepository.findAccountById(accountTransferAmountDTOIn.getId());
        if (account == null ) throw  new ApiException("Error: account not found");

        Double amount = accountTransferAmountDTOIn.getAmount();

        if (!account.getCustomer().getId().equals(myUser.getId())) throw new ApiException("Error: account access not allowed");
        if (!account.getActive()) throw new ApiException("Error: account is not active");

        if (amount <= 0 ) throw new ApiException("Error: amount must be positive");

        if (amount > account.getBalance()) throw new ApiException("Error: amount is greater then balance account");

        Account transferToAccount = accountRepository.findAccountByAccountNumber(accountTransferAmountDTOIn.getToAccountNumber());
        if (transferToAccount == null ) throw  new ApiException("Error: transfer To Account not found");
        if (!transferToAccount.getActive()) throw new ApiException("Error: transfer To Account is not active");

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
        transferToAccount.setBalance(transferToAccount.getBalance() + amount);
        accountRepository.save(transferToAccount);
    }

    public void activeBankAccount(Integer userId , Integer accountId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null ) throw  new ApiException("Error: account not found");

        if (account.getActive()) throw new ApiException("Error: account already active");

        account.setActive(true);
        accountRepository.save(account);
    }
    public void deactivateBankAccount(Integer userId , Integer accountId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null ) throw new ApiException("Error: user not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null ) throw  new ApiException("Error: account not found");

        if (!account.getActive()) throw new ApiException("Error: account already deactivated");

        account.setActive(false);
        accountRepository.save(account);
    }

}
