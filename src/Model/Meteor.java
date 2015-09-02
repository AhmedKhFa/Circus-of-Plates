package Model;

import java.io.Serializable;

public class Meteor extends Shape implements Serializable{
	final int constHeight = 35; // to be adjusted

	public Meteor(){	
		setType("Meteor");
		setHeight(constHeight);

	}

}
