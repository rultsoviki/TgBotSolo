package config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class ObjectMapperCfg {
    private static ObjectMapper mapper;
    private static MapperConfig mapperConfig;

    private ObjectMapperCfg() {
        mapperConfig = new MapperConfig();
    }

    public static ObjectMapper initJackson() {
        if (mapper != null) return mapper;


        var objectMapper = new ObjectMapper();

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                mapperConfig.getWRITE_DATES_AS_TIMESTAMPS());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                mapperConfig.getFAIL_ON_UNKNOWN_PROPERTIES());

        objectMapper.findAndRegisterModules();

        mapper = objectMapper;

        return mapper;
    }
}
