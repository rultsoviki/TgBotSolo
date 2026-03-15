package bot;

import comand.ComandFood;
import api.Integration;
import comand.Command;
import registration.RegistrationUsers;
import comand.StartComand;

import java.util.Map;

public class CommandResolver {
    private final Map<String, Command> commands;

    public CommandResolver(RegistrationUsers registrationUsers, Integration integration) {
        commands = Map.of(
                "/start", new StartComand(registrationUsers),
                "/food", new ComandFood(integration)
        );
    }

    public Command resolve(String command) {
        String parsedMessage = command.trim().toLowerCase();
        String[] parts = parsedMessage.split(" ");
        String commandName = parts[0];

        var commandHandler = commands.get(commandName);

        if (commandHandler == null) {
            throw new CommandNotFoundException();
        }

        return commandHandler;
    }
}
