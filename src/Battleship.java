import Main.Handlers.*;

import java.io.*;
import java.net.*;

import javax.swing.*;

public class Battleship
{
	public static void main(String[] args)
	{
		initializeGui();
		
		String serverName = "";
		int port = 0;
		
		String username = JOptionPane.showInputDialog(new JFrame(), "Enter username: ");
		
		try (Socket conn = new Socket(serverName, port);
				BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream())))
		{
			
			ServerHandler sh = new ServerHandler(conn);
			
			String input = read.readLine();
			
			// Wait for inputs
			while (input != null)
			{
				// Handle inputs, call ServerHandler
				
				input = read.readLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void initializeGui()
	{
		// Initialize gui
	}
	
	// Might want to create functions for altering gui.
	// May also need some global variables to access them, or a gui handler.
	// You decide
}
