import Main.Handlers.*;
import Main.Models.*;

import java.io.*;
import java.net.*;

import javax.swing.*;

public class Battleship
{
	public static void main(String[] args)
	{
//		initializeGui();
		
		String serverName = "ec2-18-207-150-67.compute-1.amazonaws.com";
		int port = 8989;
		
		String username = JOptionPane.showInputDialog(new JFrame(), "Enter username: ");
		System.out.println("Connecting to " + serverName + " on port " + port);
		
		try (Socket conn = new Socket(serverName, port);
				BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream())))
		{
			
			System.out.println("Just connected to " + conn.getRemoteSocketAddress());
			
			ServerHandler sh = new ServerHandler(conn);
			BattleshipGui gui = new BattleshipGui(sh);
			
			sh.SendLoginMessage(username);
			
			String input = read.readLine();
			
			int loop = 0;
			
			while (input != null)
			{
				System.out.println(input);
				Message message = MessageFactory.parse(input);
				
				System.out.println(message.type);
				
				if (loop == 0) {
					sh.SendHitMessage(true);
				} else if (loop == 1) {
					sh.SendMoveMessage(2, 2);
				} else if (loop == 2) {
					sh.SendStartMessage();
				}
				
				input = read.readLine();
				loop++;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
