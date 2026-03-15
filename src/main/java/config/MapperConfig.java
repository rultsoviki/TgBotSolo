package config;


import config.UtilPropertis.AppProperties;


public class MapperConfig {
    private static Boolean WRITE_DATES_AS_TIMESTAMPS;
    private static Boolean FAIL_ON_UNKNOWN_PROPERTIES;

    static {
        WRITE_DATES_AS_TIMESTAMPS = Boolean.parseBoolean(AppProperties.get("SerializationFeature.WRITE_DATES_AS_TIMESTAMPS"));
        FAIL_ON_UNKNOWN_PROPERTIES = Boolean.parseBoolean(AppProperties.get("DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES"));
    }

    public static Boolean getFAIL_ON_UNKNOWN_PROPERTIES() {
        return FAIL_ON_UNKNOWN_PROPERTIES;
    }

    public static Boolean getWRITE_DATES_AS_TIMESTAMPS() {
        return WRITE_DATES_AS_TIMESTAMPS;
    }
}
