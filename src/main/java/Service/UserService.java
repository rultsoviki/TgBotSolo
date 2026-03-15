import config.TransactionSessionManager;
import domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TransactionSessionManager transactionSessionManager;


    public void saveUser(Users user) {
        transactionSessionManager.inSession(session ->
                userRepository.save(session, user));
    }

    public Users findByChatId(Long chatId) {
        return transactionSessionManager.xSession(session ->
                userRepository.findByTgId(session, chatId)
                        .orElseThrow(() -> {
                            log.atError().addKeyValue("chatId", chatId).log("User not found");
                            return new RuntimeException("User with chatId " + chatId + " not found");
                        })
        );
    }
    public void updateUser(Users user) {
        transactionSessionManager.inSession(session ->
                userRepository.update(session, user));
    }

    public void deleteUser(Users users) {
        transactionSessionManager.inSession(session ->
                userRepository.delete(session, users));
    }

}
