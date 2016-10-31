package projects.wsn1.nodes.nodeImplementations;

import br.com.skala.bovino.rmi.Analisadora;
import java.awt.Color;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import projects.wsn1.nodes.timers.sendMsgToSink;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import projects.wsn1.nodes.messages.MessageMap;
import projects.wsn1.nodes.messages.MessageToSink;
import projects.wsn1.nodes.timers.MessageToSinkTimer;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.nodes.Node;
import sinalgo.nodes.Position;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;

public class NodeBovino extends Node {

    private Node prxUpToBase = null;
    
    private long saltUpToBase = 0;    
    private int nFloo = 0;
    private Integer seqNum    = 0;

    @Override
    public void handleMessages(Inbox inbox) {
        while (inbox.hasNext()) {
            Message message = inbox.next();
            
            if (message instanceof MessageMap) {
                MessageMap msg = (MessageMap) message;
                Boolean send = true;
                
                if (msg.nFloo == this.nFloo) {
                    if (msg.forwardingHop.equals(this) || this.saltUpToBase > 0) {
                        send = false;
                    } else {
                        this.saltUpToBase    = msg.salto;
                        this.prxUpToBase = msg.forwardingHop;
                        msg.forwardingHop = this;
                        
                        send = true;
                    }
                } else {
                    this.nFloo = msg.nFloo;
                    
                    this.saltUpToBase    = msg.salto;
                    this.prxUpToBase = msg.forwardingHop;
                    msg.forwardingHop = this;

                    send = true;
                }
                
                if (send) {
                    System.out.println("========[PASSOU]");
                    
                    broadcast(msg);

                    MessageToSink msgtosync = new MessageToSink(this);
                    MessageToSinkTimer msgtimer = new MessageToSinkTimer(this.prxUpToBase, msgtosync);
                                        
                    Timer timer = new Timer();

                    Random random = new Random();
                    long tempo = (random.nextInt(10) + 1) * 1000;

                    timer.schedule(new sendMsgToSink(msgtimer, this), tempo);
                    
                    if (isOnArea (msgtosync)){
                        this.setColor(Color.RED);
                    }else{
                        this.setColor(Color.getHSBColor(121, 85, 72));
                    }
                }
            }
        }
    }
    
    public boolean isOnArea(MessageToSink m){
        Registry registro;
        Analisadora canal;
        try {
            registro = LocateRegistry.getRegistry("Localhost", 1099);
            canal = (Analisadora) registro.lookup("bovino");
            
            return canal.isOnArea(m);
        } catch (RemoteException ex) {
            Logger.getLogger(NodeBovino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(NodeBovino.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void showMessage(String message){
        System.out.println(message);
    }
    
    @Override
    public void preStep() {}

    @Override
    public void init() {
        this.setColor(Color.getHSBColor(121, 85, 72));
    }

    @Override
    public void neighborhoodChange() {}

    @Override
    public void postStep() {}

    @Override
    public void checkRequirements() throws WrongConfigurationException {}

}
