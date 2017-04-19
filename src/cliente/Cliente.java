/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mrodrigues
 */
public class Cliente {
    
    private int port;
    private String hostName;
    private String groupAddress;
    

    public Cliente() {
        
        try {
            
            hostName        = InetAddress.getLocalHost().getHostName();
            groupAddress    = "228.5.6.7";
            port            = 6789;
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void receberMulticast() {
        
        try {
            MulticastSocket s = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(groupAddress);
            
            while(true) {
                
                s.joinGroup(group);
                
                byte buf[] = new byte[1024];
                DatagramPacket pack = new DatagramPacket(buf, buf.length);
                s.receive(pack);
                
                System.out.println("Recebido de: " + pack.getAddress().toString() +
		    ":" + pack.getPort());
                
                System.out.write(pack.getData(),0,pack.getLength());
                
                s.leaveGroup(group);
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarTCP(String value) {
        ObjectOutputStream  saida;
        Socket conexao;
        
        try {
            ServerSocket servidor = new ServerSocket(port);
            conexao = servidor.accept();
            saida   = new ObjectOutputStream(conexao.getOutputStream());

            saida.writeObject( value );
            saida.close();
            conexao.close();

        } catch (IOException e) {
            System.err.println("Erro: " + e.toString());
        }  
    }
    
    public void receberTCP() {
        ObjectInputStream   entrada;
        Socket conexao;
        
        try {
            ServerSocket servidor = new ServerSocket(port);
                        
            while (true) {
                conexao = servidor.accept();
                entrada = new ObjectInputStream(conexao.getInputStream());
                
                tratarMensagem( (String) entrada.readObject() );
                
                entrada.close();
                conexao.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro: " + e.toString());
        }
    }
    
    public void tratarMensagem(String value) {
        
    }
    
    public static void main(String[] args) {
        
        Cliente c = new Cliente();
        System.out.println(c.groupAddress);
        c.receberMulticast();
        
    }
    
}
