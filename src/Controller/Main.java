package Controller;

import View.GUI;

public class Main {
	
	
	public static void main (String[] args){
		GUI view = new GUI();
		Controller c = new Controller(view);
	}

}
