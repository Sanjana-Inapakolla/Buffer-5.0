package buffer5_0;

import java.util.*;

class Node1 {

	static Scanner sc = new Scanner(System.in);

	String path;//path in hierarchy

	String name;

	String type;// head, co-head, member, editor etc

	int level;

	ArrayList<Node1> childlist;

	Node1() {

		this.name = "";

		this.type = "";

	}

	Node1(String name, String type) {

		this.name = name;

		this.type = type;

		path = "";
	}

}

public class Hierarchy {
	Node1 root;

	Scanner sc = new Scanner(System.in);

	Hierarchy() {

		root = new Node1(" club ", "");

		root.level = 0;

		root.childlist = null;

		root.path = "->";

	}

	Hierarchy(String name, String type) {

		root = new Node1(name, type);

		root.level = 0;

		root.childlist = null;

		root.path =type+"->"+name;

	}
	void add(Node1 temp) {

		System.out.println("Adding node " + temp.name + "....");

		if (!temp.type.toLowerCase().equals("member"))// create childlist for temp only if it is a folder

		{

			temp.childlist = new ArrayList<>();

		}

		Node1 ptr = root;

		int incLevel = 0;


		while (true) {

			if (ptr.type.toLowerCase().equals("member")) {

				System.out.println("Cannot add a club member working under a member!");

				return;

			}

			// check if there exists sub folders

			if (ptr.childlist == null) {

				// if no sub folders, add into root folder

				ptr.childlist = new ArrayList<>();

				incLevel++;

				temp.level = incLevel;

				ptr.childlist.add(temp);

				temp.path = temp.path+"-->" +ptr.type+"->"+ptr.name;

				System.out.println("Member '" + temp.name + "' added under "+ptr.type+" "+ptr.name+"!");

				return;

			}

			// if sub folders exist ask if user wants to add into root or sub folder

			System.out.println("Do you want to add in " + ptr.type+"->"+ptr.name + "? Enter 1 for yes , 0 for no");

			temp.path = temp.path + "-->" + ptr.type+"->"+ptr.name;

			int ch = sc.nextInt();

			sc.nextLine();

			incLevel++;

			if (ch == 1) {

				// if root, add to root

				temp.level = incLevel;

				ptr.childlist.add(temp);

				System.out.println("member added under " + ptr.type+"->"+ptr.name);


				return;

			}

			// else print all sub folders and ask for name of folder to add into

			System.out.println("Following are the members of " + ptr.type+"->"+ptr.name + ": ");

			for (int i = 0; i < ptr.childlist.size(); i++) {

				System.out.println(ptr.childlist.get(i).name + " (" + ptr.childlist.get(i).type + ") ");

			}

			System.out.println("Enter head/team head name to add member for:");

			String name = sc.nextLine();

			boolean flag = false;

			// search the sub folder and repeat the process

			for (int i = 0; i < ptr.childlist.size(); i++) {

				if (name.equals(ptr.childlist.get(i).name)) {

					if (ptr.childlist.get(i).type.toLowerCase().contains("member")) {

						System.out.println("Cannot add under a member!");

						temp.path = "";

						return;

					}

					flag = true;

					Node1 parent = ptr.childlist.get(i);

					if (parent.childlist == null) {

						System.out.println("Adding into " + parent.type+"->"+parent.name);

						parent.childlist = new ArrayList<>();

						incLevel++;

						temp.level = incLevel;

						parent.childlist.add(temp);

						temp.path = temp.path + "-->" + parent.type+"->"+parent.name;

						System.out.println("Node added!");

						return;

					} else {


						ptr = parent;

						break;

					}

				}

			}

			// folder name entered does not match any existing folder

			if (!flag) {

				System.out.println("Club member does not exist!!");

				return;

			}

		}


	}
	void displayDF(Node1 root) {

		if (root == null) {

			return;

		}

		Stack<Node1> st = new Stack<>();

		Node1 ptr = root;

		st.add(root);

		while (!st.isEmpty()) {

			ptr = st.pop();

			for (int i = 0; i <= ptr.level; i++) {

				System.out.print(" ");

			}

			System.out.println(ptr.type + "-->" + ptr.name );

			if (ptr.childlist != null) {

				for (int i = ptr.childlist.size() - 1; i >= 0; i--) {

					st.add(ptr.childlist.get(i));

				}

			}

		}

	}

	void search(Node1 root) {

		System.out.println("Which club member you like to search? Enter position and name");

		String type = sc.nextLine();
		String name = sc.nextLine();

		sc.nextLine();

		if (root == null) {

			System.out.println("Club Directory is empty.");

			return;

		}

		Node1 result = searchNode(root, type, name);
		if (result == null) {
			System.out.println("not found!");
			return;
		} else {
			System.out.println("Member found!");
			System.out.println("Path:" + result.path + "-->" + result.type+"->"+result.name);
		}
	}

	Node1 searchNode(Node1 root, String name, String type) {
		if (root == null) {
			return null;
		}
		if (root.name.equals(name)&&root.type.equals(type)) {
			return root;
		}
		if (root.childlist != null) {
			for (Node1 child : root.childlist) {
				Node1 result = searchNode(child, name,type);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

	void delete(Node1 root) {
		System.out.println("Would you like to remove a member? Enter position");
		String type = sc.nextLine();
		System.out.println("Enter name of the member : ");
		String name = sc.next();
		sc.nextLine();

		if (root == null) {
			System.out.println("Directory does not exist.");
			return;
		}

		if (root.name.equalsIgnoreCase(name) && root.type.equalsIgnoreCase(type)) {
			System.out.println("Cannot delete the root.");
			return;
		}

		Stack<Node1> stack = new Stack<>();
		stack.push(root);

		while (!stack.isEmpty()) {
			Node1 currentNode = stack.pop();

			if (currentNode.childlist != null) {
				for (int i = 0; i < currentNode.childlist.size(); i++) {
					Node1 child = currentNode.childlist.get(i);
					if (child.name.equalsIgnoreCase(name) && child.type.equalsIgnoreCase(type)) {
						System.out.println("Do you still wish to continue deletion? (1/0)");
						int del = sc.nextInt();

						if (del == 0) {
							System.out.println("Returning to the menu");
							return;
						}
						currentNode.childlist.remove(i);
						System.out.println(name + " of position " + type + " deleted.");
						return;
					}

					if (!child.type.toLowerCase().contains("member")) {
						stack.push(child);
					}
				}
			}
		}

		System.out.println("Position not found.");
	}
	public static void main(Scanner sc) {

		Hierarchy h = new Hierarchy();

		//Scanner sc = new Scanner(System.in);

		System.out.println("Create a club hierarchy!");

		int add = 1;

		do {

			System.out.println("What do you want to do?:\n1)Display club hierarchy\n2)Add member\n3)Delete member\n4)Exit");

			int choice = sc.nextInt();

			sc.nextLine();

			switch(choice) {

			case 1:

				h.displayDF(h.root);

				break;

			case 2:

				System.out.println("Enter position of member:");

				String pos = sc.nextLine();

				System.out.println("Enter Name of member:");

				String name = sc.nextLine();

				h.add(new Node1(name,pos));

				System.out.println("Member added!");

				break;

			case 3:

				h.delete(h.root);

				break;

			case 4:

				System.out.println("Exiting!");

				add=0;

			}

		}while(add==1);

	}


}