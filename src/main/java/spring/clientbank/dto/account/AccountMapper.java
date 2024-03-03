package spring.clientbank.dto.account;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import spring.clientbank.model.Account;

@Component
public class AccountMapper {
    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Account accountRequestToAccount(AccountRequest accountRequest){
        return modelMapper.map(accountRequest, Account.class);
    }

    public AccountResponse accountToAccountResponse(Account account){
        return modelMapper.map(account, AccountResponse.class);
    }
}
