package x;

import java.awt.*;
import javax.swing.*;

public class Jframe extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextArea consoleLike;
	
	public Jframe(String URL)
	{
		setLayout(new GridLayout(1,1));
		consoleLike = new TextArea();
		consoleLike.setEditable(false);
		add(consoleLike);
		
		Server server = new Server();
		server.setConsoleLike(consoleLike);
		server.setURL(URL);
		new Thread(server).start();
	}

}
