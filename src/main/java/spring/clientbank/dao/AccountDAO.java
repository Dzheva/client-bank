package spring.clientbank.dao;

import org.springframework.stereotype.Repository;
import spring.clientbank.model.Account;
import spring.clientbank.model.Customer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Repository
public class AccountDAO implements DAO<Account>{
    private final List<Account> accounts = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Account save(Account account) {
        if(account.getId() == null){
            account.setId(nextId++);
        }
        accounts.add(account);
        return account;
    }

    @Override
    public boolean delete(Account account) {
        return accounts.remove(account);
    }

    @Override
    public void deleteAll(List<Account> accountsList) {
        accounts.removeAll(accountsList);
    }

    @Override
    public void saveAll(List<Account> accountsList) {
        accountsList.forEach(account -> save(account));
    }

    @Override
    public boolean deleteById(Long id) {
        return delete(getOne(id));
    }

    @Override
    public Account getOne(Long id) {
        return accounts.
                stream().
                filter(account -> account.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Account findByNumber (String number){
        return accounts.
                stream().
                filter(account -> account.getNumber().equals(number))
                .findFirst()
                .orElse(null);
    }

    public Account updateAccount (Account updatedAccount){
        Account existingAccount = getOne(updatedAccount.getId());

        if(existingAccount != null){
            existingAccount.setBalance(updatedAccount.getBalance());
        }

        return existingAccount;
    }

}
