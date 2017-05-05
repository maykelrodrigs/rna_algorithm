/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.util.ArrayList;
import java.util.Collections;
import padrao.Cliente;
import padrao.Pacote;

/**
 *
 * @author mrodrigues
 */
public class CtrlServidor {
   
    private FrmPrincipal frmPrincipal;
    private Pacote pacote;
    private final ArrayList<Cliente> clientes;
    private static Servidor servidor;

    public CtrlServidor() {
        
        clientes = new ArrayList();
        servidor = new Servidor( this );
        frmPrincipal = new FrmPrincipal( this );
        
    }
    
    public void janelaInicial() {
        
        frmPrincipal.setVisible(true);
        
    }
    
    public ArrayList trocarPosicao( ArrayList array ) {
        
        Collections.swap(array, 0, 23);
        Collections.swap(array, 7, 3);
        Collections.swap(array, 17, 15);
        Collections.swap(array, 21, 8);
        Collections.swap(array, 11, 19);
        
        return array;
    }
    
    public boolean criarPacote(ArrayList entrada, boolean fase, String padrao) {
        
        Cliente cliente;
        entrada = trocarPosicao(entrada);
        pacote =  new Pacote(entrada, fase, false);
        
        if( fase ) {
            
            cliente = buscarCliente(padrao);
            if(cliente == null) return false;
            pacote.setCliente(cliente); 
            
        } 
        
        servidor.enviarMulticast(pacote);
        return true;
    }
    
    public Cliente buscarCliente(String padrao) {
                
        for(Cliente c : clientes) 
            if ( padrao.equals( c.getPadrao() ) )
                return c;
        
        for(Cliente c : clientes) 
            if ( c.getPadrao() == null ) {
                c.setPadrao(padrao);
                return c;
            }
        
        return null;
    }
    
    public void inserirCliente(Cliente c) {
        if ( !clientes.contains(c) ) {
            clientes.add(c);
            frmPrincipal.appendServidor("Cliente conectado: " + c.getCodigo());
        }
    }
    
    public static void main(String[] args) {
        
        CtrlServidor ctrl = new CtrlServidor();
        ctrl.janelaInicial();
        
        Thread threadServidor = new Thread(servidor);
        threadServidor.start();
        
    }
    
}
