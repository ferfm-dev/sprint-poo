package br.com.sprintpoo.controller;

import br.com.sprintpoo.model.TrechoRodovia;
import br.com.sprintpoo.service.TrechoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trechos")
public class TrechoController {

    @Autowired
    private TrechoService service;

    @GetMapping
    public ResponseEntity<List<TrechoRodovia>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrechoRodovia> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TrechoRodovia> criar(@RequestBody TrechoRodovia trecho) {
        TrechoRodovia criado = service.salvar(trecho);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}