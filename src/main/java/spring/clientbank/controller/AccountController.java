package spring.clientbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.clientbank.model.Account;
import spring.clientbank.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor

public class AccountController {
    private final AccountService accountService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody Account account) {
        account.setId(null);
        return accountService.createAccount(account);
    }

    @GetMapping("{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") Long id){
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteAccount(@PathVariable("id") Long id){
       return accountService.deleteAccount(id);
    }

    @GetMapping("find")
    public Account findAccountByNumber(@RequestParam String number){
        return accountService.findByNumber(number);
    }

    @PutMapping("add-money")
    public Account addMoneyToAccount(@RequestParam String number, @RequestParam Double amount){
        return accountService.addMoneyToAccount(number, amount);
    }

    @PutMapping("withdraw-money")
    public Account withdrawMoneyFromAccount(@RequestParam String number, @RequestParam Double amount){
        return accountService.withdrawMoneyFromAccount(number, amount);
    }

    @PutMapping("transfer-money")
    public List<Account> transferMoney(@RequestParam String numberFrom, @RequestParam String numberTo,
                                       @RequestParam Double amount){
        return accountService.transferMoney(numberFrom, numberTo, amount);
    }

}
