package spring.clientbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.clientbank.dto.employer.EmployerMapper;
import spring.clientbank.dto.employer.EmployerRequest;
import spring.clientbank.dto.employer.EmployerResponse;
import spring.clientbank.model.Employer;
import spring.clientbank.service.EmployerService;

@RestController
@RequestMapping("employers")
@RequiredArgsConstructor
public class EmployerController {
    private final EmployerService employerService;
    private final EmployerMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployerResponse createEmployer(@Validated  @RequestBody EmployerRequest employerRequest) {
        Employer employer = mapper.employerRequestToEmployer(employerRequest);
        Employer createdEmployer = employerService.createEmployer(employer);
        EmployerResponse employerResponse = mapper.employerToEmployerResponse(createdEmployer);
        return employerResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployerResponse> getEmployer(@PathVariable("id") Long id) {
        return employerService.getEmployerById(id)
                .map(mapper::employerToEmployerResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EmployerResponse updateEmployer(@Validated @RequestBody EmployerRequest employerRequest) {
        Employer employer = mapper.employerRequestToEmployer(employerRequest);
        Employer updatedEmployer = employerService.updateEmployer(employer);
        EmployerResponse employerResponse = mapper.employerToEmployerResponse(updatedEmployer);
        return employerResponse;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteEmployerById(@PathVariable("id") Long id) {
        employerService.deleteEmployerById(id);
    }

    @PostMapping("add-employer-to-customer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployerResponse> addEmployerToCustomer(@RequestParam Long customerId, @RequestParam Long employerId){
        return employerService.addEmployerToCustomer(customerId, employerId)
                .map(mapper::employerToEmployerResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

}
