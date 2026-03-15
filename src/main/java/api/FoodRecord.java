package api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FoodRecord(
            @JsonProperty("name")
            String name,

            @JsonProperty("calories")
            Double calories,

            @JsonProperty("serving_size_g")
            Double servingSizeG,

            @JsonProperty("fat_total_g")
            Double fatTotalG,

            @JsonProperty("fat_saturated_g")
            Double fatSaturatedG,

            @JsonProperty("protein_g")
            Double proteinG,

            @JsonProperty("sodium_mg")
            Integer sodiumMg,

            @JsonProperty("potassium_mg")
            Integer potassiumMg,

            @JsonProperty("cholesterol_mg")
            Integer cholesterolMg,

            @JsonProperty("carbohydrates_total_g")
            Double carbohydratesTotalG,

            @JsonProperty("fiber_g")
            Integer fiberG,

            @JsonProperty("sugar_g")
            Double sugarG
    ) {
}
