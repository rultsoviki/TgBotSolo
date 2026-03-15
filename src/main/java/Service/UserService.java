package Service;

import config.TransactionSessionManager;
import domain.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import repository.UserRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TransactionSessionManager transactionSessionManager;

    public void saveUser(User user) {
        transactionSessionManager.inSession(session ->
                userRepository.save(session, user));
    }

    public User findByChatId(Long chatId) {
        return transactionSessionManager.xSession(session ->
                userRepository.findByTgId(session, chatId)
                        .orElseThrow(() -> {
                            log.atError().addKeyValue("chatId", chatId).log("User not found");
                            return new RuntimeException("User with chatId " + chatId + " not found");
                        })
        );
    }

    public User findById(Long id) {
        return transactionSessionManager.xSession(session ->
                userRepository.findById(session, id)
                        .orElseThrow(() -> {
                            log.atError().addKeyValue("id", id).log("User not found");
                            return new RuntimeException("User with id " + id + " not found");
                        })
        );
    }

    public void updateUser(User user) {
        transactionSessionManager.inSession(session ->
                userRepository.update(session, user));
    }

    public void deleteUser(User users) {
        transactionSessionManager.inSession(session ->
                userRepository.delete(session, users));
    }

    public List<User> findAll() {
        return transactionSessionManager.xSession(session ->
                userRepository.findAll(session));
    }

    public void createRegistasion(User user) {
        var users = transactionSessionManager.xSession(session -> {
            return userRepository.findById(session, user.getChatId());
        });
        if (users.isEmpty()) {
            transactionSessionManager.inSession(session -> userRepository.save(session, user));
        }
    }
}
