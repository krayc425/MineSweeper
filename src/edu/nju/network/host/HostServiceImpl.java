package edu.nju.network.host;

import java.util.Observable;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.network.TransformObject;

public class HostServiceImpl extends HostService {

    public boolean init(HostInHandler handler){
        boolean succeed = ServerAdapter.init(handler);
        return succeed;
    }

	public void update(Observable o, Object arg) {
            UpdateMessage msg = (UpdateMessage) arg;
            String trigger_class = o.getClass().getName();
            TransformObject obj = new TransformObject(trigger_class, msg);
            this.publishData(obj);
	}

	public void publishData(TransformObject o) {
		ServerAdapter.write(o);
	}

    public void close(){
        ServerAdapter.close();
    }

}
