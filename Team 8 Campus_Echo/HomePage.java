package buffer5_0;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class DatabaseManager{
	private static final String url = "jdbc:mysql://localhost:3306/buffer5_0";
	private static final String username = "root";
	private static final String password = "Root556;";

	static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}

class ClubManager{
	Scanner sc;
	Connection con;
	ClubManager(Scanner sc , Connection con,Recruitment_corner r_c,Calendar_club calendar) throws ClassNotFoundException, SQLException{
		int option=0;
		this.sc=sc;
		this.con=con;
		System.out.println("1)Login  2) Sign up as a new club");
		int loginoption=sc.nextInt();
		if(loginoption==1) {
			if(loginClub(sc)) {
				System.out.println("\u001B[32mlogin successful\u001B[0m");
				
			}
			else {
				System.out.println("\u001B[31mIncorrect password or username\u001B[0m");
				loginClub(sc);
			}
		}
		else {
			System.out.println("Creating a new club ....");
			AddClub ac=new AddClub(sc,con);
		}
		while(option!=4) {
			System.out.println("1)Host an event \n2)Start recruitment "
					+ "\n3)create and display club hierarchy \n4)exit");
			if(sc.hasNextInt()) {
				option=sc.nextInt();
				switch(option) {
				case 1:
					
					calendar.addNewEvent();
					calendar.displayCalendar();
					
					break;
				case 2:
				
					r_c.main(sc,r_c);
					break;
				case 3:
					Hierarchy h = new Hierarchy();
					h.main(sc);
					//call the hirarchy methods
					//insert and delete 
					break;
					
				case 4:
					return;
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
		
		
		String chkpass= storedpass;
		return chkpass.equals(password);
    }
}


class UserManager {
    Scanner sc;
    Calendar_club reg;
    Queue<Event> todoList;

    UserManager(Scanner scanner, Calendar_club reg) {
        this.sc = scanner;
        this.reg = reg;
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
                    	reg.displayNotifications();
                    	break;
                    case 6:
                    	return;
                    default:
                        System.out.println("Invalid option! Please choose again.");
                        break;
                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
        
    }

    void search_for_a_Club() {
        System.out.println("Searching for a club...");
        Main obj=new Main();
		obj.main(sc);
    }

    void register_for_an_event() {
        System.out.println("Registering for an event...");
        /*reg.call_calendar();
        reg.displayToDoList();*/
        reg.main(reg);
    }

    void register_for_a_club_membership() {
        System.out.println("Registering for a club membership...");
    }
    
    void to_do() {
    	reg.displayToDoList();
    }
    
    
}

public class HomePage {
   
    public static void main(String[] args) throws ClassNotFoundException {
    	Recruitment_corner rc=new Recruitment_corner();
    	Calendar_club calendar=new Calendar_club();
        Scanner sc = new Scanner(System.in);
        try {
			Connection conn=DatabaseManager.getConnection();
		        int option = 0;
        while(option!=4) {
            System.out.println("1) Login as a user\n2) Login as a club\n3) FAQ\n4) Exit");
            option = sc.nextInt();
            switch (option) {
            
                case 1:
                	System.out.println("1) Login\n2) New user...sign up");
                    int ch = sc.nextInt();
                    if(ch == 1) {
                        int attempts = 0;
                        while(attempts < 3) {
                            System.out.println("Enter UNumber");
                            String username = sc.next();
                            System.out.println("Enter password");
                            String password = sc.next();
                            boolean chk = authenticate(conn, username, password);
                            if(chk) {
                                System.out.println("Logged in successfully....");
                                break; 
                            }
                            else {
                                attempts++;
                                if (attempts < 3) {
                                    System.out.println("Incorrect credentials. Please try again.");
                                } else {
                                    System.out.println("Maximum login attempts exceeded. ");
                                    System.exit(0);                                 }
                            }
                        }
                    }
                    else if(ch == 2) {
                        Implement obj2 = new Implement();
                        obj2.main(sc);
                    }
                    
                    UserManager um = new UserManager(sc, calendar);

                    um.processUserInput(rc);
                    break;
                 
                case 2:
                    
                    System.out.println("Login as a club");
                    ClubManager cm=new ClubManager(sc,conn,rc,calendar);
                    break;
                case 3:
                    System.out.println("=====FAQ's=====");
                    FAQ faq = new FAQ();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option! Please choose again.");
                    break;
            }
            
        } 
       
        
        }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			
		}
		
		Implement obj1=new Implement();
		String chkpass= obj1.decrypt(storedpass);
		return chkpass.equals(password);
    }
}
