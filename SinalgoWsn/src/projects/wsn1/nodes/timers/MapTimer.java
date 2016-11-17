package projects.wsn1.nodes.timers;

import sinalgo.nodes.timers.Timer;
import projects.wsn1.nodes.nodeImplementations.Sink;
import projects.wsn1.nodes.messages.Map;

public class MapTimer extends Timer {
    private Map message;

    public MapTimer(Map message) {
        this.message = message;
    }
    
    @Override
    public void fire() {
        ((Sink)node).broadcast(message);
    }
    
}
