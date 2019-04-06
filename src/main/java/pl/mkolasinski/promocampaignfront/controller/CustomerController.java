package pl.mkolasinski.promocampaignfront.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import pl.mkolasinski.promocampaignfront.model.CustomerDto;

import java.util.Objects;

@RestController
public class CustomerController {

    private BCryptPasswordEncoder passwordEncoder;

    public CustomerController(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder, "Password encoder must be defined!");
    }

    @GetMapping("/customers")
    public ModelAndView registerPage() {
        return new ModelAndView("register-form")
                .addObject("customer", new CustomerDto());
    }

    @PostMapping("/customers")
    public ModelAndView register(@ModelAttribute CustomerDto customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        RestTemplate template = new RestTemplate();
        ResponseEntity<CustomerDto> response = template.postForEntity("http://localhost:8080/employee", customer, CustomerDto.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            return new ModelAndView("register-success");
        } else {
            return new ModelAndView("register-form")
                    .addObject("customer", customer);
        }
    }

}
