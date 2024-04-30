package buffer_demo;

import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DatabaseManager{
	private static final String url = "jdbc:mysql://localhost:3306/buffer5_0";
	private static final String username = "root";
	private static final String password = "root";

	static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}

class ClubManager{
	Scanner sc;
	 Calendar_club reg=new Calendar_club();
	 
	Connection con;
	Hierarchy hier=new Hierarchy();
	ClubManager(Scanner sc , Connection con , Recruitment_corner r_c) throws ClassNotFoundException, SQLException{
		int option=0;
		this.sc=sc;
		this.con=con;
		System.out.println("1)Login  2) Sign up as a new club");
		int loginoption=sc.nextInt();
		if(loginoption==1) {
			if(loginClub(sc)) {
				System.out.println("login successfull");
			}
			else {
				System.out.println("Incorrect password or username");
				loginClub(sc);
			}
		}
		else {
			System.out.println("Creating a new club ....");
			AddClub ac=new AddClub(sc,con);
		}
		while(option!=4) {
			System.out.println("1)Host an event \n2)Start recruitment "
					+ "\n3)add or update information \n4)exit");
			if(sc.hasNextInt()) {
				option=sc.nextInt();
				switch(option) {
				case 1:
					reg.addNewEvent();
					break;
				case 2:
					r_c.main(sc, r_c);
					break;
				case 3:
					hier.main(sc);
					//call the hirarchy methods
					//insert and delete 
					break;
				case 4:
					return ;
				}
			}
			
		}
	}
	boolean loginClub(Scanner sc) throws SQLException {
		System.out.println("Enter the name : ");
		String name=sc.next();
		System.out.println("Enter the password : ");
		String password=sc.next();
		if(authenticate(con,name,password)) {
			return true;
		}
		return false;
	}
	boolean authenticate(Connection conn,String username,String password) throws SQLException {
    	String query="SELECT password FROM clubuser WHERE name=?";
		PreparedStatement pstmt=conn.prepareStatement(query);
		pstmt.setString(1,username);
		ResultSet rs=pstmt.executeQuery();
		String storedpass="";
		while(rs.next()) {
			storedpass=rs.getString("password");
		}
		
		//Implement obj1=new Implement();
		String chkpass= storedpass;
		return chkpass.equals(password);
    }
}
/*class UserManager {
	  Calendar_club imp1;
	  Calendar_event imp;

	  Scanner sc;

	  UserManager(Scanner scanner) {
	    this.sc = scanner;
	    this.imp1 = new Calendar_club(); // Create a single Calendar_club instance
	    this.imp = new Calendar_event(); // Create a single Calendar_event instance (optional)
	  }

	  void processUserInput(Recruitment_corner rc) {
	    int option = 0;
	    while (option != 6) {
	      System.out.println("1) Search for a club\n2) Register in an event\n3) Register for a club membership\n4) To do List\n5) Notifications\n6) Logout");

	      if (sc.hasNextInt()) {
	        option = sc.nextInt();

	        switch (option) {
	          case 1:
	            search_for_a_Club();
	            break;
	          case 2:
	            register_for_an_event(imp1); // Pass the Calendar_club instance
	            break;
	          case 3:
	            rc.displayPosts();
	            register_for_a_club_membership();
	            break;
	          case 4:
	            to_do();
	            break;
	          case 5:
	            Notifications n = new Notifications();
	            Notifications.main();
	            break;
	          case 6:
	            return;
	          default:
	            System.out.println("Invalid option! Please choose again.");
	            break;
	        }
	      } else {
	        System.out.println("Invalid input! Please enter a number.");
	        sc.next(); // Clear the scanner buffer
	      }
	    }
	  }

	  @SuppressWarnings("static-access")
	  void search_for_a_Club() {
	    System.out.println("Searching for a club...");
	    Main obj = new Main();
	    obj.main(sc);
	    System.out.println("thanks for visiting ....");
	  }

	  @SuppressWarnings("static-access")
	void register_for_an_event(Calendar_club calendarClub) {
	    System.out.println("Registering for an event...");
	    calendarClub.main(calendarClub);; // Call methods on the passed Calendar_club instance
	  }

	  void to_do() {
	    System.out.println("To Do List : ");
	    imp.displayToDoList(); // Call methods on the Calendar_event instance (if needed)
	  }

	  void register_for_a_club_membership() {
	    System.out.println("Registering for a club membership...");
	  }
	}

*/

