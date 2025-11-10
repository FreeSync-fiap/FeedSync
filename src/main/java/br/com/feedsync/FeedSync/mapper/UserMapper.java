package br.com.feedsync.FeedSync.mapper;

import br.com.feedsync.FeedSync.dto.UserRequest;
import br.com.feedsync.FeedSync.dto.UserResponse;
import br.com.feedsync.FeedSync.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    public User toEntity(UserRequest user) {

        User entity = new User();

        entity.setName(user.name());
        entity.setEmail(user.email());
        entity.setPassword(user.password());
        entity.setProfile(user.profile());
        entity.setEnrolledCourses(null);

        return entity;

    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getProfile(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getActive()
        );
    }

    public List<UserResponse> toResponseList(List<User> users) {
        return  users.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
