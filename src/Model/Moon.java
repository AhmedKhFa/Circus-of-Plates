package Model;

import java.io.Serializable;

public class Moon extends Shape implements Serializable{
	final int constHeight = 35; // to be adjusted
	
	public Moon(){
		setType("Moon");
		setHeight(constHeight);
	}

}
