package buffer5_0;

import java.util.HashMap;
import java.util.Scanner;

class FAQ{
	Scanner sc=new Scanner(System.in);
	HashMap <Integer,String> map=new HashMap<>();
	HashMap <Integer,String> QuesMap=new HashMap<>();
	
	public FAQ() {
        addQuestions();
        displayQuestions();
       
    }

	void addQuestions() {
		map.put(1, "Keep checking out the recruitment corner of "
				+ "this site to find the club that interests youa nd apply!!");
		map.put(2, "Our campus offers a wide variety of clubs ranging from academic, cultural, recreational,"
				+ " to special interest groups. You can find a list of available clubs on our website.");
		map.put(3, "Yes, you can start your own club! We encourage students to bring new ideas and interests to campus."
				+ " Check our website for information on how to start a new club.");
		map.put(4,"Absolutely! You're welcome to join as many clubs as your schedule allows. Just be mindful of your time commitments"
				+ " and make sure you can actively participate in each club you join.");
		map.put(5,"Check out the events corner of the website to stay updated!!");
		//System.out.println("Enter the question number ");
		//int number=sc.nextInt();
		
	}
	
	void displayQuestions() {
		QuesMap.put(1, "How do I join a club?");
		QuesMap.put(2,"What types of clubs are available on campus?");
		QuesMap.put(3,"Can I start my own club? ");
		QuesMap.put(4,"Can I join multiple clubs?");
		QuesMap.put(5,"How do i know about the various events going on in the college");
		System.out.println("1)"+QuesMap.get(1)+"\n"+map.get(1)+"\n\n"+
		"\n2)"+QuesMap.get(2)+"\n"+map.get(2)+"\n\n"+
		"\n3"+QuesMap.get(3)+"\n"+"\n\n"+map.get(3)+"\n\n"+
		"\n4)"+QuesMap.get(4)+"\n"+map.get(4)+"\n\n"+
		"\n5)"+QuesMap.get(5)+"\n"+map.get(5)+"\n\n");
		//System.out.println( map.get(num));
		
	}
	


	
	
}