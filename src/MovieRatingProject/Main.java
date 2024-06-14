package MovieRatingProject;

import javax.swing.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		CRUD manager = new CRUD();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MovieFrame movieFrame = new MovieFrame();
				movieFrame.setVisible(true);
			}
		});
		System.out.println("Enter \"print\" or \"rate\" or \"sort name\" or \"sort rate\" or \"find genre\" or \"add\" or \"delete\" or \"save\" or \"exit\"");
		manager.loadText();
		while(true) {
			System.out.print("\nEnter action : ");
			String input = keyboard.nextLine();
			input = input.replace(" ", "");
			if(input.equalsIgnoreCase("exit")) {
				System.out.println("bye!");
				break;
			}
			else if (input.equalsIgnoreCase("print")) {
				manager.printMovie();
			}
			else if (input.equalsIgnoreCase("rate")) {
				manager.addRate();
			}
			else if (input.equalsIgnoreCase("sortname")) {
				manager.sortName();
			}
			else if (input.equalsIgnoreCase("sortrate")) {
				manager.sortRate();
			}
			else if (input.equalsIgnoreCase("findgenre")) {
				manager.findGenre();
			}
			else if (input.equalsIgnoreCase("delete")) {
				manager.deleteMovie();
			}
			else if (input.equalsIgnoreCase("add")) {
				manager.addMovie();
			}else if(input.equalsIgnoreCase("save")){
				manager.save();
			}
			else System.out.println("::not a vaild function. try again::");
		}
	}
}