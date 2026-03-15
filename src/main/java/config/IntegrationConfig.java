package config;

import config.UtilPropertis.AppProperties;

public class IntegrationConfig {
    private final static String BASE_URL;
    private final static String API_KEY;


    static {
        BASE_URL = AppProperties.get("BASE_URL");
        API_KEY = AppProperties.get("API_KEY");
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
