/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discriminador;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mrodrigues
 */
public class Discriminador {
    
    private final int[][] RAM;

    public Discriminador() {
        
        RAM = new int[5][32];
        
    }
    
    public void inserirDados(ArrayList array) {
    
        inserirRam(array, 0, 0);
        inserirRam(array, 1, 5);
        inserirRam(array, 2, 10);
        inserirRam(array, 3, 15);
        inserirRam(array, 4, 20);
 
    }
    
    public void inserirRam(ArrayList array, int linha, int inicio) {
    
        RAM[linha][buscarLinha(array, inicio)] += 1;  
        
    }
    
    public int buscarLinha(ArrayList array, int inicio) {
       
        String binario = "";
        
        binario += array.get(inicio++);
        binario += array.get(inicio++);
        binario += array.get(inicio++);
        binario += array.get(inicio++);
        binario += array.get(inicio);
        
        return Integer.parseInt(binario, 2);
        
    }

    public int buscarDados(ArrayList entrada) {
    
        int soma = 0;
        
        soma +=  RAM[0][ buscarLinha(entrada, 0) ];
        soma +=  RAM[1][ buscarLinha(entrada, 5) ];
        soma +=  RAM[2][ buscarLinha(entrada, 10) ];
        soma +=  RAM[3][ buscarLinha(entrada, 15) ];
        soma +=  RAM[4][ buscarLinha(entrada, 20) ];
        
        try {
            new Thread().sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Discriminador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soma;
    
    }
    
}
