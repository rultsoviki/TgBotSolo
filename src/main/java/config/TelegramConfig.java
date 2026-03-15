package config;

import config.UtilPropertis.AppProperties;

public class TelegramConfig {
    private final static String BOT_TOKEN;
    private final static String BOT_NAME;

    static {
        BOT_TOKEN = AppProperties.get("telegram.bot.token");
        BOT_NAME = AppProperties.get("telegram.bot.name");
    }
    public static String getBotToken() {
        return BOT_TOKEN;
    }

    public static String getBotName() {
        return BOT_NAME;
    }

}
