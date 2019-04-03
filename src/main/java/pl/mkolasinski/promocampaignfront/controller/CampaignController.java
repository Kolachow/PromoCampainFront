package pl.mkolasinski.promocampaignfront.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CampaignController {

    @GetMapping("/campaigns")
    public ModelAndView registerPage() {
        return new ModelAndView("campaign-form")
                .addObject("campaign", new CampaignDto());
    }

    @PostMapping("/campaigns")
    public ModelAndView register(@ModelAttribute CampaignDto campaign) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<CampaignDto> response = template.postForEntity("http://localhost:8080/campaigns", campaign, CampaignDto.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            return new ModelAndView("register-success");
        } else {
            return new ModelAndView("campaign-form")
                    .addObject("campaign", campaign);
        }
    }
}
