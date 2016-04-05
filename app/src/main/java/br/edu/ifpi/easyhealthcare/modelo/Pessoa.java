package br.edu.ifpi.easyhealthcare.modelo;

import java.util.Date;

/**
 * Created by Lucas on 17/03/2016.
 */
public class Pessoa {

    private String nome;
    private String dataDeNascimento;
    private String sexo;
    private int hipertenso;
    private int cardiaco;
    private int diabetico;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int isCardiaco() {
        return cardiaco;
    }

    public void setCardiaco(int cardiaco) {
        this.cardiaco = cardiaco;
    }

    public int isDiabetico() {
        return diabetico;
    }

    public void setDiabetico(int diabetico) {
        this.diabetico = diabetico;
    }

    public int isHipertenso() {
        return hipertenso;
    }

    public void setHipertenso(int hipertenso) {
        this.hipertenso = hipertenso;
    }
}
