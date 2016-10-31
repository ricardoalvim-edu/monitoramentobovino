package projects.wsn1.nodes.nodeImplementations;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import projects.wsn1.nodes.messages.MessageMap;
import projects.wsn1.nodes.messages.MessageToSink;
import projects.wsn1.nodes.timers.MsgMapTimer;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;
import sinalgo.tools.logging.Logging;

public class Sink extends Node {
    
    private Node prxUpToBase;
    private MsgMapTimer timer;
    
    private int nFloo;
    private int contTime = 10;
    
    private static final String dataPath = "C:\\SinalGo\\Dados.txt";
    
    @Override
    public void handleMessages(Inbox inbox) {
        while (inbox.hasNext()) {
            Message message = inbox.next();
            
            if(message instanceof MessageToSink) {
                MessageToSink msg = (MessageToSink) message;
                
                this.writeFile(this.dataPath, generateMessage(msg.getOrigin().ID, 
                        msg.getOrigin().getPosition().xCoord, msg.getOrigin().getPosition().yCoord));
            }
        }
    }
    
    public String generateMessage (Integer id, Double X, Double Y){
        return id + ":" + X + "," + Y;
    }

    @Override
    public void preStep() {}

    @Override
    public void init() {
        //Atenção! Bovino saindo dos limites!
        this.setColor(Color.YELLOW);
        this.prxUpToBase = this;
        this.nFloo = 0;
        this.flood();
    }
    
    @Override
    public void neighborhoodChange() {}

    @Override
    public void postStep() {
        this.flood();
    }
    
    private void flood() {
        if (this.contTime == 10) {
            System.out.println("==================[FLOODING] - " + this.nFloo);
            
            this.contTime = 0;
            
            MessageMap msg = new MessageMap(this, 0, this.nFloo);
            MsgMapTimer timer = new MsgMapTimer(msg);

            this.nFloo++;
            
            timer.startRelative(1, this);
        }
        
        this.contTime++;
    }
    
    @Override
    public void checkRequirements() throws WrongConfigurationException {}
    
    public static void writeFile(String path, String data) {
        try {
            FileWriter fw = new FileWriter(path, true);            
            //objeto do buffer de escrita
            BufferedWriter conn = new BufferedWriter(fw);            
            //escreve dados (string) no buffer de saída
            conn.write(data);
            //escreve nova linha
            conn.newLine();
            //encerra conexão
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
