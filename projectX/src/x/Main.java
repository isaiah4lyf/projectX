package x;

import javax.swing.JFrame;

public class Main {
	private static String URL = "http://localhost/";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Jframe frame = new Jframe(URL);
		frame.pack();
		frame.setTitle("Project X");
		frame.setSize(800,400);
		frame.setResizable(false);
		frame.setLocation(250,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
