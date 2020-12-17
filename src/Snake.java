import javax.swing.*;
public class Snake extends JFrame {
	Snake()
	{
		add(new Board());
		pack();//set size of the frame
		setLocationRelativeTo(null);//to set frame to middle
		setTitle("Snake Game");//to set title of frame
		setResizable(false);//to stopresizing of panel
		
	}
	public static void main(String args[])
	{
		new Snake().setVisible(true);
	}
}
