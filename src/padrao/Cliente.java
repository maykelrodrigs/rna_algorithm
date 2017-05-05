/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package padrao;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author mrodrigues
 */
public class Cliente implements Serializable {
    
    private final String codigo;
    private String padrao;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.codigo);
        return hash;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Cliente) {
            Cliente toCompare = (Cliente) o;
            return this.codigo.equals(toCompare.codigo);
        }
        return false;
    }
    
}
