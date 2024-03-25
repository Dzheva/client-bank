package spring.clientbank.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.clientbank.model.Account;
import spring.clientbank.model.Currency;
import spring.clientbank.model.Customer;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;

    private Account account;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("MaxTestName", "MaxTest@gmail.com", 30);
        customerService.createCustomer(customer);
        account = new Account(Currency.USD, customer);
        account.setNumber("11112222");
        account.setBalance(1000.0);
    }

    @Test
    void createAccount() {
        Account savedAccount = accountService.createAccount(account);

        assertNotNull(savedAccount);
        assertEquals(account.getNumber(), savedAccount.getNumber());
    }

    @Test
    void findByNumber() {
        accountService.createAccount(account);
        Account actual = accountService.findByNumber("11112222");
        assertNotNull(actual);
        assertEquals(account.getBalance(), actual.getBalance());
    }

    @Test
    void addMoneyToAccount() {
        accountService.createAccount(account);
        accountService.addMoneyToAccount("11112222", 500.0);

        assertTrue(accountService.findByNumber("11112222").getBalance() == 1500);

    }

    @Test
    void withdrawMoneyFromAccount() {
        accountService.createAccount(account);
        accountService.withdrawMoneyFromAccount("11112222", 500.0);

        assertTrue(accountService.findByNumber("11112222").getBalance() == 500);
    }

}