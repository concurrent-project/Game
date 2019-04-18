package project.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

import project.entities.Boundary;
import project.entities.Item;
import project.entities.Player;
import project.entities.Projectile;



public class GameView extends JPanel {
	static Lock lock = new ReentrantLock();
	
	public static int fps = 0;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		drawBackground(g2);
		
		drawWall(g2);
		
		drawItems(g2);
		
		drawPlayers(g2);
		
		drawProjectiles(g2);
		
		drawPlayerHealthAndAmmo(g2);
		
		drawHealthAndAmmo(g2);
		
	}
	
	private void drawItems(Graphics2D g2) {
	
		for(Item item : Game_Main.map.items){
			g2.setColor(item.color);
			g2.drawImage(item.image, item.cPos.x, item.cPos.y, this);
		}
		
	}

	public void drawHealthAndAmmo(Graphics2D g2) {
		for(Player player : Game_Main.players) {
			if(player.showHealthAndAmmo) {
				g2.setColor(new Color(77, 255, 136, 0));
				g2.fillRect(player.cPos.x-50, player.cPos.y-40, 110, 35);
				
				int p_health = 100 * player.health / player.totalHealth;
				
				int p_ammo = 100 * player.ammo.amount / player.ammo.total;
				
				//Health
				g2.setColor(new Color(255, 0, 0, 127));
				g2.fillRect(player.cPos.x-50 + 5, player.cPos.y-40 + 5, p_health, 10);
				g2.setColor(Color.WHITE);
				g2.drawRect(player.cPos.x-50 + 5, player.cPos.y-40 + 5, 100, 10);
				
				//Ammo
				g2.setColor(new Color(255, 255, 0, 127));
				g2.fillRect(player.cPos.x-50 + 5, player.cPos.y-40 + 15, p_ammo, 10);
				g2.setColor(Color.WHITE);
				g2.drawRect(player.cPos.x-50 + 5, player.cPos.y-40 + 15, 100, 10);
				
				g2.drawString(player.username, player.cPos.x-50 + 5, player.cPos.y-40);
			}
		}
	}
	
	public void drawWall(Graphics2D g2) {
		
		for(Boundary boundary : Game_Main.map.boundaries){
			g2.setColor(boundary.color);
			g2.fill(boundary);
		}
		
	}

	public void drawBackground(Graphics2D g2) {
		g2.setColor(new Color(135, 206, 250));
		g2.fillRect(0, 0, 700, 700);
	}
	
	public void drawPlayerHealthAndAmmo(Graphics2D g2) {
		
		//HUD
		g2.setColor(new Color(125, 26, 0, 127));
		g2.fillRect(5, 5, 690, 50);
		
		int p_health = 100 * Game_Main.player.health / Game_Main.player.totalHealth;
		
		int p_ammo = 100 * Game_Main.player.ammo.amount / Game_Main.player.ammo.total;
		
		//Health
		g2.setColor(new Color(255, 0, 0, 127));
		g2.fillRect(60, 10, p_health, 10);
		g2.setColor(Color.WHITE);
		g2.drawRect(60, 10, 100, 10);
		g2.drawString("Health: ", 10, 20);
		
		//Ammo
		g2.setColor(new Color(255, 255, 0, 127));
		g2.fillRect(60, 30, p_ammo, 10);
		g2.setColor(Color.WHITE);
		g2.drawRect(60, 30, 100, 10);
		g2.drawString("Ammo: ", 10, 40);
		
		//FPS
		g2.drawString("FPS: " + fps, 640, 20);
		
		
	}
	
	public void drawPlayers(Graphics2D g2) {
		for(Player player : Game_Main.players){
			
			g2.setColor(player.color);
			g2.fillOval(player.cPos.x, player.cPos.y, 10, 10);
			g2.setColor(Color.BLACK);
			g2.drawOval(player.cPos.x, player.cPos.y, 10, 10);
			
		}
	}
	
	public void drawProjectiles(Graphics2D g2) {
		lock.lock();
		try {
			for (Player player : Game_Main.players) {
				for(Projectile projectile : player.liveAmmo){
					g2.setColor(projectile.color);
					g2.fillOval(projectile.cPos.x + 3, projectile.cPos.y + 3, projectile.sizeX, projectile.sizeY);
					g2.setColor(Color.BLACK);
					g2.drawOval(projectile.cPos.x + 3, projectile.cPos.y + 3, projectile.sizeX, projectile.sizeY);
				}
			}
		}
		catch(Exception e){
	
		}
		finally {
			lock.unlock();
		}
	}
	
}