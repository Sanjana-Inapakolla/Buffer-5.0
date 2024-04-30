package buffer5_0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.*;


class TrieNode {
    TrieNode[] children;
    boolean isEnd;

    public TrieNode() {
        children = new TrieNode[52];
        isEnd = false;
    }
}

class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    void insert(String word) {
        TrieNode curr = root;
        for (char ch : word.toCharArray()) {
            int index = 0;
            if (Character.isLowerCase(ch)) {
                index = ch - 'a';
            } else if (Character.isUpperCase(ch)) {
                index = ch - 'A' + 26;
            } else {
                continue;
            }
            if (curr.children[index] == null) {
                curr.children[index] = new TrieNode();
            }
            curr = curr.children[index];
        }
        curr.isEnd = true;
    }

    boolean search(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            int index = 0;
            if (Character.isLowerCase(ch)) {
                index = ch - 'a';
            } else if (Character.isUpperCase(ch)) {
                index = ch - 'A' + 26;
            }
            if (current.children[index] == null) {
                return false;
            }
            current = current.children[index];
        }
        return true;
    }
    
    public List<String> searchClubNamesWithPrefix(String prefix) {
        List<String> clubNames = new ArrayList<>();
        searchClubNamesWithPrefixHelper(prefix, root, "", clubNames);
        return clubNames;
    }


    void searchClubNamesWithPrefixHelper(String prefix, TrieNode node, String currentPrefix,List<String> clubNames) {
        if (node == null) return;

        if (currentPrefix.startsWith(prefix) && node.isEnd) {
        	clubNames.add(currentPrefix);
        	}

        for (int i = 0; i < node.children.length; i++) {
            if (node.children[i] != null) {
                char ch = (char) (i < 26 ? 'a' + i : 'A' + (i - 26));
                searchClubNamesWithPrefixHelper(prefix, node.children[i], currentPrefix + ch,clubNames);
            }
        }
    }
}

public class Main {
	public static void  DisplayClubInfo(String name) throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3306/buffer5_0";
	    String username = "root";
	    String password = "Root556;";
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection con=DriverManager.getConnection(url,username,password);
	    String query="SELECT * from clubnames where Name=?";
	    PreparedStatement pstmt=con.prepareStatement(query);
	    pstmt.setString(1,name);
	    ResultSet rs=pstmt.executeQuery();
	    String description=null;
	    while(rs.next()) {
	    	System.out.println("Description");
	    	System.out.println(rs.getString(2)) ;
	    }
	    con.close();
	  
	}

    public static void main(Scanner scanner) {
        String url = "jdbc:mysql://localhost:3306/buffer5_0";
        String username = "root";
        String password = "Root556;";
        try {
        	
            Trie t = new Trie();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established");
            Statement stmt = con.createStatement();
            String sql = "Select * from clubNames";
            ResultSet rs = stmt.executeQuery(sql);
         

            System.out.println("=====College Clubs=====");
            while (rs.next()) {
                System.out.println(" " + rs.getString("Name"));
                t.insert(rs.getString("Name"));
            }

            System.out.println("=====Search For Clubs=====");
            System.out.print("Enter prefix to search for: ");
            String prefix = scanner.next();

            
          

            List<String> clubNames = t.searchClubNamesWithPrefix(prefix);
            if(clubNames.size()==0) {
            	System.out.println("No clubs found!");
            }
            for (String name : clubNames) {
            	System.out.println("Club names with prefix '" + prefix + "':");
            	System.out.println(name);
            	
                DisplayClubInfo(name);
                System.out.println();
            }
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        
    }
}