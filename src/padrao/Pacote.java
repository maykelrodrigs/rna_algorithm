/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package padrao;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author mrodrigues
 */
public class Pacote implements Serializable {
    
    private Cliente cliente;
    private ArrayList entrada;
    private boolean fase;
    private int pontuacao;
    private boolean conexao;
    
    public Pacote() {}

    public Pacote(ArrayList entrada, boolean fase, boolean conexao) {
        this.entrada = new ArrayList();
        this.entrada.addAll(entrada);
        this.fase = fase;
        this.conexao = conexao;
        this.cliente = null;
    }

    public Pacote(Cliente cliente, boolean conexao) {
        this.cliente = cliente;
        this.conexao = conexao;
    }
    
    public Pacote(Cliente cliente, int pontuacao) {
        this.cliente = cliente;
        this.pontuacao = pontuacao;
        this.conexao = false;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList getEntrada() {
        return entrada;
    }

    public boolean isFase() {
        return fase;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public boolean isConexao() {
        return conexao;
    }
    
    
}
