package spring.clientbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.clientbank.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
        Account findAccountByNumber(String number);
}
