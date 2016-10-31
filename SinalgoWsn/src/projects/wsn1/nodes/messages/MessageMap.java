package projects.wsn1.nodes.messages;

import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class MessageMap extends Message {
    public Node forwardingHop;
    public long salto;
    public int nFloo;

    public MessageMap(Node forwardingHop, long salto, int nFloo) {
        this.forwardingHop = forwardingHop;
        this.salto = salto;
        this.nFloo = nFloo;
    }
    
    @Override
    public Message clone() {
        MessageMap msg = new MessageMap(this.forwardingHop, this.salto, this.nFloo);
        msg.salto++;
        return msg;
    }
    
}
