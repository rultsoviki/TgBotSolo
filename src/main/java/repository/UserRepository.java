package repository;
import domain.User;
import org.hibernate.Session;
import java.util.Optional;


public class UserRepository extends BaseRepository<User, Long> {

    public UserRepository() {
        super(User.class);
    }

    public Optional<User> findByTgId(Session session, Long chatId) {
        return session.createQuery("from User u where u.chatId = :chatId", User.class)
                .setParameter("chatId", chatId)
                .uniqueResultOptional();
    }




}
