package spring.clientbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.clientbank.model.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
