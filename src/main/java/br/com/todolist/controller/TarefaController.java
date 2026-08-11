package br.com.todolist.controller;


import br.com.todolist.model.Tarefa;
import br.com.todolist.repository.TarefaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaRepository tarefaRepository;

    public TarefaController(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @GetMapping
    public List<Tarefa> listarTodos() {
        return this.tarefaRepository.findAll();
    }

    @PostMapping
    public Tarefa criar(@RequestBody Tarefa tarefa) {

        return this.tarefaRepository.save(tarefa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = this.tarefaRepository.findById(id);

        if (tarefa.isPresent()) {
            return ResponseEntity.ok(tarefa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        Optional<Tarefa> tarefaExistente = this.tarefaRepository.findById(id);


        if (tarefaExistente.isPresent()) {

            Tarefa tarefaAtualizado = tarefaExistente.get();
            tarefaAtualizado.setTitulo(tarefa.getTitulo());
            tarefaAtualizado.setDescricao(tarefa.getDescricao());
            tarefaAtualizado.setStatus(tarefa.isStatus());

            tarefaRepository.save(tarefaAtualizado);
            return ResponseEntity.ok(tarefaAtualizado);

        } else {
            return ResponseEntity.notFound().build();

        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {

        if (this.tarefaRepository.existsById(id)) {

            this.tarefaRepository.deleteById(id);
        } else {

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
