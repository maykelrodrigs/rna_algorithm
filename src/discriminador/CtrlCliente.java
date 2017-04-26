/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package discriminador;

import java.io.FileOutputStream;
import java.io.IOException;
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
public final class CtrlCliente {
    
    private final int port = 12345;
    

    public CtrlCliente() {
        
    }
    
    public void receberMulticast() {
        
        try {
            MulticastSocket s = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName("228.5.6.7");
            
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
            Logger.getLogger(CtrlCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarTCP(Pacote pacote) throws IOException {
        
        ObjectOutputStream  saida;
        Socket socket;
        
        
        
        try {
            socket = new Socket(InetAddress.getLocalHost().getHostName(), port);
            
            OutputStream oStream = socket.getOutputStream();
            ObjectOutputStream ooStream = new ObjectOutputStream(oStream);
            ooStream.writeObject(pacote);  // send serilized payload
            ooStream.close();
            
            socket.close();

        } catch (IOException e) {
            System.err.println("Erro: " + e.toString());
        }  
    }
    
    public static void main(String[] args) {
        
        CtrlCliente ctrl = new CtrlCliente();
        Cliente cliente = new Cliente("123");
        Pacote pacote = new Pacote(cliente);
        
        try {
            ctrl.enviarTCP(pacote);
        } catch (IOException ex) {
            Logger.getLogger(CtrlCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
