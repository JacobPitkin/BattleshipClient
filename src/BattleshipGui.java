import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Main.Handlers.ServerHandler;

@SuppressWarnings("serial")
public class BattleshipGui extends JFrame
{
	private ArrayList<ArrayList<JButton>> enemy;
	private ArrayList<ArrayList<JButton>> player;
	private DefaultListModel<String> dlm;
	private JButton randomize, start;
	private JPanel enemyPanel, playerPanel;
	private JTextArea text;
	private ServerHandler sh;
	
	public BattleshipGui(ServerHandler sh)
	{
		this.sh = sh;
		
		setName("Battleship");
		setSize(1400, 800);
		
		JPanel panel = new JPanel(new GridLayout(1, 2));
		enemyPanel = new JPanel(new GridLayout(11, 11));
		JPanel sidePanel = new JPanel(new GridLayout(2, 1));
		playerPanel = new JPanel(new GridLayout(11, 11));
		JPanel wholePlayerPanel = new JPanel(new BorderLayout());
		JPanel chatPanel = new JPanel(new GridLayout(2, 1));
		JPanel receivedPanel = new JPanel(new BorderLayout());
		JPanel sendPanel = new JPanel(new GridLayout(2, 1));
		
		enemyPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		playerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		sendPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		initEnemy();
		initPlayer();
		
		dlm = new DefaultListModel<>();
		JList<String> list = new JList<>(dlm);
		JScrollPane scrollPane = new JScrollPane(list);
		
		scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		text = new JTextArea();
		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener()
		{
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e)
			{
				sh.SendChatMessage(text.getText());
				text.setText("");
			}
			
		});
		
		randomize = new JButton("Randomize Ships");
		randomize.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				randomizeShips();
			}
			
		});
		
		start = new JButton("Start");
		start.addActionListener(new ActionListener()
		{
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e)
			{
				randomize.setEnabled(false);
				start.setEnabled(false);
				sh.SendStartMessage();
				System.out.println("sent start");
			}
			
		});
		
		wholePlayerPanel.add(playerPanel, BorderLayout.CENTER);
		wholePlayerPanel.add(randomize, BorderLayout.NORTH);
		wholePlayerPanel.add(start, BorderLayout.SOUTH);
		
		receivedPanel.add(scrollPane, BorderLayout.CENTER);
		sendPanel.add(text);
		sendPanel.add(send);
		
		panel.add(enemyPanel);
		sidePanel.add(wholePlayerPanel);
		chatPanel.add(receivedPanel);
		chatPanel.add(sendPanel);
		sidePanel.add(chatPanel);
		panel.add(sidePanel);
		setContentPane(panel);
		setVisible(true);
	}
	
	public void addMessage(String message)
	{
		dlm.addElement(message);
	}
	
	private void disableButtons()
	{
		for (ArrayList<JButton> tiles : enemy)
		{
			for (JButton tile : tiles)
			{
				tile.setEnabled(false);
			}
		}
	}
	
	private void enableButtons()
	{
		for (ArrayList<JButton> tiles : enemy)
		{
			for (JButton tile : tiles)
			{
				if (tile.getBackground().equals(Color.yellow) || tile.getBackground().equals(Color.blue)|| tile.getBackground().equals(Color.red) || !tile.getText().equals("")) {
					// Do nothing
				}
				else
				{
					tile.setEnabled(true);
				}
			}
		}
	}
	
	public void hit(boolean hit)
	{
		// Add hit logic
		for (ArrayList<JButton> tiles : enemy)
		{
			for (JButton tile : tiles)
			{
				if (tile.getBackground().equals(Color.yellow))
				{
					if (hit)
					{
						tile.setBackground(Color.red);
						break;
					}
					else
					{
						tile.setBackground(Color.blue);
						break;
					}
				}
			}
		}
	}
	
	private void initEnemy()
	{
		enemy = new ArrayList<>();
		enemyPanel.removeAll();
		
		for (int i = 0; i < 11; i++)
		{
			ArrayList<JButton> arr = new ArrayList<>();
			
			for (int j = 0; j < 11; j++)
			{
				JButton button = new JButton();
				
				if (i == 0)
				{
					if (j == 0)
					{
						button.setBackground(Color.black);
						button.setOpaque(true);
						button.setBorderPainted(false);
						button.setEnabled(false);
					}
					else
					{
						button.setText(String.valueOf((char)(j+64)));
						button.setEnabled(false);
					}
				}
				else
				{
					if (j == 0)
					{
						button.setText("" + i);
						button.setEnabled(false);
					}
				}
				
				button.setActionCommand(i + "," + j);
				button.addActionListener(new ActionListener()
				{

					@SuppressWarnings("static-access")
					@Override
					public void actionPerformed(ActionEvent e)
					{
						String[] coords = e.getActionCommand().split(",");
						JButton button = enemy.get(Integer.parseInt(coords[0])).get(Integer.parseInt(coords[1]));
						button.setBackground(Color.yellow);
						button.setOpaque(true);
						button.setBorderPainted(false);
						disableButtons();
						sh.SendMoveMessage(Integer.parseInt(coords[1]), Integer.parseInt(coords[0]));
					} 	
					
				});
				
				button.setEnabled(false);
				arr.add(button);
			}
			
			enemy.add(arr);
		}
		
		for (int i = 0; i < enemy.size(); i++)
		{
			for (int j = 0; j < enemy.get(i).size(); j++)
			{
				JButton button = enemy.get(i).get(j);
				enemyPanel.add(button);
			}
		}
	}
	
	private void initPlayer()
	{
		player = new ArrayList<>();
		playerPanel.removeAll();
		
		for (int i = 0; i < 11; i++)
		{
			ArrayList<JButton> arr = new ArrayList<>();
			
			for (int j = 0; j < 11; j++)
			{
				JButton button = new JButton();
				
				if (i == 0)
				{
					if (j == 0)
					{
						button.setBackground(Color.black);
						button.setOpaque(true);
						button.setBorderPainted(false);
					}
					else
					{
						button.setText(String.valueOf((char)(j+64)));
					}
				}
				else
				{
					if (j == 0)
					{
						button.setText("" + i);
						button.setBackground(Color.lightGray);
						button.setBorderPainted(false);
					}
				}
				
				button.setEnabled(false);
				arr.add(button);
			}
			
			player.add(arr);
		}
		
		for (int i = 0; i < player.size(); i++)
		{
			for (int j = 0; j < player.get(i).size(); j++)
			{
				JButton button = player.get(i).get(j);
				playerPanel.add(button);
			}
		}
	}
	
	public boolean isHit(int x, int y)
	{
		JButton tile = player.get(y).get(x);
		
		if (tile.getBackground().equals(Color.green))
		{
			tile.setBackground(Color.red);
			enableButtons();
			return true;
		}
		
		tile.setBackground(Color.blue);
		tile.setOpaque(true);
		tile.setBorderPainted(false);
		enableButtons();
		return false;
	}
	
	public boolean isWin()
	{
		int count = 0;
		
		for (ArrayList<JButton> tiles : player)
		{
			for (JButton tile : tiles)
			{
				if (tile.getBackground().equals(Color.red))
				{
					count++;
				}
			}
		}
		
		return count == 17;
	}
	
	private void newGame()
	{
		initEnemy();
		initPlayer();
		randomize.setEnabled(true);
		start.setEnabled(true);
	}
	
	private void randomizeShips()
	{
		initPlayer();
		
		int[] sizes = {5, 4, 3, 3, 2};
		
		for (int i : sizes)
		{
			while (true)
			{
				long time = System.nanoTime();
				
				if (time % 2 == 0)
				{
					// Horizontal
					int x = 1 + (int)(Math.random() * (11 - i));
					int y = 1 + (int)(Math.random() * 11);
					
					if (x == 11)
					{
						x--;
					}
					
					if (y == 11)
					{
						y--;
					}
					
					boolean cont = true;
					
					for (int j = x; j < x + i; j++)
					{
						if (player.get(y).get(j).getBackground().equals(Color.green))
						{
							cont = false;
						}
					}
					
					if (!cont)
					{
						continue;
					}
					
					for (int j = x; j < x + i; j++)
					{
						setPlayer(player.get(y).get(j));
					}
					
					break;
				}
				else
				{
					// Vertical
					int x = 1 + (int)(Math.random() * 11);
					int y = 1 + (int)(Math.random() * (11 - i));
					
					if (x == 11)
					{
						x--;
					}
					
					if (y == 11)
					{
						y--;
					}
					
					boolean cont = true;
					
					for (int j = y; j < y + i; j++)
					{
						if (player.get(j).get(x).getBackground().equals(Color.green))
						{
							cont = false;
						}
					}
					
					if (!cont)
					{
						continue;
					}
					
					for (int j = y; j < y + i; j++)
					{
						setPlayer(player.get(j).get(x));
					}
					
					break;
				}
			}
		}
	}
	
	private void setPlayer(JButton tile)
	{
		tile.setBackground(Color.green);
		tile.setOpaque(true);
		tile.setBorderPainted(false);
	}
	
	public void showWin()
	{
		int reply = JOptionPane.showConfirmDialog(null, "You won! Would you like to play again?", "Win Message", JOptionPane.YES_NO_OPTION);
        
		if (reply == JOptionPane.YES_OPTION)
        {
          newGame();
        }
        else
        {
           JOptionPane.showMessageDialog(null, "GOODBYE");
           System.exit(0);
        }
	}
	
	public void start()
	{
		enableButtons();
	}
}
