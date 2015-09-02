package Controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import Model.PStack;
import Model.Player;
import Model.Point;
import Model.Shape;
import View.GUI;
import View.Observer;
public class Controller implements Observer{

	int cnt=0;
	ShapePool sp;
	Deque<Shape> fallingShapes;
	ArrayList<Player> players;
    GUI view;
    Timer t;
    Random rand;
    int time_s ,tempCounter;
    int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public Controller(GUI v){
		view = v;
		v.register(this);
	}
	
	public void update(String cmd) {
		
		
		if(cmd.equals("New Game")){
			if(t!=null)
				t.stop();
			
			newGame();
		}
		else if(cmd.equals("Continue")){
			if(t!=null)
				t.stop();
			
			Continue();
		}
		else if(cmd.equals("score"))
		{
			view.setScore1(players.get(0).getScore());
			view.setScore2(players.get(1).getScore());
		}
		else if(cmd.equals("save")){
			save();
		}
		
		
	}
	private void save(){
		
		Object player1 = players.get(1).getMemento();
		Object player2 = players.get(0).getMemento();
		
		FileOutputStream f_out;
		try {
			
			f_out = new FileOutputStream("player1.ser"); // or .data
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
			obj_out.writeObject(player1);
			
			f_out = new FileOutputStream("player2.ser");
			obj_out = new ObjectOutputStream (f_out);
			obj_out.writeObject(player2);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void Continue(){

		 Object m1 =null ,m2 = null;
		 boolean loaded  = true ;
		try {
			
			FileInputStream f_in = new FileInputStream("player1.ser");
			ObjectInputStream obj_in = new ObjectInputStream(f_in);
			m1 = obj_in.readObject();
			
			f_in =new FileInputStream("player2.ser");
			obj_in = new ObjectInputStream(f_in);
			m2 = obj_in.readObject();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			loaded = false;
		}
		
		if(loaded){
			
			view.start();
			newGame();
			players.get(1).setMemento(m1);
			players.get(0).setMemento(m2);
			view.setScore1(players.get(0).getScore());
			view.setScore2(players.get(1).getScore());
		}
		
	}
	
	private void newGame(){	
		time_s = 0 ;
		sp = ShapePool.getInstance();
		ShapePool.getInstance().clean();
		fallingShapes= new ArrayDeque<Shape>();
		
		players = new ArrayList<Player>();

		Player.setScreenWidth(screenWidth); // for adjusting stacks positions
		
		players.add(new Player(new Point(screenWidth*155/168, screenH*5/8)));
		players.add(new Player(new Point(0, screenH*5/8)));
		
		players.get(0).register(this);
		players.get(1).register(this);
		
	    rand = new Random();
		Shape temp;
		for (int i = 0; i < 20; i++) {
			temp = sp.acquireShape();
			temp.assignColor();
			temp.setRandomStartingState();
			
			if(temp.getState() instanceof Model.FlyingToRightState)
				temp.setLocation(new Point((-75*(20-i)),130));
			else
				temp.setLocation(new Point(screenWidth+(75*(20-i)),130));
			
			fallingShapes.addFirst(temp);

		}
		tempCounter = 0;	
		t = new Timer(15, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				
				if(time_s==90){
					t.stop();
					
					if(players.get(1).getScore()==players.get(0).getScore())
						view.announceWinner(0);
					else if(players.get(1).getScore()<players.get(0).getScore())
						view.announceWinner(1);
					else
						view.announceWinner(2);
				}	
				else{
					tempCounter ++;
					if(tempCounter==67){
						tempCounter=0;
						time_s ++;
						view.setTime(time_s);
					}
					
					Deque<Shape> te = new ArrayDeque<Shape>();
					while (!fallingShapes.isEmpty()) {
						
						Shape temp  = fallingShapes.removeFirst();
						temp.move();
						detectMatches(temp, te);
					}
					
					fallingShapes = te ;
					
					if(fallingShapes.size()<20)
					{
						Shape temp ;
						for (int j = 0; j < 5; j++) {
							
							temp = sp.acquireShape();
							temp.assignColor();
							temp.setRandomStartingState();
							
							if(fallingShapes.getLast().getLocation().getX()>screenWidth/2)
							{
								if(temp.getState() instanceof Model.FlyingToLeftState)
									temp.setLocation(new Point((fallingShapes.getLast().getLocation().getX()+160),130));
								else
									temp.setLocation(new Point(-40,130));
							}
							else
							{
								if(temp.getState() instanceof Model.FlyingToRightState)
									temp.setLocation(new Point(fallingShapes.getLast().getLocation().getX()-100,130));
								else
									temp.setLocation(new Point(screenWidth+50,130));
							}
							
							fallingShapes.addLast(temp);
						}
					}
					view.drawFallingShapes(fallingShapes);
					view.drawPlayers(players);
				
				}
			}
		});
		t.start();	
	}
	
	private void detectMatches(Shape s, Deque<Shape> te){
		int sX = s.getLocation().getX()+15; //+15
		int sY = s.getLocation().getY()+25; //+25
		
		int lSX = players.get(1).getLeftStackPosition().getX();  // left stack x
		int lSY = players.get(1).getLeftStackPosition().getY();
		
		int RSX = players.get(1).getRightStackPosition().getX();  // right stack y
		int RSY = players.get(1).getRightStackPosition().getY();
								
		int lSX2 = players.get(0).getLeftStackPosition().getX();
		int lSY2 = players.get(0).getLeftStackPosition().getY();
		
		int RSX2 = players.get(0).getRightStackPosition().getX();
		int RSY2 = players.get(0).getRightStackPosition().getY();
		
		if(Math.abs(lSX-sX)<=30 && lSY-sY<=10 && lSY-sY>=0){
			PStack SL = players.get(1).getLeftStack();
			
			if(!SL.isFull())
			{
				ArrayList<Shape> reShapes = SL.addShape(s);
				for (int i = 0; i < reShapes.size(); i++) {
					ShapePool.getInstance().releaseShape(reShapes.get(i));
				}
			}
			else
				te.addLast(s);
			
			
		}
		else if(Math.abs(RSX-sX)<=30 && RSY-sY<=10 && RSY-sY>=0){
			 PStack SL = players.get(1).getRightStack();
				
				if(!SL.isFull())
				{
					ArrayList<Shape> reShapes = SL.addShape(s);
					for (int i = 0; i < reShapes.size(); i++) {
						ShapePool.getInstance().releaseShape(reShapes.get(i));
					}
				}
				else
					te.addLast(s);
		}
		else if(Math.abs(lSX2-sX)<=30 && lSY2-sY<=10 && lSY2-sY>=0){
			
			PStack SL = players.get(0).getLeftStack();
			
			if(!SL.isFull())
			{
				ArrayList<Shape> reShapes = SL.addShape(s);
				for (int i = 0; i < reShapes.size(); i++) {
					ShapePool.getInstance().releaseShape(reShapes.get(i));
				}
			}
			else
				te.addLast(s);
		}
		else if(Math.abs(RSX2-sX)<=30 && RSY2-sY<=10 && RSY2-sY>=0){
			
			PStack SL = players.get(0).getRightStack();
			
			if(!SL.isFull())
			{
				ArrayList<Shape> reShapes = SL.addShape(s);
				for (int i = 0; i < reShapes.size(); i++) {
					ShapePool.getInstance().releaseShape(reShapes.get(i));
				}
			}
			else
				te.addLast(s);
		}
		else if(s.getLocation().getY()>screenH)
			ShapePool.getInstance().releaseShape(s);
		else
			te.addLast(s);
	}

	
}
