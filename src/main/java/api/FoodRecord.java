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
    )
{
    public String toCompactString() {
        StringBuilder sb = new StringBuilder();
        sb.append("🔹 FOOD: ").append(name).append("\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append(String.format("⚡ Calories:        %.1f kcal\n", calories));
        sb.append(String.format("📏 Serving size:    %.1f g\n", servingSizeG));
        sb.append(String.format("🥩 Protein:         %.1f g\n", proteinG));
        sb.append(String.format("🧈 Fat (total):     %.1f g\n", fatTotalG));
        sb.append(String.format("🥑 Fat (sat):       %.1f g\n", fatSaturatedG));
        sb.append(String.format("🍚 Carbs:           %.1f g\n", carbohydratesTotalG));
        sb.append(String.format("🍬 Sugar:           %.1f g\n", sugarG));
        sb.append(String.format("🌾 Fiber:           %d g\n", fiberG));
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append(String.format("🧂 Sodium:          %d mg\n", sodiumMg));
        sb.append(String.format("🍌 Potassium:       %d mg\n", potassiumMg));
        sb.append(String.format("🥚 Cholesterol:     %d mg\n", cholesterolMg));
        return sb.toString();
    }
}
