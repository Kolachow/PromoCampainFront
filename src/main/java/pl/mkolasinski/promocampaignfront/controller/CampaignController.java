package pl.mkolasinski.promocampaignfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import pl.mkolasinski.promocampaignfront.config.Config;
import pl.mkolasinski.promocampaignfront.model.CampaignDto;
import pl.mkolasinski.promocampaignfront.model.CustomerDto;

import java.util.List;
import java.util.Objects;

@RestController
public class CampaignController {

    @Autowired
    private Config config;

    @GetMapping("/campaigns")
    public ModelAndView registerPage() {
        return new ModelAndView("campaign-form")
                .addObject("campaign", new CampaignDto());
    }

    @PostMapping("/campaigns")
    public ModelAndView register(@ModelAttribute CampaignDto campaign) {
        RestTemplate template = new RestTemplate();

        User customer = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String customerEmail = customer.getUsername();
        ResponseEntity<CustomerDto> customerResponse = template.getForEntity(config.getBaseUrl() + "/customers/" + customerEmail, CustomerDto.class);
        campaign.setEmployeeId(Objects.requireNonNull(customerResponse.getBody()).getId());
        campaign.setBrand(customerResponse.getBody().getCompany());

        ResponseEntity<CampaignDto> response = template.postForEntity(config.getBaseUrl() + "/campaigns", campaign, CampaignDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return new ModelAndView("register-success");
        } else {
            return new ModelAndView("campaign-form")
                    .addObject("campaign", campaign);
        }
    }

    @GetMapping("/campaigns/all")
    public ModelAndView allCampaigns() {
        RestTemplate restTemplate = new RestTemplate();
        String userEmail = getUserName(restTemplate);

        ResponseEntity<List> result = restTemplate.getForEntity(config.getBaseUrl() + "/campaigns", List.class);
        return new ModelAndView("campaigns-list")
                .addObject("campaigns", result.getBody())
                .addObject("userEmail", userEmail);
    }

    @GetMapping("/campaigns/all/my")
    public ModelAndView customerCampaigns() {
        RestTemplate restTemplate = new RestTemplate();
        String brand = getBrand(restTemplate);
        String userEmail = getUserName(restTemplate);

        ResponseEntity<List> result = restTemplate.getForEntity(config.getBaseUrl()+"/campaigns/all/".concat(brand), List.class);
        return new ModelAndView("my-campaigns")
                .addObject("campaigns", result.getBody())
                .addObject("userEmail", userEmail);
    }

    @GetMapping("/campaigns/current")
    public ModelAndView brandCampaigns() {
        RestTemplate restTemplate = new RestTemplate();
        String brand = getBrand(restTemplate);
        String userEmail = getUserName(restTemplate);

        ResponseEntity<List> result = restTemplate.getForEntity(config.getBaseUrl() + "/campaigns/current/".concat(brand), List.class);
        return new ModelAndView("campaigns-list")
                .addObject("campaigns", result.getBody())
                .addObject("userEmail", userEmail);
    }

    @GetMapping("/campaigns/ended")
    public ModelAndView endedCampaigns() {
        RestTemplate restTemplate = new RestTemplate();
        String brand = getBrand(restTemplate);
        String userEmail = getUserName(restTemplate);

        ResponseEntity<List> result = restTemplate.getForEntity(config.getBaseUrl() + "/campaigns/ended/".concat(brand), List.class);
        return new ModelAndView("campaigns-list")
                .addObject("campaigns", result.getBody())
                .addObject("userEmail", userEmail);
    }

    @GetMapping("/campaigns/future")
    public ModelAndView futureCampaigns() {
        RestTemplate restTemplate = new RestTemplate();
        String brand = getBrand(restTemplate);
        String userEmail = getUserName(restTemplate);

        ResponseEntity<List> result = restTemplate.getForEntity(config.getBaseUrl() + "/campaigns/future/".concat(brand), List.class);
        return new ModelAndView("campaigns-list")
                .addObject("campaigns", result.getBody())
                .addObject("userEmail", userEmail);
    }

    private String getBrand(RestTemplate restTemplate) {
        User customer = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String customerEmail = customer.getUsername();
        ResponseEntity<CustomerDto> customerResponse = restTemplate.getForEntity(config.getBaseUrl() + "/customers/" + customerEmail, CustomerDto.class);
        return Objects.requireNonNull(customerResponse.getBody()).getCompany();
    }

    private String getUserName(RestTemplate restTemplate) {
        User customer = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String customerEmail = customer.getUsername();

        return customerEmail;
    }
}
