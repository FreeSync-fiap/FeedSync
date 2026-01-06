package br.com.feedsync.FeedSync.repository;


import br.com.feedsync.FeedSync.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional <User> findById(String userId);

    List<User> findAll();

    void deleteById(String userId);

    List<User> findByField(String fieldName, Object value);

    User findByEmail(String value);

}
