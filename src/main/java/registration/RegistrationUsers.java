package comand;

import Service.UserService;
import domain.User;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class RegistrationUsers {
    private final Map<Long, RegistrationStatus> status = new HashMap<>();
    private final Map<Long, User> users = new HashMap<>();
    private final UserService userService;

    public boolean isInRegistration(Long chatId) {
        return status.containsKey(chatId);
    }

    public RegistrationStatus getStatus(Long chatId) {
        return status.get(chatId);
    }

    public void registration(User user) {
        userService.createRegistasion(user);
    }

    //зарегестрирован или нет
    public boolean isRegistered(Long chatId) {
        User user = userService.findByChatId(chatId);
        if (user != null) {
            return true;
        }
        return false;
    }

    //НАЧАЛО РЕГИСТРАЦИИ
    public void registrationStart(Long chatId) {
        User user = new User();
        user.setChatId(chatId);
        users.put(chatId, user);
        status.put(chatId, RegistrationStatus.REGISTRATION_START);
        return;
    }

    public void registrationName(Long chatId, String text) {
        RegistrationStatus currentStatus = status.get(chatId);
        if (currentStatus == null) return;
        if (currentStatus == RegistrationStatus.REGISTRATION_START) {
            var user = users.get(chatId);
            user.setUsername(text);
            status.put(chatId, RegistrationStatus.REGISTRATION_AGE);
            return;
        }
    }

    public void registrationAge(Long chatId, String text) {
        RegistrationStatus currentStatus = status.get(chatId);
        if (currentStatus == null) return;
        if (currentStatus == RegistrationStatus.REGISTRATION_AGE) {
            var user = users.get(chatId);
            int paramUserAge = Integer.parseInt(text);
            user.setAge(paramUserAge);
            status.put(chatId, RegistrationStatus.REGISTRATION_WEIGHT);
            System.out.println("Введите вес (в кг)");
            return;
        }
    }

    public void registrationWeight(Long chatId, String text) {
        RegistrationStatus currentStatus = status.get(chatId);
        if (currentStatus == null) return;
        if (currentStatus == RegistrationStatus.REGISTRATION_WEIGHT) {
            var user = users.get(chatId);
            double paramUserWeight = Double.parseDouble(text);
            user.setWeight(paramUserWeight);
            status.put(chatId, RegistrationStatus.REGISTRATION_HEIGHT);
            System.out.println("Введите рост (в см)");
            return;
        }
    }

    public void registrationHeight(Long chatId, String text) {
        RegistrationStatus currentStatus = status.get(chatId);
        if (currentStatus == null) return;
        if (currentStatus == RegistrationStatus.REGISTRATION_HEIGHT) {
            var user = users.get(chatId);
            double paramUserHeight = Double.parseDouble(text);
            user.setHeight(paramUserHeight);
            status.put(chatId, RegistrationStatus.REGISTRATION_GOAL);
            System.out.println("Введите цель");
            return;
        }
    }

    public void registrationGoal(Long chatId, String text) {
        RegistrationStatus currentStatus = status.get(chatId);
        if (currentStatus == null) return;
        if (currentStatus == RegistrationStatus.REGISTRATION_GOAL) {
            var user = users.get(chatId);
            String goal = text;
            user.setGoal(goal);
            user.setChatId(chatId);
            user.setCreatedAt(LocalDateTime.now());
            System.out.println("Вы прошли регистрацию ");
            registration(user);
            status.remove(chatId);
            users.remove(chatId);

        }
    }

}


