package br.com.todolist.dto;

public class TarefaResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private boolean status;

    public TarefaResponseDTO() {}

    public TarefaResponseDTO(Long id, String titulo, String descricao, boolean status) {

        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
