package br.com.feedsync.FeedSync.service;

import br.com.feedsync.FeedSync.entity.User;
import br.com.feedsync.FeedSync.repository.UserRepository;
import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // Importe
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService { // Implemente a interface

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método da interface UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Você precisa de um método que busque por e-mail e retorne um único usuário.
        // Vou simular isso usando o findByField, mas o ideal é ter um findByEmail.
        List<User> users = repository.findByField("email", email);

        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = users.get(0); // Pega o primeiro (assumindo que email é único)

        // Converte o seu User (entidade) para o User (do Spring Security)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getActive(), // enabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                new ArrayList<>() // Authorities (perfis/roles) - pode ser implementado depois
        );
    }

    // ... (Seus métodos create, findUserById, findAll, deleteUserById, findByField continuam aqui)
    // ...
}