import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BattleSnake extends JFrame {

	/**
	 * by Tihhoo(Ken Chen)
	 */
	private static final long serialVersionUID = -2608176897484310156L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleSnake frame = new BattleSnake();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BattleSnake() {
		setResizable(false);
		setTitle("Battle Snakes!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 550);
		setLocationRelativeTo(null);
		setBackground(Color.BLACK);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel optionPanel = new JPanel();
		optionPanel.setBounds(0, 0, 500, 500);
		optionPanel.setLayout(null);
		optionPanel.setBackground(Color.BLACK);
		contentPane.add(optionPanel);
		
		JPanel messagePanel = new JPanel();
		messagePanel.setBorder(new MatteBorder(1, 0, 0, 0,Color.WHITE));
		messagePanel.setBounds(0, 500, 500, 28);
		messagePanel.setLayout(null);
		messagePanel.setBackground(Color.BLACK);
		contentPane.add(messagePanel);
		
		JLabel lblMessage = new JLabel("");
		lblMessage.setForeground(Color.WHITE);
		lblMessage.setBounds(6, 6, 488, 16);
		messagePanel.add(lblMessage);

		JLabel lblTitle = new JLabel("BATTLE SNAKE");
		lblTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 55));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(50, 80, 400, 100);
		optionPanel.add(lblTitle);
		
		JButton btnSolo = new JButton("Classic Solo");
		btnSolo.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		btnSolo.setBounds(165, 228, 170, 35);
		optionPanel.add(btnSolo);
		
		JButton btnDual = new JButton("PVP Dual");
		btnDual.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		btnDual.setBounds(165, 276, 170, 35);
		optionPanel.add(btnDual);
		
		btnSolo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				optionPanel.setVisible(false);
				optionPanel.setEnabled(false);
				contentPane.remove(optionPanel);
				
				Screen screen = new SoloScreen(lblMessage);
				screen.setBounds(0, 0, 500, 500);
				screen.setBackground(Color.BLACK);
				contentPane.add(screen);
				screen.requestFocus();
			}
		});
		
		btnDual.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				optionPanel.setVisible(false);
				optionPanel.setEnabled(false);
				contentPane.remove(optionPanel);
				
				Screen screen = new DualScreen(lblMessage);
				screen.setBounds(0, 0, 500, 500);
				screen.setBackground(Color.BLACK);
				contentPane.add(screen);
				screen.requestFocus();
			}
		});
	}
}
