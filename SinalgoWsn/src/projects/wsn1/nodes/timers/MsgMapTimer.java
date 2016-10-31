package projects.wsn1.nodes.timers;

import projects.wsn1.nodes.messages.MessageMap;
import projects.wsn1.nodes.nodeImplementations.Sink;
import sinalgo.nodes.timers.Timer;

public class MsgMapTimer extends Timer {

    private MessageMap message;

    public MsgMapTimer(MessageMap message) {
        this.message = message;
    }
    
    @Override
    public void fire() {
        ((Sink)node).broadcast(message);
    }
    
}
