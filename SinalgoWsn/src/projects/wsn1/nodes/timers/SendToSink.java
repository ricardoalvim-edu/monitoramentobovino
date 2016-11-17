package projects.wsn1.nodes.timers;

import java.util.TimerTask;
import sinalgo.nodes.Node;

public class SendToSink extends TimerTask {
    private Node relative;
    private ToSinkTimer msgtimer;  

    public SendToSink(ToSinkTimer msgtimer, Node relative) {
        this.msgtimer = msgtimer;
        this.relative = relative;
    }
    
    @Override
    public void run() {
        msgtimer.startRelative(1, this.relative);
    }    
}
