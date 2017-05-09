/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package discriminador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import padrao.Cliente;
import padrao.Pacote;

/**
 *
 * @author mrodrigues
 */
public final class CtrlCliente implements Runnable {
    
    private final int PORT = 12345;
    private final String ADDRESS = "228.5.6.7";
    private final Discriminador discriminador;
    private static Cliente cliente;
    

    public CtrlCliente() {
        
        discriminador = new Discriminador();
        cliente = new Cliente("121");
        
    }
    
    public void receberMulticast() {
        
        final int bufferSize = 1024 * 4;
        
        try {
            
            MulticastSocket socket = new MulticastSocket(PORT);
            InetAddress group = InetAddress.getByName(ADDRESS);
            socket.joinGroup(group);
            
            while(true) {
                
                System.out.println("Aguardando pacotes do servidor...");
 
                byte[] buffer = new byte[bufferSize];
                socket.receive(new DatagramPacket(buffer, bufferSize, group, PORT));
                System.out.println("Pacote recebido!");
 
                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                ObjectInputStream ois = new ObjectInputStream(bais);
                
                try {
                    
                    Pacote entrada = (Pacote)ois.readObject();
                    
                    if (entrada.isFase()) {
                        
                        if ( cliente.getCodigo().equals( entrada.getCliente().getCodigo() )) {
                            cliente.setPadrao( entrada.getCliente().getPadrao() );                  
                            discriminador.inserirDados(entrada.getEntrada());
                        }
                        
                    } else {
                        int valor = discriminador.buscarDados(entrada.getEntrada());
                        Pacote pacote = new Pacote(cliente, valor);
                        enviarTCP(pacote);
                    
                    }
                    
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("No object could be read from the received UDP datagram.");
                }
                
            }

        } catch (IOException ex) {
            Logger.getLogger(CtrlCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarTCP(Pacote pacote) {
        
        ObjectOutputStream  saida;
        Socket socket;
        
        try {
            
            socket = new Socket(InetAddress.getLocalHost().getHostName(), PORT);
            
            OutputStream oStream = socket.getOutputStream();
            ObjectOutputStream oostream = new ObjectOutputStream(oStream);
            oostream.writeObject(pacote); 
            oostream.close();
            
            socket.close();

        } catch (IOException ioe) {
            System.out.println("Couldn't get I/O for the connection");
            System.exit(1);
        }  
    }
    
    public static void main(String[] args) {
        
        CtrlCliente ctrl = new CtrlCliente();
        
        // Conectar ao servidor
        Pacote pacote = new Pacote(cliente, true);
        ctrl.enviarTCP(pacote);
        
        // Receber os pacotes do servidor
        Thread threadServidor = new Thread(ctrl);
        threadServidor.start();
        
    }

    @Override
    public void run() {
        
        receberMulticast();
        
    }
}
