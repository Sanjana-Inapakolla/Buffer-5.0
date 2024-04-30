package buffer_demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String mail;
    private String year;
    private String branch;
    private long mobNo;
    private ArrayList<String> clubs;

    User() {
        clubs = new ArrayList<>();
    }

    public User(String u, String p, String fn, String ln, String m, String y, String b, long mno) {
        this();
        username = u;
        password = p;
        firstName = fn;
        lastName = ln;
        mail = m;
        year = y;
        branch = b;
        mobNo = mno;
    }


    // Getter and Setter Methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public long getMobNo() {
        return mobNo;
    }

    public void setMobNo(long mobNo) {
        this.mobNo = mobNo;
    }

    public ArrayList<String> getClubs() {
        return clubs;
    }

    public void setClubs(ArrayList<String> clubs) {
        this.clubs = clubs;
    }


    public void insertUserDataIntoDB(Connection con) throws SQLException {
        String query = "INSERT INTO buffer5_0 (username, password, firstName, lastName, mail, year, branch, mobNo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, getUsername());
            preparedStatement.setString(2, getPassword());
            preparedStatement.setString(3, getFirstName());
            preparedStatement.setString(4, getLastName());
            preparedStatement.setString(5, getMail());
            preparedStatement.setString(6, getYear());
            preparedStatement.setString(7, getBranch());
            preparedStatement.setLong(8, getMobNo());

            preparedStatement.executeUpdate();
        }
    }
}

public class Implement {
    private static Map<Character, Character> encryptionMap = new HashMap<>();

    public static void main(Scanner sc) {
        //Scanner sc = new Scanner(System.in);
        initializeEncryptionMap();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buffer5_0", "root", "root")) {
                System.out.println("Connection created");
                signup(con, sc);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    static void signup(Connection con, Scanner sc) {
        System.out.println("Enter your roll number:");
        String roll = sc.next();
        User obj = new User();
        obj.setUsername(roll);

        System.out.println("Enter password  : ");
        String password = sc.next();
        int flag=0,upper=0,lower=0,digit=0;
        do {
        	 if(password.length()<8)
        	 {
        		 System.out.println("Enter a password with atleast 8 charachters");
        		 password = sc.next(); 
        	 }
        	 else {
        		 for(int i=0;i<password.length();i++)
        		 {
        			 char ch=password.charAt(i);
        			 if(Character.isUpperCase(ch))
        				 upper++;
        			 else if(Character.isLowerCase(ch))
        				 lower++;
        			 else if(Character.isDigit(ch))
        				 digit++;
        				 
        			 flag=1;
        		 }
        		 
        		 if(upper<1||lower<1||digit<1)
        		 {
        			 if(upper<1)
        			 System.out.println("Enter atleast one upper case letter");
        			 if(lower<1)
            			 System.out.println("Enter atleast one lower case letter");
        			 if(digit<1)
            			 System.out.println("Enter atleast one digit ");
        			 
        			 password = sc.next(); 
        		 }
        	 }
        }while(flag==0);
 String encryptedPassword = encrypt(password, encryptionMap);
        obj.setPassword(encryptedPassword);

        System.out.println("Enter first name : ");
        String fname = sc.next();
        obj.setFirstName(fname);

        System.out.println("Enter last name : ");
        String lname = sc.next();
        obj.setLastName(lname);

        System.out.println("Enter mail id : ");
        String mail = sc.next();
        if (!mail.endsWith("@cumminscollege.in"))// Ensure email ends with '@gmail.com'
{
            System.out.println("Enter mail id (ending with '@cumminscollege.in') : ");
            mail = sc.next();
        } 
        obj.setMail(mail);

        System.out.println("Enter year  : ");
        String year = sc.next();
        obj.setYear(year);

        System.out.println("Enter branch : ");
        String branch = sc.next();
        obj.setBranch(branch);

        System.out.println("Enter mobNo : ");
        long mobNo = sc.nextLong(); // Assuming mobNo is entered as a long

     // Convert the long mobNo to a string
     String mobNoStr = String.valueOf(mobNo);

     // Check if the length of the string is exactly 10
     int flag1=0;
     do         {
     if (mobNoStr.length() == 10) {
         System.out.println("Mobile number is valid.");
         flag1=1;
         break;
     } else {
         System.out.println("Mobile number should be exactly 10 digits long.");
         System.out.println("Enter again");
         mobNo = sc.nextLong(); 
         mobNoStr = String.valueOf(mobNo);

     }}while(flag1==0);

obj.setMobNo(mobNo);

        try {
            obj.insertUserDataIntoDB(con);
            System.out.println("User data inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to insert user data into the database.");
            e.printStackTrace();
        }
    }

    private static String getPasswordFromUser(Scanner sc) {
        String password;
        while (true) {
            System.out.println("Enter password (min 8 characters, at least one uppercase, one lowercase, and one digit):");
            password = sc.next();
            if (isValidPassword(password)) {
                break;
            }
        }
        return password;
    }

    private static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUpper = false, hasLower = false, hasDigit = false;
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpper = true;
            if (Character.isLowerCase(ch)) hasLower = true;
            if (Character.isDigit(ch)) hasDigit = true;
        }
        return hasUpper && hasLower && hasDigit;
    }

    private static void initializeEncryptionMap() {
        for (char c = 'A'; c <= 'Z'; c++) {
            char encryptedChar = (char) ((c - 'A' + 5) % 26 + 'A');
            encryptionMap.put(c, encryptedChar);
        }
        for (char c = 'a'; c <= 'z'; c++) {
            char encryptedChar = (char) ((c - 'a' + 5) % 26 + 'a');
            encryptionMap.put(c, encryptedChar);
        }
    }

    public static String encrypt(String password, Map<Character, Character> encryptionMap) {
        StringBuilder encryptedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            encryptedPassword.append(encryptionMap.getOrDefault(c, c));
        }
        return encryptedPassword.toString();
    }
    public static String decrypt(String encryptedPassword) {
        StringBuilder decryptedPassword = new StringBuilder();
        for (int i = 0; i < encryptedPassword.length(); i++) {
            char c = encryptedPassword.charAt(i);
            if (Character.isUpperCase(c)) {
                char decryptedChar = (char) ((c - 'A' - 5 + 26) % 26 + 'A');
                decryptedPassword.append(decryptedChar);
            } else if (Character.isLowerCase(c)) {
                char decryptedChar = (char) ((c - 'a' - 5 + 26) % 26 + 'a');
                decryptedPassword.append(decryptedChar);
            } else {
                // If the character is not an alphabet, just append it as it is
                decryptedPassword.append(c);
            }
        }
        return decryptedPassword.toString();
    }

    
}