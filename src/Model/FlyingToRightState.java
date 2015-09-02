package Model;

import java.awt.Toolkit;
import java.io.Serializable;
import java.util.Random;

public class FlyingToRightState extends ShapeState implements Serializable{

	
	
	public FlyingToRightState(Shape s){
		super.s=s;
		setRandomLimit();
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		s.setLocation(new Point(s.getLocation().getX()+2,s.getLocation().getY()));
		
		if(s.getLocation().getX()>limit)
			s.setState(new FaillingState(s));
		
	}

}
