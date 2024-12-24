package com.example.project3.Controller;

import com.example.project3.DTO.AccountDTOIn;
import com.example.project3.DTO.AccountTransferAmountDTOIn;
import com.example.project3.Model.MyUser;
import com.example.project3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get-all")
    public ResponseEntity getAllAccount(){
        return ResponseEntity.ok(accountService.getAllAccount());
    }

    @GetMapping("/get-my-account")
    public ResponseEntity getMyAccounts(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.ok(accountService.getMyAccounts(myUser.getId()));
    }

    @GetMapping("/get-by-id/{accountId}")
    public ResponseEntity getAccountById(@AuthenticationPrincipal MyUser myUser , @PathVariable Integer accountId){
        return ResponseEntity.ok(accountService.getAccountById(myUser.getId(), accountId));
    }
    @PostMapping("/add")
    public ResponseEntity<?> addAccount(
            @AuthenticationPrincipal MyUser myUser,
            @Valid @RequestBody AccountDTOIn accountDTOIn
    ) {
        accountService.addAccount(myUser.getId(), accountDTOIn);
        return ResponseEntity.ok("Account added successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(
            @AuthenticationPrincipal MyUser myUser,
            @Valid @RequestBody AccountDTOIn accountDTOIn
    ) {
        accountService.updateAccount(myUser.getId(), accountDTOIn);
        return ResponseEntity.ok("Account updated successfully");
    }

    @PutMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity<?> deposit(
            @AuthenticationPrincipal MyUser myUser,
            @PathVariable Integer accountId,
            @PathVariable Double amount
    ) {
        accountService.deposit(myUser.getId(), accountId, amount);
        return ResponseEntity.ok("Amount deposited successfully");
    }

    @PutMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity<?> withdraw(
            @AuthenticationPrincipal MyUser myUser,
            @PathVariable Integer accountId,
            @PathVariable Double amount
    ) {
        accountService.withdraw(myUser.getId(), accountId, amount);
        return ResponseEntity.ok("Amount withdrawn successfully");
    }

    @PutMapping("/transfer")
    public ResponseEntity<?> transferAmount(
            @AuthenticationPrincipal MyUser myUser,
            @Valid @RequestBody AccountTransferAmountDTOIn accountTransferAmountDTOIn
    ) {
        accountService.transferAmount(myUser.getId(), accountTransferAmountDTOIn);
        return ResponseEntity.ok("Amount transferred successfully");
    }

    @PutMapping("/activate/{accountId}")
    public ResponseEntity<?> activateAccount(
            @AuthenticationPrincipal MyUser myUser,
            @PathVariable Integer accountId
    ) {
        accountService.activeBankAccount(myUser.getId(), accountId);
        return ResponseEntity.ok("Account activated successfully");
    }

    @PutMapping("/deactivate/{accountId}")
    public ResponseEntity<?> deactivateAccount(
            @AuthenticationPrincipal MyUser myUser,
            @PathVariable Integer accountId
    ) {
        accountService.deactivateBankAccount(myUser.getId(), accountId);
        return ResponseEntity.ok("Account deactivated successfully");
    }

}
