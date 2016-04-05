package br.edu.ifpi.easyhealthcare.modelo;

/**
 * Created by Lucas on 02/04/2016.
 */
public class Prescricao {
    private int id;
    private int idConsulta;
    private String nome;
    private String detalhes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    @Override
    public String toString() {
        return "(ID " + id + ") " + nome;
    }
}
