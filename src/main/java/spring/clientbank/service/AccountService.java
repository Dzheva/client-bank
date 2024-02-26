package spring.clientbank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.clientbank.model.Account;
import spring.clientbank.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account findByNumber(String number) {
        return accountRepository.findAccountByNumber(number);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Account updateAccount(Account updatedAccount) {
        return accountRepository.save(updatedAccount);
    }

    public Account updateAccountBalance(String number, Double amount, boolean isAdding) {
        Account updatedAccount = findByNumber(number);
        Double currentAmount = updatedAccount.getBalance();

        if (isAdding) {
            updatedAccount.setBalance(currentAmount + amount);
        } else if (currentAmount >= amount) {
            updatedAccount.setBalance(currentAmount - amount);
        }

        return updateAccount(updatedAccount);
    }

    public Account addMoneyToAccount(String number, Double amount) {
        return updateAccountBalance(number, amount, true);
    }

    public Account withdrawMoneyFromAccount(String number, Double amount) {
        return updateAccountBalance(number, amount, false);
    }

    public List<Account> transferMoney(String numberFrom, String numberTo, Double amount) {
        Account from = withdrawMoneyFromAccount(numberFrom, amount);
        Account to = addMoneyToAccount(numberTo, amount);
        return List.of(from, to);
    }
}
