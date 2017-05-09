package servidor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import padrao.Pacote;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mrodrigues
 */
public class Servidor implements Runnable {
    
    private final int PORT = 12345;
    private final String ADDRESS = "228.5.6.7";
    CtrlServidor ctrlServidor;

    public Servidor(CtrlServidor ctrlServidor) {
        this.ctrlServidor = ctrlServidor;
    }
            
    public void enviarMulticast(Pacote pacote) {
        
        try {
            
            MulticastSocket socket = new MulticastSocket(PORT);
            InetAddress group = InetAddress.getByName(ADDRESS);
            socket.joinGroup(group);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(pacote);
            byte[] data = baos.toByteArray();

            socket.send(new DatagramPacket(data, data.length, group, PORT));
            socket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void receberTCP() {
        
        try {
            
            while(true) {
                System.out.println("Aguardando pacotes do cliente...");
                ServerSocket server = new ServerSocket(PORT);
                Socket socket;

                socket = server.accept();

                InputStream istream = socket.getInputStream();
                ObjectInputStream oistream = new ObjectInputStream(istream);
                Pacote pacote = (Pacote) oistream.readObject();

                if ( pacote.isConexao() )
                    ctrlServidor.inserirCliente(pacote.getCliente());
                else
                    ctrlServidor.somarPontos(pacote);
                
                socket.close();
                server.close();
            }
            
        } catch (IOException eio) {
            System.out.println("Couldn't get I/O for the connection");
            System.exit(1);
        } catch (ClassNotFoundException cne) {
            System.out.println("Wanted class Pacote, but got class " + cne);
        }
    }

    @Override
    public void run() {
    
        receberTCP();
        
    }
    
}
