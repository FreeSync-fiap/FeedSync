package br.com.feedsync.FeedSync.repository.impl;

import br.com.feedsync.FeedSync.entity.Report;
import br.com.feedsync.FeedSync.repository.ReportRepository;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class ReportRepositoryFirestoreImpl implements ReportRepository {

    private final Firestore db;

    public ReportRepositoryFirestoreImpl(Firestore db) {
        this.db = db;
    }

    @Override
    public Report save(Report report) {
        try {
            db.collection("reports")
                    .document(report.getReportId())
                    .set(report)
                    .get();
            return report;
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao salvar relatório no Firestore", e);
        }
    }

    @Override
    public Optional<Report> findById(String reportId) {
        try {
            var snapshot = db.collection("reports")
                    .document(reportId)
                    .get()
                    .get();

            if (!snapshot.exists()) {
                return Optional.empty();
            }

            return Optional.ofNullable(snapshot.toObject(Report.class));

        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao buscar relatório por ID", e);
        }
    }

    @Override
    public List<Report> findAll() {
        try {
            return db.collection("reports").get().get().toObjects(Report.class);
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao listar relatórios", e);
        }
    }
}