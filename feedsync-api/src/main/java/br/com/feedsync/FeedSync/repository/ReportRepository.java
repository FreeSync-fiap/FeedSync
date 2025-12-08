package br.com.feedsync.FeedSync.repository;

import br.com.feedsync.FeedSync.entity.Report;
import java.util.Optional;
import java.util.List;

public interface ReportRepository {
    Report save(Report report);
    Optional<Report> findById(String reportId);
    List<Report> findAll();
}