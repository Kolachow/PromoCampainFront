package pl.mkolasinski.promocampaignfront.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.mkolasinski.promocampaignfront.model.CustomerDto;

import java.util.List;

public class LoginUser extends User {

    public LoginUser(CustomerDto customerDto, List<GrantedAuthority> grantedAuthorityList) {
        super(customerDto.getEmail(), customerDto.getPassword(), grantedAuthorityList);
    }
}
