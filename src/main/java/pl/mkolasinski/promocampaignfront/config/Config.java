package pl.mkolasinski.promocampaignfront.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${campaign.baseurl}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}
