package comand;

import api.FoodRecord;
import api.Integration;

import java.util.Arrays;

public class ComandFood implements Command {
    private final Integration integration;

    public ComandFood(Integration integration) {
        this.integration = integration;
    }

    @Override
    public String execute(Long id, String... agrs) {
        FoodRecord[] foodRecords = integration.getInfoFood(agrs[0]).get();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < foodRecords.length; i++) {
            result.append(foodRecords[i].toCompactString());
            result.append("---------------------------------------------------");
        }
        return result.toString();
    }

}
