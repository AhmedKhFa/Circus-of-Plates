package Controller;
import java.util.ArrayList;
import java.util.Random;

import Model.Shape;
import Model.ShapeFactory;

public class ShapePool {
	///singleton
	private static ShapePool instance;
	
	private ShapePool(){}
	
	public synchronized static ShapePool getInstance(){
		if(instance == null)
			instance = new ShapePool();
		return instance;
	}
	/////
	private ShapeFactory shapeFactory;
	private ArrayList<Shape> pool = new ArrayList<Shape>();
	int maxPoolSize = 200; //to be adjusted
	private int created = 0;
	
	public Shape acquireShape(){
		if(pool.isEmpty() && created == maxPoolSize){
			System.out.println("Wait until shape is released!");
			return null;
		}
		if(pool.isEmpty())
			return createShape();
		
		int index = randomNumber(pool.size());
		Shape s = pool.get(index);
		pool.remove(index);
		return s;
	}
	public void releaseShape(Shape shape){
		shape.setColor(0);
		shape.setLocation(null);
		shape.setState(null);
		pool.add(shape);
	}
	public void setMaxPoolSize(int maxSize){
		maxPoolSize = maxSize;
	}
	private Shape createShape(){
		if(shapeFactory == null)		//lazy instantiation
			shapeFactory = new ShapeFactory();
		
		created++;
		Shape s = shapeFactory.createShape(randomType());
		return s;
	}
	private String randomType(){
		String[] types = {"Moon", "Meteor", "Star"};
		return types[randomNumber(3)];
	}
	private int randomNumber(int peek){
		Random rn = new Random();
		return rn.nextInt(peek);
	}
	public void clean(){
		created = pool.size() ;
	}
}
