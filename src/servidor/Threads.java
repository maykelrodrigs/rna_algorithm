package servidor;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mrodrigues
 */
public class Threads extends Thread {
    
    Servidor s;

    public Threads(Servidor s) {
        this.s = s;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                //s.receberTCP();
                Thread.sleep(500);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
