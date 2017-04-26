/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package padrao;

import java.io.Serializable;

/**
 *
 * @author mrodrigues
 */
public class Cliente implements Serializable {
    
    private final String codigo;
    private String padrao;
    private int pontuacao;

    public Cliente(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getPadrao() {
        return padrao;
    }
    
    public void setPadrao(String padrao) {
        this.padrao = padrao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
    
}
