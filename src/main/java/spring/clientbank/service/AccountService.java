package spring.clientbank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.clientbank.dao.AccountDAO;
import spring.clientbank.model.Account;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountDAO accountDAO;

    public Account createAccount (Account account){
        return accountDAO.save(account);
    }

    public Optional<Account> getAccountById(Long id){
        return Optional.ofNullable(accountDAO.getOne(id));
    }

    public boolean deleteAccount(Long id){
        return accountDAO.deleteById(id);
    }

    public Account findByNumber(String number){
        return accountDAO.findByNumber(number);
    }

    public Account updateAccount(Account updatedAccount){
        return accountDAO.updateAccount(updatedAccount);
    }

    public Account updateAccountBalance (String number, Double amount, boolean isAdding){
        Account updatedAccount = findByNumber(number);
        Double currentAmount = updatedAccount.getBalance();

        if(isAdding){
            updatedAccount.setBalance(currentAmount + amount);
        } else if(currentAmount >= amount) {
            updatedAccount.setBalance(currentAmount - amount);
        }

        return updateAccount(updatedAccount);
    }

    public Account addMoneyToAccount(String number, Double amount){
        return updateAccountBalance(number, amount, true);
    }

    public Account withdrawMoneyFromAccount(String number, Double amount){
        return updateAccountBalance(number, amount, false);
    }

    public List<Account> transferMoney(String numberFrom, String numberTo, Double amount){
        Account from = withdrawMoneyFromAccount(numberFrom, amount);
        Account to = addMoneyToAccount(numberTo, amount);
        return List.of(from, to);
    }

}
