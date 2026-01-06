package br.com.feedsync.functions.report_service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseConfig {

    private static Firestore firestoreInstance;
    private static final Object lock = new Object();

    public static Firestore getFirestore() throws IOException {
        if (firestoreInstance == null) {
            synchronized (lock) {
                if (firestoreInstance == null) {
                    initFirebase();
                }
            }
        }
        return firestoreInstance;
    }

    private static void initFirebase() throws IOException {

        FirebaseApp app;

        if (FirebaseApp.getApps().isEmpty()) {

            String emulatorHost = System.getenv("FIRESTORE_EMULATOR_HOST");

            FirebaseOptions options;

            if (emulatorHost != null && !emulatorHost.isEmpty()) {
                options = FirebaseOptions.builder()
                        .setProjectId("feedsync-teste-local-3a1c2")
                        .setCredentials(GoogleCredentials.create(null))
                        .build();

                app = FirebaseApp.initializeApp(options);
                System.out.println("Firebase EMULADOR inicializado");

            } else {
                InputStream serviceAccount =
                        FirebaseConfig.class.getResourceAsStream("/firebase-service-key.json");

                if (serviceAccount == null) {
                    throw new RuntimeException("firebase-service-key.json não encontrado no classpath");
                }

                options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setProjectId("feedsync-uat")
                        .build();

                app = FirebaseApp.initializeApp(options);
                System.out.println("Firebase PRODUÇÃO inicializado");
            }

        } else {
            app = FirebaseApp.getInstance();
        }

        firestoreInstance = FirestoreClient.getFirestore(app);
        System.out.println("Firestore conectado: " + firestoreInstance);
    }
}

