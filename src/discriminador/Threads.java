/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discriminador;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mrodrigues
 */
public class Threads extends Thread {
    
    servidor.Servidor s;

    public Threads(servidor.Servidor s) {
        this.s = s;
    }
    
    @Override
    public void run() {
     
        try {
            
            s.receberTCP();
            Thread.sleep(500);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(servidor.Threads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
}
