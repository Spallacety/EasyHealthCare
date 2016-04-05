package br.edu.ifpi.easyhealthcare.modelo;

/**
 * Created by Lucas on 02/04/2016.
 */
public class Exame {
    private int id;
    private int idConsulta;
    private String tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    @Override
    public String toString() {
        return "(ID " + id + ") " + tipo;
    }
}
