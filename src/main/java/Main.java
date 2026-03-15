import Service.UserService;
import api.FoodRecord;
import api.Integration;
import bot.CommandResolver;
import config.*;
import registration.RegistrationUsers;
import bot.TreckerBot;
import repository.UserRepository;

import java.util.Arrays;
import java.util.List;
//1)Протестировать метод public String toCompactString() (красивый вывод)

public class Main {
    public static void main(String[] args) {
        TelegramConfig telegramConfig = new TelegramConfig();

        UserRepository userRepository = new UserRepository();

        HibernateFactory factory = new HibernateFactory();
        TransactionSessionManager transactionSessionManager = new TransactionSessionManager(factory);
        UserService userService = new UserService(userRepository, transactionSessionManager);
        RegistrationUsers registrationUsers = new RegistrationUsers(userService);

        Integration integration = new Integration();
//        FoodRecord[] food = integration.getInfoFood("tomato").get();
//        System.out.println(Arrays.toString(food));

        CommandResolver commandResolver = new CommandResolver(registrationUsers, integration);
        TreckerBot treckerBot = TreckerBot.getInstance(telegramConfig, registrationUsers, commandResolver);
        treckerBot.startBot();
    }
}
