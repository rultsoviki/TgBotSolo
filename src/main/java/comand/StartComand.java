package comand;

import registration.RegistrationUsers;


public class StartComand implements Command{
private RegistrationUsers registrationUsers;

    public StartComand(RegistrationUsers registrationUsers) {
        this.registrationUsers = registrationUsers;
    }

    @Override
    public String execute(Long id,String...param) {
            if (registrationUsers.isRegistered(id)) {
                return "Вы уже зарегистрированы!";
            } else {
                registrationUsers.registrationStart(id);
                return "Введите имя:";
            }
        }
}
