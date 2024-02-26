package spring.clientbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.clientbank.model.Employer;
import spring.clientbank.service.EmployerService;

@RestController
@RequestMapping("employers")
@RequiredArgsConstructor
public class EmployerController {
    private final EmployerService employerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employer createEmployer(@RequestBody Employer employer) {
        return employerService.createEmployer(employer);
    }

    @GetMapping("{id}")
    public ResponseEntity<Employer> getEmployer(@PathVariable("id") Long id) {
        return employerService.getEmployerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Employer updateEmployer(@RequestBody Employer updatedEmployer) {
        return employerService.updateEmployer(updatedEmployer);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteEmployerById(@PathVariable("id") Long id) {
        employerService.deleteEmployerById(id);
    }

    @PostMapping("add-employer-to-customer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employer> addEmployerToCustomer(@RequestParam Long customerId, @RequestParam Long employerId){
        return employerService.addEmployerToCustomer(customerId, employerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

}
