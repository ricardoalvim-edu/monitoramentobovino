package projects.wsn1.nodes.timers;

import projects.wsn1.nodes.messages.ToSink;
import sinalgo.nodes.Node;
import sinalgo.nodes.timers.Timer;

public class ToSinkTimer extends Timer {
    //para o sink que receberá a mensagem 
    private Node target;
    //msg a ser enviada para o sink
    private ToSink message;

    public ToSinkTimer(Node target, ToSink message) {
        this.target = target;
        this.message = message;
    }
    
    @Override
    public void fire() {
        this.node.send(this.message, this.target);
    }    
}
