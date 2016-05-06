package trials;

import java.util.ArrayList;
import java.util.List;

public class InstanceInitializerChild extends InstanceInitializeer {
	
	static private String xyz;
	
	public InstanceInitializerChild(String name) {
		xyz = "abc;";
		System.out.println(name + "Instance Initializer Child constructor");
	}
	
	static{System.out.println("Child Static Block");}
	
	{System.out.println("Child InstanceInitializer");}
	
	public static void main(String[] args) {
//		new InstanceInitializerChild("One");
//		new InstanceInitializerChild("two");
	}

}
