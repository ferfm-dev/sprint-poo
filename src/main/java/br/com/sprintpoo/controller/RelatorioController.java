package br.com.sprintpoo.controller;

import br.com.sprintpoo.model.RelatorioPrioridade;
import br.com.sprintpoo.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService service;

    @PostMapping
    public ResponseEntity<RelatorioPrioridade> gerarRelatorio() {
        RelatorioPrioridade gerado = service.gerarESalvarRelatorio();
        return ResponseEntity.status(HttpStatus.CREATED).body(gerado);
    }

    @GetMapping
    public ResponseEntity<List<RelatorioPrioridade>> listarHistorico() {
        return ResponseEntity.ok(service.listarHistorico());
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<RelatorioPrioridade>> consultarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(service.buscarPorPeriodo(inicio, fim));
    }
}