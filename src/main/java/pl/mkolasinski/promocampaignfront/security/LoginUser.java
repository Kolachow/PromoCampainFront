package pl.mkolasinski.promocampaignfront.security;

import org.springframework.security.core.userdetails.User;
import pl.mkolasinski.promocampaignfront.model.CustomerDto;

import java.util.Collections;

public class LoginUser extends User {

    public LoginUser(CustomerDto customerDto) {
        super(customerDto.getEmail(), customerDto.getPassword(), Collections.emptyList());
    }
}
