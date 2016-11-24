package projects.wsn1.nodes.nodeImplementations;

import br.com.skala.bovino.rmi.Analisadora;
import java.awt.Color;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import projects.wsn1.nodes.timers.SendToSink;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import projects.wsn1.nodes.messages.Map;
import projects.wsn1.nodes.messages.ToSink;
import projects.wsn1.nodes.timers.ToSinkTimer;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.nodes.Node;
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
            
            if (message instanceof Map) {
                Map msg = (Map) message;
                Boolean send = true;
                
                if (msg.nFloo == this.nFloo) {
                    if (msg.fwHop.equals(this) || this.saltUpToBase > 0) {
                        send = false;
                    } else {
                        this.saltUpToBase    = msg.salt;
                        this.prxUpToBase = msg.fwHop;
                        msg.fwHop = this;                        
                        send = true;
                    }
                } else {
                    this.nFloo = msg.nFloo;
                    this.saltUpToBase    = msg.salt;
                    this.prxUpToBase = msg.fwHop;
                    msg.fwHop = this;
                    send = true;
                }
                
                if (send) {                    
                    broadcast(msg);

                    ToSink msgtosync = new ToSink(this);
                    ToSinkTimer msgtimer = new ToSinkTimer(this.prxUpToBase, msgtosync);
                                        
                    Timer timer = new Timer();

                    Random random = new Random();
                    long tempo = (random.nextInt(10) + 1) * 1000;

                    timer.schedule(new SendToSink(msgtimer, this), tempo);
                    
                    if (!isOnArea (msgtosync)){
                        this.setColor(Color.RED);
                    }else{
                        this.setColor(Color.GREEN);
                    }
                }
            }
        }
    }
    
    //método que conecta com a classe analisadora do server para obtenção de coordenadas do bovino
    public boolean isOnArea(ToSink m){
        Registry registro;
        Analisadora canal;
        try {
            //conectar com Localhost:1099
            registro = LocateRegistry.getRegistry("Localhost", 1099);
            canal = (Analisadora) registro.lookup("bovino");
            //ID do boi, X e Y
            return canal.isOnArea(m.getOrigin().ID, 
                    m.getOrigin().getPosition().xCoord, 
                    m.getOrigin().getPosition().yCoord);
        } catch (RemoteException ex) {
            Logger.getLogger(NodeBovino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(NodeBovino.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //método de apresentação de msg genérica
    public void showMessage(String message){
        System.out.println(message);
    }
    
    @Override
    public void preStep() {}

    @Override
    public void init() {
        this.setColor(Color.GREEN);
    }

    @Override
    public void neighborhoodChange() {}

    @Override
    public void postStep() {}

    @Override
    public void checkRequirements() throws WrongConfigurationException {}

}
