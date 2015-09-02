package Model;

import java.io.Serializable;
import java.util.Random;

public class Shape implements Serializable{
	
	private String type;
	private int color ; // 0 red , 1 green , 2 Golden
	private Point location ;
	private int height ;
	private ShapeState state;
	
	
	
	public void setType(String type) {
		this.type = type;
	}
	public String getType(){
		return type;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public Point getLocation() {
		return location;
	}
	public void setLocation(Model.Point p) {
		this.location = p;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void assignColor(){
		Random rn = new Random();
		int color = rn.nextInt(3);
		this.setColor(color);
	}
	public ShapeState getState() {
		return state;
	}
	public void setState(ShapeState state) {
		this.state = state;
	}
	public void setRandomStartingState(){
		
		Random rand = new Random();
		
		int ch = rand.nextInt(2);
		
		switch(ch)
		{
			case 0 :
				state = new FlyingToRightState(this);
				break ;
			case 1 :	
				state = new FlyingToLeftState(this);
				break ;
		}
		
	}
	public void move(){
		
		state.move();
	}
}
