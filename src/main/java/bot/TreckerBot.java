package bot;

import registration.RegistrationStatus;
import registration.RegistrationUsers;
import config.TelegramConfig;
import lombok.extern.slf4j.Slf4j;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


import javax.inject.Singleton;
import java.util.concurrent.CountDownLatch;

@Slf4j

public class TreckerBot extends TelegramLongPollingBot {
    private final TelegramConfig telegramConfig;
    private RegistrationUsers registrationUsers;
    private final CommandResolver commandResolver;

    private static TreckerBot INSTANCE;

    private TreckerBot(TelegramConfig telegramConfig, RegistrationUsers registrationUsers, CommandResolver commandResolver) {
        super(TelegramConfig.getBotToken());
        this.telegramConfig = telegramConfig;
        this.registrationUsers = registrationUsers;
        this.commandResolver = commandResolver;
    }

    public static TreckerBot getInstance(TelegramConfig telegramConfig, RegistrationUsers registrationUsers, CommandResolver commandResolver) {
        if (INSTANCE == null) {
            return INSTANCE = new TreckerBot(telegramConfig, registrationUsers, commandResolver);
        } else {
            return INSTANCE;
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText() || update.getMessage().getFrom() == null) {
            return;
        }
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        //идет ли регистрация
        if (registrationUsers.isInRegistration(chatId)) {
            handleRegistrationStage(chatId, text);
            return;
        }

        if (!registrationUsers.isRegistered(chatId)) {
            sendMessage(chatId, "Вы не зарегистрированы. Введите /start для регистрации.");
        }

        try {
            //либо выкинет я зареган либо регистрация начинаетс

            var command = commandResolver.resolve(text);
            if (command != null) {
                String respons = command.execute(chatId,text);
                sendMessage(chatId, respons);
                return;
            }
        } catch (CommandNotFoundException e) {
            sendMessage(chatId, e.getMessage());
        }

    }


    private void handleRegistrationStage(Long chatId, String text) {
        RegistrationStatus currentStatus = registrationUsers.getStatus(chatId);

        if (currentStatus == null) {
            sendMessage(chatId, "Ошибка. Начните заново /start");
            return;
        }
        try {
            switch (currentStatus) {
                case REGISTRATION_START:
                    registrationUsers.registrationName(chatId, text);
                    sendMessage(chatId, "Введите возраст:");
                    break;

                case REGISTRATION_AGE:
                    registrationUsers.registrationAge(chatId, text);
                    sendMessage(chatId, "Введите вес (в кг):");
                    break;

                case REGISTRATION_WEIGHT:
                    registrationUsers.registrationWeight(chatId, text);
                    sendMessage(chatId, "Введите рост (в см):");
                    break;

                case REGISTRATION_HEIGHT:
                    registrationUsers.registrationHeight(chatId, text);
                    sendMessage(chatId, "Введите цель:");
                    break;

                case REGISTRATION_GOAL:
                    registrationUsers.registrationGoal(chatId, text);
                    sendMessage(chatId, "Успешно прошли регистрацию!");
                    break;
            }
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Ошибка! Введите число:");
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.atError().addKeyValue("chatId", chatId).log("Error sending message" + e);
        }
    }

    @Override
    public String getBotUsername() {
        return TelegramConfig.getBotName();
    }

    public void startBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(INSTANCE);
            new CountDownLatch(1).await();
        } catch (Exception e) {
            throw new RuntimeException("Failed to start telegram bot", e);
        }
    }
}
