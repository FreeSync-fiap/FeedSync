package br.com.feedsync.functions.notification_service.repository;

import br.com.feedsync.functions.notification_service.model.User;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final Firestore db;

    public UserRepository(Firestore db) {
        this.db = db;
    }

    public List<User> findByProfile(String profile) throws Exception {
        var snapshot = db.collection("users")
                .whereEqualTo("profile", profile)
                .get()
                .get();

        return snapshot.toObjects(User.class);

    }

}
