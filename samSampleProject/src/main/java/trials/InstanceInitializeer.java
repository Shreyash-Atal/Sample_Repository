package trials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InstanceInitializeer {
	
	static public List<String> lst = new ArrayList<String>(){{add("x"); add("y");}};
	public InstanceInitializeer(){
		System.out.println("Parent Default constructor called");
	}
	
	public InstanceInitializeer(String name) { System.out.println(name + "'s constructor called"); }

    static { System.out.println("static initializer called"); }

    { System.out.println("instance initializer called"); }

//    static { System.out.println("static initializer2 called"); }

//    { System.out.println("instance initializer2 called"); }

    public static void main( String[] args ) {
//        new InstanceInitializeer("one");
//        new InstanceInitializeer("two");
//        new InstanceInitializeer("three");
    	
    	System.out.println(new HashMap<String, String>(){{put("x", "x"); put("y", "y");}});
  }
}
