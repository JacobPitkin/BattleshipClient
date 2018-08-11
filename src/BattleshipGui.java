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
	private DefaultListModel dlm;
	private JTextArea text;
	private ServerHandler sh;
	
	public BattleshipGui(ServerHandler sh)
	{
		this.sh = sh;
		
		setName("Battleship");
		setSize(1400, 800);
		
		enemy = new ArrayList<>();
		player = new ArrayList<>();
		
		for (int i = 0; i < 11; i++) {
			ArrayList<JButton> arr = new ArrayList<>();
			
			for (int j = 0; j < 11; j++) {
				JButton button = new JButton();
				
				if (i == 0) {
					if (j == 0) {
						button.setBackground(Color.black);
						button.setOpaque(true);
						button.setBorderPainted(false);
						button.setEnabled(false);
					} else {
						button.setText(String.valueOf((char)(j+64)));
						button.setEnabled(false);
					}
				} else {
					if (j == 0) {
						button.setText("" + i);
						button.setEnabled(false);
					}
				}
				
				button.setActionCommand(i + "," + j);
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String[] coords = e.getActionCommand().split(",");
//						sh.SendMoveMessage(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
						System.out.println(coords[0] + "," + coords[1]);
						JButton button = enemy.get(Integer.parseInt(coords[0])).get(Integer.parseInt(coords[1]));
						button.setBackground(Color.red);
						button.setOpaque(true);
						button.setBorderPainted(false);
					} 	
					
				});
				
				arr.add(button);
			}
			
			enemy.add(arr);
		}
		
		for (int i = 0; i < 11; i++) {
			ArrayList<JButton> arr = new ArrayList<>();
			
			for (int j = 0; j < 11; j++) {
				JButton button = new JButton();
				
				if (i == 0) {
					if (j == 0) {
						button.setBackground(Color.black);
						button.setOpaque(true);
						button.setBorderPainted(false);
					} else {
						button.setText(String.valueOf((char)(j+64)));
					}
				} else {
					if (j == 0) {
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
		
		JPanel panel = new JPanel(new GridLayout(1, 2));
		JPanel enemyPanel = new JPanel(new GridLayout(11, 11));
		JPanel sidePanel = new JPanel(new GridLayout(2, 1));
		JPanel playerPanel = new JPanel(new GridLayout(11, 11));
		JPanel chatPanel = new JPanel(new GridLayout(2, 1));
		JPanel receivedPanel = new JPanel(new BorderLayout());
		JPanel sendPanel = new JPanel(new GridLayout(2, 1));
		
		enemyPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		playerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		sendPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		for (int i = 0; i < enemy.size(); i++) {
			for (int j = 0; j < enemy.get(i).size(); j++) {
				JButton button = enemy.get(i).get(j);
//				System.out.println("Number: " + ((10 * i) + j) + "\tColor: " + button.getBackground().getRed() + "," + button.getBackground().getGreen() + "," + button.getBackground().getBlue() + "\tText: " + button.getText() + "\tEnabled : " + button.isEnabled());
				enemyPanel.add(button);
			}
		}
		
		for (int i = 0; i < player.size(); i++) {
			for (int j = 0; j < player.get(i).size(); j++) {
				JButton button = player.get(i).get(j);
				playerPanel.add(button);
			}
		}
		
		dlm = new DefaultListModel();
		JList list = new JList(dlm);
		JScrollPane scrollPane = new JScrollPane(list);
		
		scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		text = new JTextArea();
		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dlm.addElement(text.getText());
				text.setText("");
			}
			
		});
		
		receivedPanel.add(scrollPane, BorderLayout.CENTER);
		sendPanel.add(text);
		sendPanel.add(send);
		
		panel.add(enemyPanel);
		sidePanel.add(playerPanel);
		chatPanel.add(receivedPanel);
		chatPanel.add(sendPanel);
		sidePanel.add(chatPanel);
		panel.add(sidePanel);
		setContentPane(panel);
		setVisible(true);
	}
}
