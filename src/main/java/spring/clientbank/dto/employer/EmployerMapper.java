package spring.clientbank.dto.employer;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import spring.clientbank.model.Employer;

@Component
public class EmployerMapper {
    private final ModelMapper modelMapper;

    public EmployerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Employer employerRequestToEmployer(EmployerRequest employerRequest){
        return modelMapper.map(employerRequest, Employer.class);
    }

    public EmployerResponse employerToEmployerResponse(Employer employer){
        return modelMapper.map(employer, EmployerResponse.class);
    }

}
