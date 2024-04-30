package buffer5_0;

import java.util.Scanner;
import java.sql.*;

public class AddClub {

	
	AddClub(Scanner sc , Connection conn) throws ClassNotFoundException , SQLException{
		sc.nextLine();
		System.out.println("Enter the name : ");
		String name=sc.nextLine();
		System.out.println("Enter the description : ");
		
		String description=sc.nextLine();
		String query="Insert into clubnames values (?,?)";
		PreparedStatement pstmt=conn.prepareStatement(query);
		pstmt.setString(1,name);
		pstmt.setString(2, description.toString());
		pstmt.execute();
		System.out.println("Data saved !");
		System.out.println("Enter the password : ");
		String passwordIn=sc.nextLine();
		System.out.println("Enter the email : ");
		String mailIn=sc.nextLine();
		String queryClubUser="Insert into clubuser values (?,?,?)";
		PreparedStatement pstmt2=conn.prepareStatement(queryClubUser);
		pstmt2.setString(1,name);
		pstmt2.setString(2,passwordIn);
		pstmt2.setString(3,mailIn);
		pstmt2.execute();
	}
	
}