class UserManager {
	  Calendar_club reg=new Calendar_club();
	
    Scanner sc;
   // Recruitment_corner rc;
    UserManager(Scanner scanner,  Calendar_club reg) {
        this.sc = scanner;
       
    }

    void processUserInput(Recruitment_corner rc) {
        int option = 0;
        while(option!=6) {

        	System.out.println("1) Search for a club\n2) Register in an event\n3) Register for a club membership\n4) To do List\n5) Notifications\n6) Logout");


        	if (sc.hasNextInt()) {

        	option = sc.nextInt();

        	switch (option) {

        	case 1:

        	search_for_a_Club();

        	break;

        	case 2:
        		register_for_an_event();
        	
        	break;

        	case 3:


        	rc.displayPosts();

        	register_for_a_club_membership();

        	break;


        	case 4:

        	to_do();

        	break;


        	case 5:

        	Notifications n=new Notifications();

        	Notifications.main();

        	break;

        	case 6:

        	return;

        	default:

        	System.out.println("Invalid option! Please choose again.");

        	break;

        	}

        	} else {

        	System.out.println("Invalid input! Please enter a number.");

        	}}
        	}


    @SuppressWarnings("static-access")
	void search_for_a_Club() {
        System.out.println("Searching for a club...");
        Main obj=new Main();
		obj.main(sc);
		System.out.println("thanks for visiting ....");

    }
    void register_for_an_event() {
        System.out.println("Registering for an event...");
        reg.main(reg);
    }
   void to_do ()
    {
    	System.out.println("To Do List : ");
    	reg.displayToDoList();
    }
   
    void register_for_a_club_membership() {
        System.out.println("Registering for a club membership...");
    }}

public class HomePage {
   
    public static void main(String[] args) throws ClassNotFoundException {
    	Recruitment_corner rc=new Recruitment_corner();
    	  Calendar_club calendar=new Calendar_club();
    	  	
        Scanner sc = new Scanner(System.in);
        try {
			Connection conn=DatabaseManager.getConnection();
		        int option = 0;
        while(option!=5) {
            System.out.println("1) Login as a user\n2) Login as a club\n3) FAQ\n4) Notifications \n5) Exit");
            option = sc.nextInt();
            switch (option) {
            
                case 1:
                	
                    
                	System.out.println("1)Login\n2)New user...sign up");
                	int ch=sc.nextInt();
                	if(ch==1) {
                		System.out.println("Enter UNumber");
                		String username=sc.next();
                		System.out.println("Enter password");
                		String password=sc.next();
                		boolean chk=authenticate(conn,username,password);
                		if(chk) {
                			System.out.println("Logged in successfully....");
                		}
                		else {
                			System.out.println("Incorrect credentials");
                		}
                		
                	}
                	else if(ch==2) {
                		Implement obj2 = new Implement();
                        obj2.main(sc);
                	}
                    
                    UserManager um = new UserManager(sc, calendar);
                    um.processUserInput(rc);
                    break;
                case 2:
                    
                    System.out.println("Login as a club");
                    ClubManager cm=new ClubManager(sc,conn,rc);
                    break;
                case 3:
                    System.out.println("=====FAQ's=====");
                    FAQ faq = new FAQ();
                    break;
                
                case 4:
                	calendar.displayNotifications();
                	break;
                	
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option! Please choose again.");
                    break;
            }
            
        } 
       
        
        }catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}


    }
    public static boolean authenticate(Connection conn,String username,String password) throws SQLException {
    	String query="SELECT password FROM buffer5_0 WHERE username=?";
		PreparedStatement pstmt=conn.prepareStatement(query);
		pstmt.setString(1,username);
		ResultSet rs=pstmt.executeQuery();
		String storedpass="";
		while(rs.next()) {
			storedpass=rs.getString("password");
			//System.out.print(storedpass);
		}
		
		Implement obj1=new Implement();
		String chkpass= obj1.decrypt(storedpass);
		return chkpass.equals(password);
    }
}
//condition for the club name
//login for user problem
//
