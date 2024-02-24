

import java.util.ArrayList;

public abstract class IPublisher {
    
    protected ArrayList<ISubscriber> subscribers;

    IPublisher() {
        this.subscribers = new ArrayList<ISubscriber>();
    }
    
    public void Add(ISubscriber s) {
        this.subscribers.add(s);
    }

    public void Remove(ISubscriber s) {
        this.subscribers.remove(s);
    }

    public void Notify(String message) {
        for( ISubscriber s : this.subscribers ) {
            s.Notify(message);
        }
    }
};