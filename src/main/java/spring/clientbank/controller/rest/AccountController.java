package spring.clientbank.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.clientbank.dto.account.AccountMapper;
import spring.clientbank.dto.account.AccountRequest;
import spring.clientbank.dto.account.AccountResponse;
import spring.clientbank.model.Account;
import spring.clientbank.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor

public class AccountController {
    private final AccountService accountService;
    private final AccountMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Validated @RequestBody AccountRequest accountRequest) {
        Account account = mapper.accountRequestToAccount(accountRequest);
        Account createdAccount = accountService.createAccount(account);
        AccountResponse accountResponse = mapper.accountToAccountResponse(createdAccount);
        return accountResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("id") Long id) {
        return accountService.getAccountById(id)
                .map(mapper::accountToAccountResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
    }

    @GetMapping("find")
    public AccountResponse findAccountByNumber(@RequestParam String number) {
        Account account = accountService.findByNumber(number);
        AccountResponse accountResponse = mapper.accountToAccountResponse(account);
        return accountResponse;
    }

    @PutMapping("add-money")
    public AccountResponse addMoneyToAccount(@RequestParam String number, @RequestParam Double amount) {
        Account account = accountService.addMoneyToAccount(number, amount);
        AccountResponse accountResponse = mapper.accountToAccountResponse(account);
        return accountResponse;
    }

    @PutMapping("withdraw-money")
    public AccountResponse withdrawMoneyFromAccount(@RequestParam String number, @RequestParam Double amount) {
        Account account = accountService.withdrawMoneyFromAccount(number, amount);
        AccountResponse accountResponse = mapper.accountToAccountResponse(account);
        return accountResponse;
    }

    @PutMapping("transfer-money")
    public List<AccountResponse> transferMoney(@RequestParam String numberFrom, @RequestParam String numberTo,
                                               @RequestParam Double amount) {
        return accountService.transferMoney(numberFrom, numberTo, amount).stream()
                .map(mapper::accountToAccountResponse)
                .collect(Collectors.toList());
    }

}
