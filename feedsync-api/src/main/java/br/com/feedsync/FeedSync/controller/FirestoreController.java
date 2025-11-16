package br.com.feedsync.FeedSync.controller;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/firestore")
public class FirestoreController {

    @GetMapping("/testar")
    public String testar() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Object> data = Map.of("nome", "Theu", "role", "Dev Java");
        db.collection("usuarios").document("theu-id").set(data);
        return "Documento salvo com sucesso no Firestore!";
    }
}
