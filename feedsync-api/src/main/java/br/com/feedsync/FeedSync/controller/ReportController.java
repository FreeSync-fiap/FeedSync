package br.com.feedsync.FeedSync.controller;

import br.com.feedsync.FeedSync.client.ReportClient;
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
    private final ReportClient reportClient;
    private final ReportMapper mapper;

    public ReportController(ReportService service, ReportClient reportClient, ReportMapper mapper) {
        this.service = service;
        this.reportClient = reportClient;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "Listar todos os relatórios", description = "Retorna uma lista com todos os relatórios existentes no banco.")
    public ResponseEntity<List<ReportResponse>> findAll() {
        var reports = service.findAll();
        return ResponseEntity.ok(mapper.toResponseList(reports));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar relatório por ID", description = "Busca um relatório específico já gerado.")
    public ResponseEntity<ReportResponse> findById(@PathVariable String id) {
        var report = service.findById(id);
        return ResponseEntity.ok(mapper.toResponse(report));
    }

    @PostMapping("/generate")
    @Operation(summary = "Disparar geração de relatório", description = "Aciona a função serverless externa para processar os dados.")
    public ResponseEntity<String> generate() {
        String response = reportClient.report();
        return ResponseEntity.ok("Processamento iniciado: " + response);
    }
}