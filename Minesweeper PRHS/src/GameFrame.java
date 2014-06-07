
import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

public class GameFrame extends JFrame implements ActionListener{
	public GameFrame() {

		panel = new JPanel();
		panel.setBackground(SystemColor.scrollbar);
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 11, 167, 223);
		getContentPane().add(panel);
		panel.setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 11, 61, 29);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		lbBombs = new JLabel("10");
		lbBombs.setBounds(4, 0, 57, 29);
		panel_1.add(lbBombs);

		panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(10, 51, 149, 158);
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_3.setBounds(98, 11, 61, 29);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		lbTimes = new JLabel("000");
		lbTimes.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTimes.setBounds(4, 0, 55, 29);
		panel_3.add(lbTimes);

		btnSmiley = new JButton("");
		btnSmiley.setBounds(69, 11, 30, 29);
		btnSmiley.setIcon(imgSmiley);
		btnSmiley.addActionListener(this);
		panel.add(btnSmiley);
		
		timer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				lbTimes.setText(String.valueOf(seconds + 1)); 
				seconds++;
			}
		});
	}
	/**
	 * Updated version. Just for you Isaaac.
	 * Revision: 2
	 */
	private static final long serialVersionUID = 1L;
	public static GameFrame mineFrame;
	public static JPanel panel, panel_1, panel_2, panel_3;
	public static JButton btnSmiley;
	public static JLabel lbTimes, lbBombs; 
	public static Timer timer;
	public static int seconds = 0;
	private static ImageIcon imgSmiley = new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\Smiley.png");
	private static boolean started = false;

	Grid grid;

	public static void Initialize(){
		timer.stop();
		if (started == true){
			panel_2.remove(mineFrame.grid);
			mineFrame.grid = new Grid(mineFrame,9,9,10);
			panel_2.add(mineFrame.grid);
			mineFrame.validate();
		}
		else{
			mineFrame.grid = new Grid(mineFrame,9,9,10);
			panel_2.add(mineFrame.grid);
			mineFrame.validate();
		}
	}

	public static void main(String args[]) {
		mineFrame = new GameFrame();
		mineFrame.setBounds(100, 100, 174, 250);
		mineFrame.setVisible(true);
		mineFrame.getContentPane().setLayout(null);
		mineFrame.setDefaultCloseOperation(3);
		Initialize();
		started = true;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== btnSmiley){
			seconds = 0;
			lbTimes.setText("0");
			Initialize();
		}
	}
}