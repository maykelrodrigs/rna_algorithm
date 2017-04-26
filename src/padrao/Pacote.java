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

    public Pacote(ArrayList entrada, boolean fase) {
        this.entrada = new ArrayList();
        this.entrada.addAll(entrada);
        this.fase = fase;
        this.cliente = null;
    }

    public Pacote(Cliente cliente) {
        this.cliente = cliente;
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
    
}
