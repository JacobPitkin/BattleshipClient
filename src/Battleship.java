import Main.Handlers.*;

import java.io.*;
import java.net.*;

import javax.swing.*;

public class Battleship
{
	public static void main(String[] args)
	{
//		initializeGui();
		
		BattleshipGui gui = new BattleshipGui();
		
//		String serverName = "ec2-18-207-150-67.compute-1.amazonaws.com";
//		int port = 8989;
//		
//		String username = JOptionPane.showInputDialog(new JFrame(), "Enter username: ");
//		System.out.println("Connecting to " + serverName + " on port " + port);
//		
//		try (Socket conn = new Socket(serverName, port);
//				BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream())))
//		{
//			
//			System.out.println("Just connected to " + conn.getRemoteSocketAddress());
//			
//			ServerHandler sh = new ServerHandler(conn);
//			sh.SendMoveMessage(5, 8);
//			
//			String input = read.readLine();
//			
//			while (input != null)
//			{
//				System.out.println(input);
//				
//				input = read.readLine();
//			}
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
	}
	
	private static void initializeGui()
	{
		
	}
}
