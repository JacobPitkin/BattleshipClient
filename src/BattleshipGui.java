import java.awt.Color;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class BattleshipGui extends JFrame
{
	private ArrayList<ArrayList<JButton>> buttons;
	
	public BattleshipGui()
	{
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
				
				arr.add(button);
			}
			
			buttons.add(arr);
		}
		
		for (int i = 0; i < buttons.size(); i++) {
			for (int j = 0; j < buttons.get(i).size(); j++) {
				JButton button = buttons.get(i).get(j);
				System.out.println("Number: " + ((10 * i) + j) + "\tColor: " + button.getBackground().getRed() + "," + button.getBackground().getGreen() + "," + button.getBackground().getBlue() + "\tText: " + button.getText() + "\tEnabled : " + button.isEnabled());
			}
		}
		
		setVisible(true);
	}
}
