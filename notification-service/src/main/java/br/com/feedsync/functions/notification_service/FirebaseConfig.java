package br.com.feedsync.functions.notification_service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseConfig {

    private static Firestore firestoreInstance;

    public static Firestore getFirestore() throws IOException {
        if (firestoreInstance == null) {
            initFirebase();
        }
        return firestoreInstance;
    }

    private static void initFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {

            String emulatorHost = System.getenv("FIRESTORE_EMULATOR_HOST");

            FirebaseOptions options;

            if (emulatorHost != null && !emulatorHost.isEmpty()) {
                // modo EMULADOR
                options = FirebaseOptions.builder()
                        .setProjectId("feedsync-teste-local-3a1c2")
                        .setCredentials(GoogleCredentials.create(null))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado no modo EMULADOR!");
            } else {
                // modo PRODUÇÃO
                InputStream serviceAccount = FirebaseConfig.class
                        .getResourceAsStream("/firebase-service-key.json");

                options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://feedsync-teste-local-3a1c2.firebaseio.com")
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado no modo PRODUÇÃO!");
            }

            firestoreInstance = FirestoreClient.getFirestore();
            System.out.println("Firestore conectado com sucesso: " + firestoreInstance);
        }
    }
}
