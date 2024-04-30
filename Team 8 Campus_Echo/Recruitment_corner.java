package buffer5_0;

import java.util.*;
import java.text.SimpleDateFormat;

class recruitment_post {
	String title;
	int id;
	String content;
	int year;
	int deadLine;//number of days remaining 
	String datePosted;
	String cl;
	String regLink;
	recruitment_post(String t, int i , String c, int y, int dl, String dp , String cl, String link){
		title = t;
		content = c;
		year = y;
		deadLine = dl;
		datePosted = dp;
		this.cl = cl;
		regLink = link;
	}
}


public class Recruitment_corner {
	//using heap
	ArrayList<recruitment_post> posts_heap = new ArrayList<recruitment_post>();
	
	//Queue<recruitment_post> posts;
	int numOfPosts;
	Recruitment_corner(){
		//posts = new LinkedList<>();
		numOfPosts = 0;
	}
	public void recruitment_corner() {
		System.out.println("What do you want to do?");
		System.out.println("1.Display all postings by clubs");
		System.out.println("2.Create a new recruitment posting");
		System.out.println("3.Add a post to starred posts(and enable notifications)");
	} 
public void create_post(Scanner sc ) {
		
		recruitment_post p;
		System.out.println("Enter post title :");
		sc.next();
		String t = sc.nextLine();
		System.out.println("Enter content :");
		String c = sc.nextLine();
		System.out.println("Year for recruitment :");
		int y = sc.nextInt();
		System.out.println("Registration deadline(in number of days)");
		int rd = sc.nextInt();
		Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    String strDate= formatter.format(date); 
	    System.out.println("Enter club name:");
	    sc.nextLine();
	    String cl = sc.nextLine();
	    System.out.println("Enter url of google form/ registration link: ");
	    String link = sc.nextLine();
	    p = new recruitment_post(t,++numOfPosts,c,y,rd,strDate,cl,link);
	    //posts.add(p); normal queue
	    insertHeap(p);
	    
	    return;
	}
	void insertHeap(recruitment_post p) {
		posts_heap.add(p);
		int idx = posts_heap.size()-1;
		while(idx>0) {
			if(posts_heap.get(idx/2).deadLine>posts_heap.get(idx).deadLine) {
				recruitment_post temp = posts_heap.get(idx);
				posts_heap.set(idx,posts_heap.get(idx/2));
				posts_heap.set(idx/2,temp);
			}
			idx = idx/2;
		}
	}
	
	public void displayPosts() {
		System.out.println("----------------------------------------");
		System.out.println("          Recruitment Corner!           ");
		System.out.println(" Latest updates about club recruitments ");
		System.out.println("----------------------------------------");
		
		if(posts_heap.isEmpty()) {
			System.out.println("Sorry! Nothing to see here!");
			return;
		}

		for(recruitment_post e : posts_heap) {
			System.out.println(e.cl);
			System.out.println(e.title);
			System.out.println(e.datePosted);
			System.out.println("Deadline :"+e.deadLine+" days from now!");
			System.out.println("----------------------------------------");
			System.out.println(e.content);
			System.out.println("----------------------------------------");
			System.out.println("Link : "+e.regLink);
			System.out.println("----------------------------------------");
		}
	}
	public static void main(Scanner sc ,Recruitment_corner rc) {
		// TODO Auto-generated method stub
		//Recruitment_corner rc = new Recruitment_corner();
		rc.displayPosts();
		int option=-1;
		do {
			rc.create_post(sc);
			System.out.println("Do you want to add more posts ? ");
			System.out.println("1)Yes \n2)No");
			
			option=sc.nextInt();
		}while(option!=2);
		
		
		rc.displayPosts();
	}

}