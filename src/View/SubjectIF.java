package View;

public interface SubjectIF {	
	public void register(Observer o);
	public void unregister(Observer o);
	public void notifyObserver(String cmd);
	
}