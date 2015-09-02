package View;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JPanel;

import Model.PStack;
import Model.Player;
import Model.Shape;

public class Drawer extends JPanel{

	private Deque<Shape> shapes;
	public ArrayList<Player> players;

	public void paint(Graphics g) {

		Shape temp;
		Player p;

		Image img = Toolkit.getDefaultToolkit().getImage("./src/Pictures/BackG.PNG");
		g.drawImage(img, 0, 0, null);

		if (shapes != null) {
			Iterator it = shapes.iterator();
			while (it.hasNext()) {
				temp = (Shape) it.next();
				img = Toolkit.getDefaultToolkit().getImage("./src/Pictures/" + temp.getType() + temp.getColor()+ ".PNG");
				g.drawImage(img, temp.getLocation().getX(), temp.getLocation().getY(), null);
			}
		}
		if (players != null) {
			Iterator it = players.iterator();
			int i=0;
			while (it.hasNext()) {
				p = (Player) it.next();
				img = Toolkit.getDefaultToolkit().getImage("./src/Pictures/" +"Clown"+Integer.toString(++i)+ ".PNG");
				g.drawImage(img, p.getPosition().getX(), p.getPosition().getY(), null);
				
				int cnt = 0;
//				Stack<Shape> tempStack  = new Stack<Shape>();
//				Stack<Shape> StackUnderDrawing = p.getRightStack().getPlates() ;
				Iterator shapesIn =p.getRightStack().getPlates().iterator();
				int stackSize = p.getRightStack().getPlates().size();
				while(shapesIn.hasNext()){
					temp = (Shape) shapesIn.next();
					img = Toolkit.getDefaultToolkit().getImage("./src/Pictures/" + temp.getType() + temp.getColor()+ ".PNG");
					g.drawImage(img,p.getRightStackPosition().getX()-temp.getHeight() , p.getRightStackPosition().getY()+(stackSize*temp.getHeight()), null);
					stackSize--;
				}
				
				
				shapesIn =p.getLeftStack().getPlates().iterator();
				stackSize = p.getLeftStack().getPlates().size();
				while(shapesIn.hasNext()){
					temp = (Shape) shapesIn.next();
					img = Toolkit.getDefaultToolkit().getImage("./src/Pictures/" + temp.getType() + temp.getColor()+ ".PNG");
					g.drawImage(img,p.getLeftStackPosition().getX()-temp.getHeight() , p.getLeftStackPosition().getY()+(stackSize*temp.getHeight()), null);
					stackSize--;
				}
			}
			
			
		}
	}

	public void setShapes(Deque<Shape> s) {
		shapes = s;
	}

	public void setPlayer(ArrayList<Player> p) {
		players = p;
	}
}