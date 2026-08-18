package br.com.todolist.controller;


import br.com.todolist.dto.TarefaRequestDTO;
import br.com.todolist.dto.TarefaResponseDTO;
import br.com.todolist.dto.TarefaUpdateDTO;
import br.com.todolist.model.Tarefa;
import br.com.todolist.repository.TarefaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaRepository tarefaRepository;

    public TarefaController(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }


    // Uso do .stream() para "Transforma a lista numa esteira, onde item vai passar um de cada vez"
    // .map(t ->..) Para cada item t que passar na esteira, aplica essa transformação (Tranforma Tarefa em TarefaResponseDTO)
    // .collect(Collectors.toList()) Junta tudo que saiu da esteira numa lista nova
    // t -> new TarefaResponseDTO() Opção lambda

    @GetMapping
    public List<TarefaResponseDTO> listarTodos() {

        List<Tarefa> tarefas = tarefaRepository.findAll();

        List<TarefaResponseDTO> dtos = tarefas.stream()
                .map(t -> new TarefaResponseDTO(t.getId(), t.getTitulo(), t.getDescricao(), t. isStatus()))
                .collect(Collectors.toList());

        return dtos;


    }


    // Usando o metodo DTO para separa o que vem do cliente e o que o banco de dados gera //

    @PostMapping
    public TarefaResponseDTO criar(@Valid @RequestBody  TarefaRequestDTO dto) {

        Tarefa novaTarefa = new Tarefa(null, dto.getTitulo(), dto.getDescricao(), false);

        Tarefa tarefaSalva = this.tarefaRepository.save(novaTarefa);

        TarefaResponseDTO dtoo = new TarefaResponseDTO(tarefaSalva.getId(), tarefaSalva.getTitulo(),  tarefaSalva.getDescricao(), tarefaSalva.isStatus());

        return dtoo;
    }



    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(@PathVariable Long id) {

        Optional<Tarefa> tarefa = this.tarefaRepository.findById(id);


        if (tarefa.isPresent()) {

            Tarefa t = tarefa.get();
            TarefaResponseDTO dto = new TarefaResponseDTO(t.getId(), t.getTitulo(), t.getDescricao(), t.isStatus());
            return ResponseEntity.ok(dto);
        }
        else  {
            return ResponseEntity.notFound().build();
        }

    }

    // Usando o metodo DTO para separa o que vem do cliente e o que o banco de dados gera //

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TarefaUpdateDTO dto) {

        Optional<Tarefa> tarefaExistente = this.tarefaRepository.findById(id);

        if (tarefaExistente.isPresent()) {

            Tarefa tarefaAtualizado = tarefaExistente.get();
            tarefaAtualizado.setTitulo(dto.getTitulo());
            tarefaAtualizado.setDescricao(dto.getDescricao());
            tarefaAtualizado.setStatus(dto.isStatus());
            tarefaRepository.save(tarefaAtualizado);

            TarefaResponseDTO t = new TarefaResponseDTO(tarefaAtualizado.getId(), tarefaAtualizado.getTitulo(), tarefaAtualizado.getDescricao(), tarefaAtualizado.isStatus());

            return ResponseEntity.ok(t);



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
