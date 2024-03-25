package spring.clientbank.repository;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import spring.clientbank.model.Account;
import spring.clientbank.model.Currency;
import spring.clientbank.model.Customer;

import javax.transaction.Transactional;
import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Account account;
    private Customer customer;
    @BeforeEach
    void setUp() {
        customer = new Customer("MaxTestName", "maxtest@gmail.com", 30);
        customerRepository.save(customer);
        account = new Account(Currency.USD, customer);
        account.setNumber("11112222");
        account.setBalance(1000.0);
    }

    @Test
    void save() {
        Account savedAccount = accountRepository.save(account);
        assertTrue(savedAccount.getCurrency().equals(Currency.USD));
        assertTrue(savedAccount.getNumber().equals("11112222"));
    }

    @Test
    void findByNumber() {
        accountRepository.save(account);
        Account actual = accountRepository.findAccountByNumber("11112222");

        assertNotNull(actual);
        assertEquals(account.getBalance(), actual.getBalance());
    }

    @Test
    void delete() {
        accountRepository.delete(account);
        Account actual = accountRepository.findAccountByNumber("11112222");

        assertNull(actual);
    }

}