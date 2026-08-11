package br.com.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<br.com.todolist.model.Tarefa, Long> {
}
