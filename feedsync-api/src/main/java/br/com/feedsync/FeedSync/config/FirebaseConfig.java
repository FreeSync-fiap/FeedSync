package br.com.feedsync.FeedSync.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FirebaseConfig {

    @PostConstruct
    public void initFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {

            String emulatorHost = System.getenv("FIRESTORE_EMULATOR_HOST");

            FirebaseOptions options;

            if (emulatorHost != null && !emulatorHost.isEmpty()) {
                options = FirebaseOptions.builder()
                        .setProjectId("feedsync-teste-local-3a1c2")
                        .setCredentials(GoogleCredentials.create(null)) // cred fake
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado no modo EMULADOR!");
            } else {
                options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(
                                getClass().getResourceAsStream("/firebase-service-key.json")))
                        .setDatabaseUrl("https://feedsync-teste-local-3a1c2.firebaseio.com")
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado no modo PRODUÇÃO!");
            }

            Firestore db = FirestoreClient.getFirestore();
            System.out.println("Firestore conectado com sucesso: " + db);
        }
    }

    @Bean
    public Firestore firestore() {
        return FirestoreClient.getFirestore();
    }

}
