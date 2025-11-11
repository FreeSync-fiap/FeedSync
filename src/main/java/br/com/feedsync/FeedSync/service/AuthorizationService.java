package br.com.feedsync.FeedSync.service;

import br.com.feedsync.FeedSync.entity.enums.Profile;
import br.com.feedsync.FeedSync.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var users = userRepository.findByField("email", username);

        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + username);
        }

        var user = users.get(0);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getActive(),
                true,
                true,
                true,
                getAuthorities(user.getProfile())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Profile profile) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + profile.name()));
    }
}