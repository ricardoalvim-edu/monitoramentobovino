                                                                                                                                                                                                package projects.wsn1.nodes.nodeImplementations;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import projects.wsn1.nodes.messages.Map;
import projects.wsn1.nodes.messages.ToSink;
import projects.wsn1.nodes.timers.MapTimer;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;

public class Sink extends Node {
    //caminho para ir para o primeiro sink
    private Node prxUpToBase;
    private MapTimer timer;    
    private int nFloo;
    private int contTime = 10;    
    private static final String dataPath = "C:\\SinalGo\\Data.txt";
    
    @Override
    public void handleMessages(Inbox inbox) {
        while (inbox.hasNext()) {
            Message message = inbox.next();
            
            if(message instanceof ToSink) {
                ToSink msg = (ToSink) message;
                
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
        this.contTime = 0;
            
        Map msg = new Map(this, 0, this.nFloo);
        MapTimer timer = new MapTimer(msg);
        
        timer.startRelative(1, this);
    }
    
    //Método escritor de arquivo
    public static void writeFile(String path, String data) {
        try {
            FileWriter escritor = new FileWriter(path, true);            
            //objeto do buffer de escrita
            BufferedWriter conn = new BufferedWriter(escritor);            
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
    
    @Override
    public void neighborhoodChange() {}

    @Override
    public void postStep() {
        if (this.contTime == 10) {            
            this.contTime = 0;
            
            Map message = new Map(this, 0, this.nFloo);
            MapTimer t = new MapTimer(message);

            this.nFloo++;
            
            t.startRelative(1, this);
        }        
        this.contTime++;
    }
    @Override
    public void checkRequirements() throws WrongConfigurationException {}

}
