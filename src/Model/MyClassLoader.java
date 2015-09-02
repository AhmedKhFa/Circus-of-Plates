package Model;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFileChooser;

public class MyClassLoader {
	public static Class importClass(String cName) throws Throwable {
		File f = new File("./bin/Model/"+cName+".class");
		String temp = f.getName();
		String[] className = temp.split(".class");
		URL t = new File(f.getParent()).toURI().toURL();

		URL[] s = { t };
		URLClassLoader c = new URLClassLoader(s, Shape.class.getClassLoader());
		return  c.loadClass("Model." + className[0]);

	}
}
