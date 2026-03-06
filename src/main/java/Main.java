import config.HibernateFactory;
import repository.UserDao;
import domain.Users;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Runtime.getRuntime().addShutdownHook(new Thread(sessionFactory::close));
        var session = sessionFactory.openSession();
        Users users = Users.builder()
                .username("test")
                .age(18)
                .weight(100)
                .height(200)
                .goal("Хочу похудеть")
                .createdAt(LocalDateTime.now())
                .build();
        UserDao u = new UserDao();
        u.save(session, users);
        System.out.println(users);
    }
}
