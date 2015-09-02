package Model;

import java.awt.Toolkit;
import java.util.Random;

public abstract class ShapeState {

	protected Shape s;
	protected int limit ;
	
	public abstract void move();
	
	protected void setRandomLimit(){
		
		Random rand = new Random();
		
		limit = Toolkit.getDefaultToolkit().getScreenSize().width*rand.nextInt(10)/10;
	}
}
