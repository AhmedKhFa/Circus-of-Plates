package Model;

public class ShapeFactory {

	Shape shape;

	public Shape createShape(String type) {
		try {
			if (type.equals("Star")) {
				shape = (Shape) MyClassLoader.importClass("Star").newInstance();
			} else if (type.equals("Moon")) {
				shape = (Shape) MyClassLoader.importClass("Moon").newInstance();
			} else if (type.equals("Meteor")) {
				shape = (Shape) MyClassLoader.importClass("Meteor")
						.newInstance();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return shape;
	}
}