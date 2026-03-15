package api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.IntegrationConfig;
import config.ObjectMapperCfg;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

@Slf4j
public class Integration {
    private final HttpClient client;
    private final IntegrationConfig integrationConfig;
    private final ObjectMapper mapper;

    public Integration() {
        this.mapper = ObjectMapperCfg.initJackson();
        this.integrationConfig = new IntegrationConfig();
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public Optional<FoodRecord[]> getInfoFood(String foodName) {
        String encodedQuery = URLEncoder.encode(foodName, StandardCharsets.UTF_8);
        String url = IntegrationConfig.getBaseUrl() + encodedQuery;

        String jsonResponse = executeRequest(url);
        if (jsonResponse == null) {
            return Optional.empty();
        }
        return parseFoodRecordFromJson(jsonResponse, foodName);
    }


    private String executeRequest(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("X-Api-Key", integrationConfig.getApiKey())
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            int statusCode = response.statusCode();
            String responseBody = response.body();

            if (statusCode < 200 || statusCode >= 300) {
                log.error("Ошибка API. Статус: {}, Тело: {}", statusCode, responseBody);
                return null;
            }
            return responseBody;
        } catch (Exception e) {
            log.error("Ошибка при выполнении запроса к {}", url, e);
            return null;
        }
    }


    private Optional<FoodRecord[]> parseFoodRecordFromJson(String jsonResponse, String foodName) {
        try {
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode itemsNode = root.get("items");

            if (itemsNode == null || !itemsNode.isArray() || itemsNode.isEmpty()) {
                log.warn("Нет данных в ответе для: {}", foodName);
                return Optional.empty();
            }

            JsonNode firstItemNode = itemsNode.get(0);
            FoodRecord[] foodRecord = mapper.treeToValue(itemsNode, FoodRecord[].class);
            return Optional.ofNullable(foodRecord);
        } catch (Exception e) {
            log.error("Ошибка при парсинге JSON для продукта: {}", foodName, e);
            return Optional.empty();
        }
    }
}