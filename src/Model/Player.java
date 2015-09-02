package Model;

import java.io.Serializable;
import java.util.Stack;

import View.Observer;
import View.SubjectIF;
import View.SubjectImpl;

public class Player implements Observer{
	private int score;
	private PStack rightStack;
	private PStack leftStack;
	private Point position;
	private static int screenWidth;
	private final static double deltaXLeft = 3.0/256; //between top left of clown's pic & leftStack
	private final static double deltaXRight = 27.0/256; //between top left of clown's pic & rightStack
	private SubjectIF sub ;
	
	

	public Player(Point startPt){
		score = 0;
		setRightStack(new PStack());
		setLeftStack(new PStack());
		getRightStack().register(this);
		getLeftStack().register(this);
		position = startPt;
		sub = new SubjectImpl();
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

	public static void setScreenWidth(int w){
		screenWidth = w;
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getRightStackPosition() {
		int x = position.getX()+(int)(deltaXRight*screenWidth);
		int y = position.getY()-rightStack.getCurrentHeight();
		return new Point(x, y);
	}

	public Point getLeftStackPosition() {
		int x = position.getX()+(int)(deltaXLeft*screenWidth);
		int y = position.getY()-leftStack.getCurrentHeight();
		return new Point(x, y);
	}

	public PStack getRightStack() {
		return rightStack;
	}

	private void increaseScore(){
		score+=10;
	}
	
	private void decreaseScore(){
		
		score--;
		
	}
	private void setRightStack(PStack rightStack) {
		this.rightStack = rightStack;
	}

	public PStack getLeftStack() {
		return leftStack;
	}

	private void setLeftStack(PStack leftStack) {
		this.leftStack = leftStack;
	}

	@Override
	public void update(String cmd) {
		if(cmd.equals("score++"))
		{
			increaseScore();
			notifyObserver("score");
		}
		else if(cmd.equals("score--" ) && score >0){
			
			decreaseScore();
			notifyObserver("score");
		}
	}
	
	
	public Object getMemento(){
		
		Memento state = new Memento();
		state.score = score;
		state.x = position.getX();
		state.y = position.getY();
		state.LScounter = leftStack.getCounter();
		state.RScounter = rightStack.getCounter();
		state.LScurrentHeight = leftStack.getCurrentHeight();
		state.RScurrentHeight = rightStack.getCurrentHeight();
		state.LSfull = leftStack.isFull();
		state.RSfull = rightStack.isFull();
		state.LSplates = leftStack.getPlates();
		state.RSplates = rightStack.getPlates();
		return state ;
	}
	
	public void setMemento(Object o){
		
		Memento state = (Memento)o ;
		score=state.score  ;
		setPosition(new Point(state.x,state.y));
		leftStack.setCounter(state.LScounter);
		rightStack.setCounter(state.RScounter);
		leftStack.setCurrentHeight(state.LScurrentHeight);
		rightStack.setCurrentHeight(state.RScurrentHeight);
		leftStack.setFull(state.LSfull);
		rightStack.setFull(state.RSfull);
		leftStack.setPlates(state.LSplates);
		rightStack.setPlates(state.RSplates);
		
	}
/////////////////////////////////////////////////////////

}


class Memento implements Serializable{
	int score , x , y;
	int LScounter,RScounter ;
	int LScurrentHeight,RScurrentHeight;
	boolean LSfull,RSfull ;	
	Stack<Shape> LSplates,RSplates ;
	
}