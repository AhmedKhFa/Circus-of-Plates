package Model;


import java.io.Serializable;


public class FlyingToLeftState extends ShapeState implements Serializable{

	
	public FlyingToLeftState(Shape s){
		super.s=s;
		setRandomLimit();
		
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		s.setLocation(new Point(s.getLocation().getX()-2,s.getLocation().getY()));
		
		if(s.getLocation().getX()<limit )
			s.setState(new FaillingState(s));
		
	}

}
