package br.com.feedsync.functions.notification_service.repository;

import br.com.feedsync.functions.notification_service.model.NotificationLog;
import com.google.cloud.firestore.Firestore;

import java.util.concurrent.ExecutionException;

public class NotificationLogRepository {

    private final Firestore db;

    public NotificationLogRepository(Firestore db) {
        this.db = db;
    }

    public NotificationLog save(NotificationLog log) {
        try {
            db.collection("notification_logs")
                    .document(log.getNotificationLogId().toString())
                    .set(log)
                    .get();
            return log;
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao salvar Log no Firestore", e);
        }
    }

}
