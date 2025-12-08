package br.com.feedsync.FeedSync.controller;

import br.com.feedsync.FeedSync.dto.ReportResponse;
import br.com.feedsync.FeedSync.mapper.ReportMapper;
import br.com.feedsync.FeedSync.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@Tag(name = "Reports", description = "Endpoints para consulta de relatórios consolidados")
public class ReportController {

    private final ReportService service;
    private final ReportMapper mapper;

    public ReportController(ReportService service, ReportMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "Listar todos os relatórios", description = "Retorna uma lista com todos os relatórios de feedback gerados.")
    public ResponseEntity<List<ReportResponse>> findAll() {
        var reports = service.findAll();
        return ResponseEntity.ok(mapper.toResponseList(reports));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar relatório por ID", description = "Retorna os detalhes de um relatório específico pelo seu ID (ex: report_week_50_2025).")
    public ResponseEntity<ReportResponse> findById(@PathVariable String id) {
        var report = service.findById(id);
        return ResponseEntity.ok(mapper.toResponse(report));
    }

    @PostMapping("/generate")
    @Operation(summary = "Gerar novo relatório", description = "Processa todos os feedbacks existentes e cria um novo relatório consolidado.")
    public ResponseEntity<ReportResponse> generate() {
        var report = service.generateReport();
        return ResponseEntity.ok(mapper.toResponse(report));
    }
}