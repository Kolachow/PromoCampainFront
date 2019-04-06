package pl.mkolasinski.promocampaignfront.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.mkolasinski.promocampaignfront.config.Config;
import pl.mkolasinski.promocampaignfront.model.CustomerDto;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    private Config config;

    public LoginUserDetailsService(Config config) {
        this.config = config;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<CustomerDto> response = restTemplate.getForEntity(config.getBaseUrl() + "/customers/" + email, CustomerDto.class);

        if(response.getBody() == null) {
            throw new UsernameNotFoundException("Email " + email + " doesn't exist.");
        }
        return new LoginUser(response.getBody());
    }
}
