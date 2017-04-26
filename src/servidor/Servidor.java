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
public class Servidor {
    
    private final int port = 12345;
    
    //Threads thread;

    public Servidor() {

        //this.thread     = new Threads(this);        
        //thread.start();
                
    }
    
    public void enviarMulticast(Pacote pacote) {
        
        try {
            
            MulticastSocket s = new MulticastSocket();
            InetAddress group = InetAddress.getByName("228.5.6.7");
            s.joinGroup(group);

            final ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
            final ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(pacote);
            final byte[] data = baos.toByteArray();
            final DatagramPacket packet = new DatagramPacket(data, data.length);

            s.send(packet);
            s.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void receberTCP() {
        
        ObjectInputStream entrada;

        try {
            
            ServerSocket server = new ServerSocket(port);
            Socket socket;
            
            while (true) {
                
                socket = server.accept();
                
                InputStream istream = socket.getInputStream();
                ObjectInputStream oistream = new ObjectInputStream(istream);
                Pacote pacote = (Pacote) oistream.readObject();
                System.out.println(pacote.getCliente().getCodigo());
                
                socket.close();

            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }
    
}
