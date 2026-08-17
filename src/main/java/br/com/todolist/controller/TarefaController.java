package br.com.todolist.controller;


import br.com.todolist.dto.TarefaRequestDTO;
import br.com.todolist.dto.TarefaUpdateDTO;
import br.com.todolist.model.Tarefa;
import br.com.todolist.repository.TarefaRepository;
import jakarta.validation.Valid;
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


    // Usando o método DTO para separa o que vem do cliente e o que o banco de dados gera //
    @PostMapping
    public Tarefa criar(@Valid @RequestBody  TarefaRequestDTO dto) {

        Tarefa novaTarefa = new Tarefa(null, dto.getTitulo(), dto.getDescricao(), false);
        return this.tarefaRepository.save(novaTarefa);

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
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @Valid @RequestBody TarefaUpdateDTO dto) {
        Optional<Tarefa> tarefaExistente = this.tarefaRepository.findById(id);


        if (tarefaExistente.isPresent()) {

            Tarefa tarefaAtualizado = tarefaExistente.get();
            tarefaAtualizado.setTitulo(dto.getTitulo());
            tarefaAtualizado.setDescricao(dto.getDescricao());
            tarefaAtualizado.setStatus(dto.isStatus());

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
