package br.edu.ifpi.easyhealthcare.modelo;

/**
 * Created by Lucas on 31/03/2016.
 */
public class Consulta {
    private int id;
    private String data;
    private String especialidade;
    private String local;
    private String horário;
    private int consultaRealizada;

    public int isConsultaRealizada() {
        return consultaRealizada;
    }

    public void setConsultaRealizada(int consultaRealizada) {
        this.consultaRealizada = consultaRealizada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getHorário() {
        return horário;
    }

    public void setHorário(String horário) {
        this.horário = horário;
    }

    @Override
    public String toString() {
        return "(ID " + id + ") " + data + " - " + especialidade + " (" + local + ")";
    }
}
