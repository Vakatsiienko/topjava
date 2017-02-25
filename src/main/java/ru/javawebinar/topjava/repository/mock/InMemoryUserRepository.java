package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private Map<Integer, User> userById = new ConcurrentHashMap<>();
    private AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return userById.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        user.setId(idCounter.incrementAndGet());
        userById.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return new User(userById.get(id));
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> all = new ArrayList<>(userById.values().size());
        userById.values().forEach(user -> all.add(new User(user)));
        return all;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        Optional<User> byEmail = userById.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        return byEmail.isPresent() ? new User(byEmail.get()) : null;
    }
}
