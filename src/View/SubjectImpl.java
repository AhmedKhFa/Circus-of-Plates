package View;

import java.util.ArrayList;

public class SubjectImpl implements SubjectIF{

	ArrayList<Observer> observers = new ArrayList<Observer>();
	@Override
	public void register(Observer o) {
		observers.add(o);
	}

	@Override
	public void unregister(Observer o) {
		for (int i = 0; i < observers.size(); i++) {
			if(observers.get(i)==o){
				observers.remove(i);
				break;
			}
			
		}
	}

	@Override
	public void notifyObserver(String cmd) {
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).update(cmd);
		}
	}

}
