package Model;

import java.util.ArrayList;
import java.util.Stack;

import View.Observer;
import View.SubjectIF;
import View.SubjectImpl;

public class PStack {
	
	
	
	private int counter ;
	private int currentHeight;
	private boolean full ;
	final private int maxHeight = 385;
	private Stack<Shape> plates ;
	private SubjectIF sub ;
	
	
	public 	PStack(){
		counter=0;
		currentHeight = 0;
		full = false;
		sub = new SubjectImpl();
		plates = new Stack<Shape>();
	}
	
	public int getCounter(){
		return counter;
	}
	public void setCounter(int c){
		this.counter=c;
	}
	
	public void setFull(boolean full) {
		this.full = full;
	}

	public void setCurrentHeight(int currentHeight) {
		this.currentHeight = currentHeight;
	}

	public int getCurrentHeight(){
		return currentHeight;
	}
	public void notifyObserver(String cmd){
		sub.notifyObserver(cmd);
	}
	public void register(Observer o){
		sub.register(o);
	}
	public void unregister(Observer o){
		sub.unregister(o);
	}
	public Stack<Shape> getPlates() {
		return plates;
	}

	public void setPlates(Stack<Shape> plates) {
		this.plates = plates;
	}
	
	public boolean isFull(){
		return full;
	}

    private void checkIfFull(){
    	
    	if(currentHeight>=maxHeight)
    		full=true;
    }
    public ArrayList<Shape> addShape(Shape newShape){
    	
    	ArrayList<Shape> reShapes = new ArrayList<Shape>();
    	if(newShape.getType()=="Meteor"){
    		notifyObserver("score--");
    	    reShapes.add(newShape);
    	}
    	
    	else if(plates.isEmpty()){
  
    		counter++;
    		plates.add(newShape);
    		currentHeight+=newShape.getHeight();
    		checkIfFull();
    		
    	}
    	else
    	{
    		if(newShape.getColor()==plates.peek().getColor()){
    		
	    			counter++;
	    		
		    		if(counter==3){
		    			
		    			notifyObserver("score++");
		    			reShapes.add(newShape);
		    			currentHeight -= plates.peek().getHeight();
		    			reShapes.add(plates.pop());
		    			currentHeight -= plates.peek().getHeight();
		    			reShapes.add(plates.pop());
		    			
		    			if(!plates.isEmpty()){
		    				
		    				counter=1;
		    				if(plates.size()>1)
		    				{
		    					Shape temp = plates.pop();
		    					if(plates.peek().getColor()==temp.getColor())
		    						counter ++ ;
		    					plates.push(temp);
		    				}
		  
		    			}
		    			else
		    				counter=0;
		    		}
		    		else
		    		{
		    			plates.add(newShape);
		        		currentHeight+=newShape.getHeight();
		        		checkIfFull();
		    		}
		    }
	    	else
	    	{
	    			counter=1;
	    			plates.add(newShape);
	    			currentHeight+=newShape.getHeight();
	        		checkIfFull();
	    			
	    	}
	    		
    	}
    	
    	 return reShapes;
    }

}
