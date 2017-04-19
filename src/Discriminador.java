/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mrodrigues
 */
public class Discriminador {
    
    private final String nome;
    private String padrao;

    public Discriminador(String nome) {
        this.nome   = nome;
        this.padrao = "";
    }

    public String getNome() {
        return nome;
    }

    public String getPadrao() {
        return padrao;
    }
    
    public void setPadrao(String value) {
        this.padrao = value;
    }
    
}
