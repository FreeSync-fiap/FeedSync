package br.com.feedsync.FeedSync.service;

import br.com.feedsync.FeedSync.entity.User;
import br.com.feedsync.FeedSync.repository.UserRepository;
import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {
        Date now = new  Date();

        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setActive(true);

        return repository.save(user);

    }

    public User findUserById(String userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user" , "userId", userId ));
    }

    public List<User> findAll(Boolean active) {
        if (active == null) {
            return repository.findAll();
        };
        return this.findByField("active", active);
    }

    public void deleteUserById(String userId) {
        repository.deleteById(userId);
    }

    public List<User> findByField( String fieldName, Object value) {
        return repository.findByField(fieldName, value);
    }

    public void enrollUserInCourse(String userId, String courseId) {
        User user = findUserById(userId);

        if (user.getEnrolledCourses() == null) {
            user.setEnrolledCourses(new ArrayList<>());
        }

        if (!user.getEnrolledCourses().contains(courseId)) {
            user.getEnrolledCourses().add(courseId);
            user.setUpdatedAt(new Date());

            repository.save(user);
        }
    }

}
