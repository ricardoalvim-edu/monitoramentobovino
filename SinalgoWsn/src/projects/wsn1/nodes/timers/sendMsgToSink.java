package projects.wsn1.nodes.timers;

import java.util.TimerTask;
import projects.wsn1.nodes.timers.MessageToSinkTimer;
import sinalgo.nodes.Node;

public class sendMsgToSink extends TimerTask {

    private MessageToSinkTimer msgtimer;
    private Node relativo;

    public sendMsgToSink(MessageToSinkTimer msgtimer, Node relativo) {
        this.msgtimer = msgtimer;
        this.relativo = relativo;
    }
    
    @Override
    public void run() {
        msgtimer.startRelative(1, this.relativo);
    }
    
}
