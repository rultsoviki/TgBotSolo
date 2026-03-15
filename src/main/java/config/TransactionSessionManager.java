package config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public class TransactionSessionManager {
    private final HibernateFactory hibernateFactory;

    public void inSession(Consumer<Session> function) {
        try (var session = HibernateFactory.getSessionFactory().openSession()) {
            function.accept(session);
        }
    }

    public <T> T xSession(Function<Session, T> func) {
        try (var session = HibernateFactory.getSessionFactory().openSession()) {
            return func.apply(session);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
