package Model;

import java.io.Serializable;

public class Star extends Shape implements Serializable{
	final int constHeight = 35; // to be adjusted
	
	public Star(){		
		setType("Star");
		setHeight(constHeight);

	}

}
