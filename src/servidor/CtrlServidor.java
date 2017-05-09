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
   
    private final FrmPrincipal frmPrincipal;
    private final ArrayList<Cliente> clientes;
    private static Servidor servidor;
    
    private int pontuacao;
    private int recebidos;
    private String padrao;

    public CtrlServidor() {
        
        clientes = new ArrayList();
        servidor = new Servidor( this );
        frmPrincipal = new FrmPrincipal( this );
        recebidos = 0;
        pontuacao = 0;
        
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
        Collections.swap(array, 5, 13);
        
        return array;
    }
    
    public void somarPontos(Pacote pacote) {
        
        recebidos++;
        
        frmPrincipal.escreverResultado("Cliente " + pacote.getCliente().getCodigo() +
                    ": " + pacote.getCliente().getPadrao() +
                " - " + pacote.getPontuacao());
        
        if(pacote.getPontuacao() > pontuacao) {
            pontuacao = pacote.getPontuacao();
            padrao = pacote.getCliente().getPadrao(); 
        }
        
        if (recebidos == clientes.size()) {
            //frmPrincipal.escreverResultado("PADRÃO: " + padrao);
            recebidos = 0;
            //padrao = "";
        }
        
    }
    
    public boolean criarPacote(ArrayList entrada, boolean fase, String padrao) {
        
        Cliente cliente;
        entrada = trocarPosicao(entrada);
        Pacote pacote =  new Pacote(entrada, fase, false);
        
        if( clientes.size() > 0 ) {
        
            if( fase ) {

                cliente = buscarCliente(padrao);
                if(cliente == null) return false;
                pacote.setCliente(cliente); 
                frmPrincipal.escreverServidor("Enviado para cliente: " + cliente.getCodigo());

            } 

            servidor.enviarMulticast(pacote);
            return true;
        }
        
        frmPrincipal.escreverServidor("ALERTA: Não há clientes conectados.");
        return false;
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
            frmPrincipal.escreverServidor("Cliente conectado: " + c.getCodigo());
        }
    }
    
    public static void main(String[] args) {
        
        CtrlServidor ctrl = new CtrlServidor();
        ctrl.janelaInicial();
        
        Thread threadServidor = new Thread(servidor);
        threadServidor.start();
        
    }
    
}
