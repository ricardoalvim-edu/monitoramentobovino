package projects.wsn1.nodes.messages;

import projects.wsn1.nodes.nodeImplementations.NodeBovino;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class MessageToSink extends Message {

    //Nó que enviou a mensagem
    private Node origin;

    public MessageToSink(Node origin) {
        this.origin = origin;
    }
    
    @Override
    public Message clone() {
        return new MessageToSink(this.origin);
    }

    public Node getOrigin() {
        return origin;
    }
}
