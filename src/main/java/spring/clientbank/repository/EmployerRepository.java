package spring.clientbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.clientbank.model.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}
