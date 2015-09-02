package Model;

import java.io.Serializable;

public class FaillingState extends ShapeState implements Serializable {

	
	
	public FaillingState(Shape s){
		super.s = s;
	}
	
	@Override
	public void move() {
		
		s.setLocation(new Point(s.getLocation().getX(),s.getLocation().getY()+3));
	}

}
