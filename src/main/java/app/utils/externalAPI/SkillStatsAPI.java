package app.utils.externalAPI;

import app.config.ApplicationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SkillStatsAPI {
    private final ObjectMapper objectMapper;

    public SkillStatsAPI(){
        objectMapper = ApplicationConfig.getInstance().getJsonMapper();
    }

    public Data skillStats() throws IOException {
        String url = "https://apiprovider.cphbusinessapps.dk/api/v1/skills/stats?slugs=java,spring-boot,postgresql";
        return objectMapper.readValue(new URL(url), Data.class);

    }
}
