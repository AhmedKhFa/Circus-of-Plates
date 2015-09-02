package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Deque;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Model.Player;
import Model.Point;
import Model.Shape;

public class GUI extends JFrame implements KeyListener{

	Observer controller;
	SubjectIF sub = new SubjectImpl();
	JButton newGame, contin, exit, menu, save, exit2 ;
	Drawer sd;
	JLabel bgLabel, timer, scoreL , scoreR , scoreRNum , scoreLNum ,  winner;
	JPanel bar, timerBoard , scoreLBoarder , scoreRBoarder , SRNBoarder , SLNBoarder;
	JFrame f = this;
	Timer t;
	final int Velocity=10;
	int velX1=0, velX2=0;
	
	int cnt ;
	
	public GUI() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Galaxy");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);

		newGame = makeMenuButton("./src/Pictures/newGame.PNG", 450, 100, 470,
				210);
		newGame.setFocusable(false);
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				start();
				notifyObserver("New Game");
			}

		});
		newGame.setBackground(Color.lightGray);
		contin = makeMenuButton("./src/Pictures/Continue.PNG", 450, 100, 470,
				210 + 85 + 30);
		contin.setFocusable(false);
		contin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				notifyObserver("Continue");
			}
		});
		contin.setBackground(Color.lightGray);
		
		exit = makeMenuButton("./src/Pictures/Exit.PNG", 450, 100, 470, 210 + 85
				+ 85 + 30 + 30);
		exit.setFocusable(false);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exit.setBackground(Color.lightGray);
		
		bgLabel = new JLabel();
		bgLabel.setIcon(new ImageIcon("./src/Pictures/bg.PNG"));

		add(newGame);
		add(contin);
		add(exit);
		add(bgLabel);

		repaint();
		revalidate();

	}

	private JButton makeMenuButton(String url, int w, int h, int x, int y) {
		JButton temp = new JButton(new ImageIcon(url));
		temp.setBackground(Color.black);
		temp.setSize(w, h);
		temp.setLocation(x, y);
		return temp;
	}

	public void drawFallingShapes(Deque<Shape> shapes) {
		sd.setShapes(shapes);
		repaint();
		revalidate();
	}

	public void drawPlayers(ArrayList<Player> players) {
		sd.setPlayer(players);
		updateMoves();
		repaint();
		revalidate();
	}

	public void setTime(int time) {

		timerBoard.remove(timer);
		timer = new JLabel("" + time);
		timer.setFont(new Font("Veerdana", 100, 70));
		timerBoard.add(timer);
		timerBoard.repaint();
		timerBoard.revalidate();

	}

	public void start() {
		
		remove(newGame);
		remove(contin);
		remove(exit);
		remove(bgLabel);

		bar = new JPanel();
		bar.setBackground(Color.black);
		bar.setBounds(0, 0, 1700, 90);
		
		menu = new JButton(new ImageIcon("./src/Pictures/menu.PNG"));
		menu.setFocusable(false);
		menu.setSize(300, 70);
		menu.setLocation(10, 7);
		menu.setBackground(Color.lightGray);
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				remove(sd);
				remove(bar);
				remove(menu);
				remove(save);
				remove(exit2);
				remove(timerBoard);
				remove(SRNBoarder);
				remove(SLNBoarder);
				remove(scoreLBoarder);
				remove(scoreRBoarder);
				add(newGame);
				add(contin);
				add(exit);
				add(bgLabel);

				repaint();
				revalidate();
			}

		});

		save = new JButton(new ImageIcon("./src/Pictures/save.PNG"));
		save.setFocusable(false);
		save.setSize(300, 70);
		save.setLocation(330, 7);
		save.setBackground(Color.lightGray);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				notifyObserver("save");
			}

		});

		exit2 = new JButton(new ImageIcon("./src/Pictures/exit2.PNG"));
		exit2.setFocusable(false);
		exit2.setSize(300, 70);
		exit2.setLocation(660, 7);
		exit2.setBackground(Color.lightGray);
		exit2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		timer = new JLabel("" + 0);
		timer.setFont(new Font("Italic", 100, 70));
		timer.setLocation(0, 0);
		timerBoard = new JPanel();
		timerBoard.add(timer);
		timerBoard.setBackground(Color.lightGray);
		timerBoard.setBounds(1200, 3, 100, 85);
		
		scoreL = new JLabel(new ImageIcon("./src/Pictures/player1.PNG"));
		scoreL.setLocation(0, 0);
		scoreLBoarder = new JPanel();
		scoreLBoarder.add(scoreL);
		scoreLBoarder.setBackground(Color.BLACK);
		scoreLBoarder.setBounds(0, 90, 300, 45);
		scoreLBoarder.setBounds(1000, 90, 250, 45);

		
		scoreR = new JLabel(new ImageIcon("./src/Pictures/player2.PNG"));
		scoreR.setLocation(0, 0);
		scoreRBoarder = new JPanel();
		scoreRBoarder.add(scoreR);
		scoreRBoarder.setBackground(Color.BLACK);
		scoreRBoarder.setBounds(0, 90, 300, 45);
		
		scoreLNum = new JLabel("0");
		scoreLNum.setLocation(0, 0);
		scoreLNum.setFont(new Font("Italic", 50, 35));
		SLNBoarder = new JPanel();
		SLNBoarder.add(scoreLNum);
		SLNBoarder.setBackground(Color.BLACK);
		SLNBoarder.setBounds(1250, 90, 100, 45);
		
		
		scoreRNum = new JLabel("0");
		scoreRNum.setLocation(0, 0);
		scoreRNum.setFont(new Font("Italic", 50, 35));
		SRNBoarder = new JPanel();
		SRNBoarder.add(scoreRNum);
		SRNBoarder.setBackground(Color.BLACK);
		SRNBoarder.setBounds(300, 90, 100, 45);
		
		sd = new Drawer();
		
		add(SRNBoarder);
		add(SLNBoarder);
		add(scoreLBoarder);
		add(scoreRBoarder);
		add(timerBoard);
		add(exit2);
		add(save);
		add(menu);
		add(bar);
		add(sd);
		repaint();
		revalidate();
	}
	
	
	public void setScore1(int score1){
		
		SLNBoarder.remove(scoreLNum);
		scoreLNum = new JLabel(score1+"");
		scoreLNum.setLocation(0, 0);
		scoreLNum.setFont(new Font("Italic", 50, 35));
		SLNBoarder.add(scoreLNum);
		
		
	}
	public void setScore2(int score2){
		
		SRNBoarder.remove(scoreRNum);
		scoreRNum = new JLabel(score2+"");
		scoreRNum.setLocation(0, 0);
		scoreRNum.setFont(new Font("Italic", 50, 35));
		SRNBoarder.add(scoreRNum);
		
	}
	
	public void announceWinner(int winnerNum){
		
		
		if(winnerNum==0){
			winner = new JLabel(new ImageIcon("./src/Pictures/draw.PNG"));
		}
		else if(winnerNum == 1){
			winner = new JLabel(new ImageIcon("./src/Pictures/winner1.PNG"));
		}
		else
			winner = new JLabel(new ImageIcon("./src/Pictures/winner2.PNG"));
		
		
		
		remove(sd);
		remove(bar);
		remove(menu);
		remove(save);
		remove(exit2);
		remove(timerBoard);
		remove(SRNBoarder);
		remove(SLNBoarder);
		remove(scoreLBoarder);
		remove(scoreRBoarder);
		
		add(winner);
		repaint();
		revalidate();
		
		cnt=0;
		t = new Timer(4000, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				if(cnt>0){
					
					remove(winner);
					
					add(newGame);
					add(contin);
					add(exit);
					add(bgLabel);
	
					repaint();
					revalidate();
				}
				cnt++;
				
				if(cnt>1)
					t.stop();
			}
			
			
			
		});
		t.start();

		
	}

	// ////////////////////////////////////
	private void notifyObserver(String cmd) {
		sub.notifyObserver(cmd);
	}

	public void register(Observer o) {
		sub.register(o);
	}

	public void unregister(Observer o) {
		sub.unregister(o);
	}
	// ///////////////////////////////////////
	
	public void keyPressed(KeyEvent e) {
		if(sd!=null){
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_LEFT)
				velX1=-Velocity;
			else if (code == KeyEvent.VK_RIGHT)
				velX1=Velocity;
			else if (code == KeyEvent.VK_A)
				velX2=-Velocity;
			else if (code == KeyEvent.VK_D)
				velX2=Velocity;
		}
	}
	
	public void keyTyped(KeyEvent e) {
	}
	
	public void keyReleased(KeyEvent e){
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT)
			velX1 = 0;
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_D)
			velX2 = 0;
	}
	
	private void updateMoves(){
		Point p1 = sd.players.get(0).getPosition();
		Point p2 = sd.players.get(1).getPosition();
		if(p1.getX()>-1 && p1.getX()<1101)
			p1.setX(p1.getX()+velX1);
		if(p1.getX()<0)
			p1.setX(0);
		if (p1.getX()>1100)
			p1.setX(1100);
		
		if(p2.getX()>-1 && p2.getX()<1101)
			p2.setX(p2.getX()+velX2);
		if(p2.getX()<0)
			p2.setX(0);
		if (p2.getX()>1100)
			p2.setX(1100);
	}
}
