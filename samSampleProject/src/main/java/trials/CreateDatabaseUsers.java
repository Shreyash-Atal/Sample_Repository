package trials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabaseUsers {
	public static void main(String[] args) throws Exception {
		Thread t0 = new Thread(new CreateUsers(), "ThreadA");
		Thread t1 = new Thread(new CreateUsers(), "ThreadB");
		Thread t2 = new Thread(new CreateUsers(), "ThreadC");
		Thread t3 = new Thread(new CreateUsers(), "ThreadD");
		Thread t4 = new Thread(new CreateUsers(), "ThreadE");
		
		t0.start();
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}

class CreateUsers implements Runnable{

	Connection con;
	
	public CreateUsers() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
		
		con.setAutoCommit(true);
	}
	
	public void run() {
		try {
			Statement stmt = con.createStatement();
		
			for(int i =0; i<5 ; i++){
				stmt.execute("CREATE USER sanket" + Thread.currentThread().getName() 
						+ "@localhost IDENTIFIED BY 'AsDf';");
				
				stmt.execute("SET PASSWORD FOR sanket" + Thread.currentThread().getName() 
						+ "@localhost = PASSWORD('AsDfG');");
				
				stmt.execute("DROP USER sanket" + Thread.currentThread().getName() + "@localhost;");
				
				System.out.println("User : sanket" + Thread.currentThread().getName() + " Processed");
			}
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
}
