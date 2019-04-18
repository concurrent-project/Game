package project.main;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import project.entities.Player;
import project.entities.Projectile;
import project.util.Util;

import javax.swing.JTextArea;
import javax.swing.JButton;

public class Frame {

	public JFrame frame;
	
	public JPanel gameView;
	public JTextArea connectionTextArea;
	public JButton connectButton;
	
	public static int gameViewHeight;
	public static int gameViewWidth;

	public Frame() {
		initFrame();
		initEvents();
		initPlayerEvents();
	}

	private void initPlayerEvents() {
		
		gameView.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent key) {
				
				Game_Main.player.keyPressed(key);
				
			}
			
			@Override
			public void keyReleased(KeyEvent key) {
				
				Game_Main.player.keyReleased(key);
				
			}
		});
		
		gameView.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mouse) {
				//pew pew...
				Projectile proj = new Projectile(Game_Main.player, Game_Main.player.cPos, mouse.getPoint());
				Game_Main.player.shoot(proj);
				Game_Main.player.connection.echoProjectile(proj);
			}
		});
		
	}

	private void initEvents() {
		
		connectButton.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent mouse) {
				
				Game_Main.player.connection.connect();
				
			}
			
		});
		
		gameView.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseMoved(MouseEvent mouse) {
				for (Player player : Game_Main.players) {
					//If mouse is in the game bounds
					if (Util.inArea(player.bounds(), mouse.getPoint())) {
						player.showHealthAndAmmo = true;
					} else {
						player.showHealthAndAmmo = false;
					}
				}
			}
		});
		
		gameView.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent mouse){
				
				gameView.requestFocus();
				
			}
			
		});
		
		gameView.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				
				Game_Main.player.stop();
				
			}
			
		});
		
		
			
		
		
	}

	private void initFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 937, 760);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		gameView = new GameView();
		gameView.setBounds(10, 10, 700, 700);
		frame.getContentPane().add(gameView);
		
		gameViewHeight = gameView.getHeight();
		gameViewWidth = gameView.getWidth();
		
		
		connectionTextArea = new JTextArea();
		JScrollPane connectionScrollPane = new JScrollPane(connectionTextArea);
		connectionScrollPane.setBounds(720, 144, 191, 566);
		frame.getContentPane().add(connectionScrollPane);
		connectionTextArea.setEditable(false);
		
		connectButton = new JButton("Go Online!");
		connectButton.setBounds(720, 10, 191, 123);
		frame.getContentPane().add(connectButton);
	}
}
