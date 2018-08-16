import Main.Handlers.*;
import Main.Models.*;

import java.io.*;
import java.net.*;

import javax.swing.*;

public class Battleship
{
	@SuppressWarnings("static-access")
	public static void main(String[] args)
	{
		String serverName = "ec2-18-207-150-67.compute-1.amazonaws.com";
		int port = 8989;
		
		String username = JOptionPane.showInputDialog(new JFrame(), "Enter username: ");
		
		try (Socket conn = new Socket(serverName, port);
				BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream())))
		{
			ServerHandler sh = new ServerHandler(conn);
			BattleshipGui gui = new BattleshipGui(sh);
			
			sh.SendLoginMessage(username);
			
			String input = read.readLine();
			
			while (input != null)
			{
				System.out.println(input);
				Message message = MessageFactory.parse(input);
				
				if (message.type.equals("Chat"))
				{
					System.out.println("Chat message");
					ChatMessage chat = (ChatMessage) message;
					System.out.println(chat.username + ": " + chat.chatMessage);
					gui.addMessage(chat.chatMessage);
				}
				else if (message.type.equals("Hit"))
				{
					System.out.println("received hit");
					HitMessage hitMessage = (HitMessage) message;
					gui.hit(hitMessage.hit);
				}
				else if (message.type.equals("Ignore"))
				{
					// Ignore this message
				}
				else if (message.type.equals("Move"))
				{
					System.out.println("reeived move");
					MoveMessage moveMessage = (MoveMessage) message;
					boolean hit = gui.isHit(moveMessage.xCoordinate, moveMessage.yCoordinate);
					
					if (hit)
					{
						if (gui.isWin())
						{
							sh.WinMessage();
							gui.showLose();
						}
						else
						{
							sh.SendHitMessage(hit);
						}
					}
					else
					{
						sh.SendHitMessage(hit);
					}
				}
				else if (message.type.equals("Start"))
				{
					System.out.println("received start");
					gui.start();
				}
				else if (message.type.equals("Win"))
				{
					System.out.println("received win");
					gui.showWin();
				}
				
				input = read.readLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
