package projects.wsn1.nodes.messages;

import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class Map extends Message {
    public Node fwHop;
    public long salt;
    public int nFloo;

    public Map(Node fwHop, long salt, int nFloo) {
        this.fwHop = fwHop;
        this.salt = salt;
        this.nFloo = nFloo;
    }
    
    @Override
    public Message clone() {
        Map msg = new Map(this.fwHop, this.salt, this.nFloo);
        msg.salt++;
        return msg;
    }
    
}
