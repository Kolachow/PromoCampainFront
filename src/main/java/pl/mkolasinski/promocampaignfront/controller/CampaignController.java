package pl.mkolasinski.promocampaignfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import pl.mkolasinski.promocampaignfront.config.Config;
import pl.mkolasinski.promocampaignfront.model.CampaignDto;

import java.util.List;

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

        ResponseEntity<List> result = restTemplate.getForEntity(config.getBaseUrl() + "/campaigns", List.class);
        return new ModelAndView("campaigns-list")
                .addObject("campaigns", result.getBody());
    }

    @GetMapping("/campaigns/{brand}")
    public ModelAndView brandCampaigns(@PathVariable String brand) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List> result = restTemplate.getForEntity(config.getBaseUrl() + "/campaigns/current/".concat(brand), List.class);
        return new ModelAndView("campaigns-list")
                .addObject("campaigns", result.getBody());
    }

    @GetMapping("/campaigns/ended/{brand}")
    public ModelAndView endedCampaigns(@PathVariable String brand) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List> result = restTemplate.getForEntity(config.getBaseUrl() + "/campaigns/ended/".concat(brand), List.class);
        return new ModelAndView("campaigns-list")
                .addObject("campaigns", result.getBody());
    }

    @GetMapping("/campaigns/future/{brand}")
    public ModelAndView futureCampaigns(@PathVariable String brand) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List> result = restTemplate.getForEntity(config.getBaseUrl() + "/campaigns/future/".concat(brand), List.class);
        return new ModelAndView("campaigns-list")
                .addObject("campaigns", result.getBody());
    }
}
