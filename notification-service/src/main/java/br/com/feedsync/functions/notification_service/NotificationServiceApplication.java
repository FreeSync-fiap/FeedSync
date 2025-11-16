package br.com.feedsync.functions.notification_service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class NotificationServiceApplication implements HttpFunction {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceApplication.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {

        Firestore db = FirebaseConfig.getFirestore();

        Map<String, Object> report = new HashMap<>();

        report.put("teste", "NOTIFICATION FUNCIONANDO !!!");

        db.collection("notifications").add(report).get();
        log.info("Notification salvo com sucesso no Firestore.");

        BufferedWriter writer = response.getWriter();
        writer.write("Notification salvo com sucesso !!!");

    }
}
