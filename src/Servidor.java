
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
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
public class Servidor {
    
    private int port;
    private String hostAddress;
    private String hostName;
    private String groupAddress;
    
    private final HashMap<String, Discriminador> discriminador;
    Threads threadServidor;

    public Servidor() {
        
        this.discriminador = new HashMap<>();
        this.threadServidor = new Threads(this);
        
        try {
            
            hostAddress     = InetAddress.getLocalHost().getHostAddress();
            hostName        = InetAddress.getLocalHost().getHostName();
            groupAddress    = "228.5.6.7";
            port            = 6789;
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean inserirDiscriminador(String value) {
        
        if (!discriminador.containsKey(value)) {
            discriminador.put(value, new Discriminador(value));
            return true;
        }
        return false;
        
    }
    
    public void removerDiscriminador(String value) {
        
        discriminador.remove(value);
        
    }
    
    public void enviarMulticast() {
        
        String msg = "Teste de mensagem!";
        
        try {
            
            while(true) {
                MulticastSocket s = new MulticastSocket();
                InetAddress group = InetAddress.getByName(groupAddress);
                s.joinGroup(group);

                byte buf[] = msg.getBytes();

                DatagramPacket pack = new DatagramPacket(buf, buf.length,
                                                         InetAddress.getByName(groupAddress), port);
                s.send(pack);

                s.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("ok");
    }
    
    public static void main(String[] args) {
        
        //FrmPrincipal tela = new FrmPrincipal();
        //tela.setVisible(true);
        
        Servidor s = new Servidor();
        //s.threadServidor.start();        
        s.enviarMulticast();
        
    }
    
}
