import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

import Main.Handlers.ServerHandler;

@SuppressWarnings("serial")
public class BattleshipGui extends JFrame
{
	private ArrayList<ArrayList<JButton>> buttons;
	private ServerHandler sh;
	
	public BattleshipGui(ServerHandler sh)
	{
		this.sh = sh;
		
		setName("Battleship");
		setSize(1000, 800);
		
		buttons = new ArrayList<>();
		
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
						button.setBackground(Color.lightGray);
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
						JButton button = buttons.get(Integer.parseInt(coords[0])).get(Integer.parseInt(coords[1]));
						button.setBackground(Color.red);
						button.setOpaque(true);
						button.setBorderPainted(false);
					}
					
				});
				
				arr.add(button);
			}
			
			buttons.add(arr);
		}
		
		JPanel panel = new JPanel(new GridLayout(11,11));
		
		for (int i = 0; i < buttons.size(); i++) {
			for (int j = 0; j < buttons.get(i).size(); j++) {
				JButton button = buttons.get(i).get(j);
				System.out.println("Number: " + ((10 * i) + j) + "\tColor: " + button.getBackground().getRed() + "," + button.getBackground().getGreen() + "," + button.getBackground().getBlue() + "\tText: " + button.getText() + "\tEnabled : " + button.isEnabled());
				panel.add(button);
			}
		}
		
		setContentPane(panel);
		setVisible(true);
	}
}
