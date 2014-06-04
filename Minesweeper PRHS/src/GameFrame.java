
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

public class GameFrame extends JFrame{
	public GameFrame() {
		initialize();
	}

	public static GameFrame mineFrame;
	public static JPanel panel, panel_1, panel_2, panel_3;
	public static JButton btnSmiley;
	public static Timer timer;
	public static JLabel lblBombs;
	public Grid grid;

	private static ImageIcon imgSmiley = new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\Smiley.png");
	
	public void startTimer(){
		timer.start();
	}

	public static void main(String[] args) {
		System.out.println("8===D");
		initialize();
	}
	private static void initialize() {
		mineFrame = new GameFrame();
		mineFrame.grid = new Grid(mineFrame/*, 12, 5, 3*/);
		mineFrame.setBounds(100, 100, 200, 277);
		mineFrame.setVisible(true);
		mineFrame.getContentPane().setLayout(null);
		mineFrame.setDefaultCloseOperation(3);
		panel = new JPanel();
		panel.setBackground(SystemColor.scrollbar);
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 11, 167, 223);
		mineFrame.getContentPane().add(panel);
		panel.setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 11, 61, 29);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(10, 51, 149, 155);
		panel.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0};
		gbl_panel_2.rowHeights = new int[]{0};
		gbl_panel_2.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);

		panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_3.setBounds(98, 11, 61, 29);
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		timer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){

			}
		});

		btnSmiley = new JButton("");
		btnSmiley.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mineFrame.grid = new Grid(mineFrame);
				panel_2.add(mineFrame.grid);
			}
		});
		btnSmiley.setBounds(69, 11, 30, 29);
		btnSmiley.setIcon(imgSmiley);
		panel.add(btnSmiley);

		mineFrame.grid = new Grid(mineFrame);
		panel_2.add(mineFrame.grid);

		mineFrame.repaint();

	}
}
