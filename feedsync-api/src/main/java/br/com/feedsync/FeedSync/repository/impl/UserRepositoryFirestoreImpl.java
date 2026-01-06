package br.com.feedsync.FeedSync.repository.impl;

import br.com.feedsync.FeedSync.entity.User;
import br.com.feedsync.FeedSync.repository.UserRepository;

import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class UserRepositoryFirestoreImpl implements UserRepository {

    private final Firestore db;

    public UserRepositoryFirestoreImpl(Firestore db) {
        this.db = db;
    }

    @Override
    public  User save(User user) {
        try {
            db.collection("users")
                    .document(user.getUserId().toString())
                    .set(user)
                    .get();
            return user;
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao salvar usuário no Firestore", e);
        }
    }

    @Override
    public Optional<User> findById(String userId) {
        try {
            var snapshot = db.collection("users")
                    .document(userId)
                    .get()
                    .get();

            if (!snapshot.exists()) {
                return Optional.empty();
            }

            return Optional.ofNullable(snapshot.toObject(User.class));

        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error to find user by ID", e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return db.collection("users").get().get().toObjects(User.class);
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error to list all users", e);
        }
    }

    @Override
    public void deleteById(String userId) {
        try {
            var docRef = db.collection("users").document(userId);
            var snapshot = docRef.get().get();

//            if (!snapshot.exists()) {
//                throw new ResourceNotFoundException("user","userId",userId);
//            }

            docRef.update("active", false).get();
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ResourceNotFoundException("user","userId",userId);
        }
    }

    @Override
    public List<User> findByField(String fieldName, Object value) {
        try {
            var querySnapshot = db.collection("users")
                    .whereEqualTo(fieldName, value)
                    .get()
                    .get();

            return querySnapshot.toObjects(User.class);

        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error to find user by field: " + fieldName, e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            var querySnapshot = db.collection("users")
                    .whereEqualTo("email", email)
                    .limit(1)
                    .get()
                    .get();

            var users = querySnapshot.toObjects(User.class);

            if (users.isEmpty()) {
                return null; // ou lança um erro, você que manda
            }

            return users.get(0);

        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error to find user by field: " + "email ", e);
        }
    }

}
