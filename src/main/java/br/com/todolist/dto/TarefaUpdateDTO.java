package br.com.todolist.dto;

import jakarta.validation.constraints.NotBlank;

public class TarefaUpdateDTO {

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    private boolean status;

    public TarefaUpdateDTO() {}

    public TarefaUpdateDTO(String titulo, String descricao, boolean status) {

        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
