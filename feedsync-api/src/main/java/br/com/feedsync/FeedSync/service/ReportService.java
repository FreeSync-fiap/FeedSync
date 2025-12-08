package br.com.feedsync.FeedSync.service;

import br.com.feedsync.FeedSync.entity.Feedback;
import br.com.feedsync.FeedSync.entity.Report;
import br.com.feedsync.FeedSync.entity.ReportCourseAverage;
import br.com.feedsync.FeedSync.entity.ReportItem;
import br.com.feedsync.FeedSync.repository.FeedbackRepository;
import br.com.feedsync.FeedSync.repository.ReportRepository;
import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository repository) {
        this.reportRepository = repository;
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report findById(String id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("report", "id", id));
    }
}