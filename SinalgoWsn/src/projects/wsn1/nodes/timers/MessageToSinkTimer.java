package projects.wsn1.nodes.timers;

import projects.wsn1.nodes.messages.MessageToSink;
import sinalgo.nodes.Node;
import sinalgo.nodes.timers.Timer;

public class MessageToSinkTimer extends Timer {

    //para o sink que receberá a mensagem 
    private Node target;
    private MessageToSink message;

    public MessageToSinkTimer(Node target, MessageToSink message) {
        this.target = target;
        this.message = message;
    }
    
    @Override
    public void fire() {
        this.node.send(this.message, this.target);
    }
    
}
