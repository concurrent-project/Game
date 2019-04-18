package project.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import project.entities.AmmoPack;
import project.entities.Boundary;
import project.entities.BreakableBoundary;
import project.entities.HealthPack;
import project.entities.Item;
import project.entities.Player;
import project.entities.Projectile;
import project.load.Map;
import project.load.MapLoader;
import project.network.Connection;
import project.util.Util;

public class Game_Main {
	
	

	public static Frame window;
	
	public static Player player;
	
	public volatile static ArrayList<Player> players;
	
	public static Map map;
	
	public static int fps;

	public static void main(String[] args) {
		
        String username = JOptionPane.showInputDialog(null, "What will your user name be?");

		player = new Player(username, Color.BLUE, new Connection());
		
		players = new ArrayList<Player>();
		
		players.add(player);
		
		File m = new File("project/maps/testMap.map");
		
		map = MapLoader.load(m);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			TimeUnit.SECONDS.sleep(2);
	    } catch (InterruptedException e) {}
		gameLoop();
		
	}
	
	
	
	
	
	private static void tick() {
		
		for(int i = 0; i < players.size(); i++) {
			
			Player eplayer = players.get(i);

			if(eplayer.getHealth() <= 0){
				
				players.remove(i);
				Game_Main.window.connectionTextArea.append(eplayer.username + " is killed");
				continue;
			}
			
			eplayer.move();
			
			if(eplayer.cPos.x != eplayer.pPos.x || 
			   eplayer.cPos.y != eplayer.pPos.y && 
			   eplayer.username.equals(player.username)){
				if(eplayer.connection == null) eplayer.connection = new Connection();
				eplayer.connection.echoPosition();
			}
			
			ArrayList<Projectile> projs = eplayer.liveAmmo;
			
			for(int c = 0; c < projs.size(); c++){
				Projectile proj = projs.get(c);
				
				if(proj.move()) {
					for(Player hitPlayer : players) {
						if(!proj.owner.username.equals(hitPlayer.username)) {
							if(Util.intersects(proj.bounds(), hitPlayer.bounds())) {
								hitPlayer.takeDamage(proj.damage);
								eplayer.liveAmmo.remove(c);
								break;
							}
						}
					}	
				} else {
					for(int p = 0; p < map.boundaries.size(); p++) {
						Boundary boundary = map.boundaries.get(p);
						if(boundary instanceof BreakableBoundary) {
							BreakableBoundary bound = (BreakableBoundary) boundary;
							if(Util.intersects(new Rectangle(proj.nPos.x, proj.nPos.y, proj.sizeX, proj.sizeY), bound)) {
								bound.takeDamage(proj.damage);
								if(bound.getHealth() <= 0) {
									map.boundaries.remove(p);
								}
							}
						}
					}
				eplayer.liveAmmo.remove(c);
				}
				
			}
			
			
			for(int f = 0 ; f < map.items.size(); f++){
				Item item = map.items.get(f);
				
				if(Util.intersects(eplayer.bounds(), item.bounds())){
					if(item instanceof HealthPack){
						eplayer.addHealth(((HealthPack) item).health);
						map.items.remove(f);
						continue;
					}
					if(item instanceof AmmoPack){
						eplayer.addAmmo(((AmmoPack) item).amount);
						map.items.remove(f);
						continue;
					}
				}
			}
		}
	}
	
	private static void paint() {
		GameView.lock.lock();
		try {
			window.gameView.repaint();
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
		GameView.lock.unlock();
		}
	}
	
	public static void gameLoop() {
		
		long lastLoopTime = System.nanoTime();
		int targetFPS = 100;
		long optimalTime = 1000000000 / targetFPS;   
		int lastFpsTime = 0;

		   while (true) {
			     
			      long now = System.nanoTime();
			      long updateLength = now - lastLoopTime;
			      lastLoopTime = now;
			      
	
			      lastFpsTime += updateLength;
			      fps++;
			      
			      if (lastFpsTime >= 1000000000) {
			    	 GameView.fps = fps;
			         lastFpsTime = 0;
			         fps = 0;
			      }
			      //move players and check projectile
			      tick();
			     
			      // repaint our game according to tick
			      paint();
			      

			      try {
			    	  Thread.sleep(Math.abs((lastLoopTime-System.nanoTime() + optimalTime)/1000000));
			      } catch(Exception e) {
			    	  e.printStackTrace();
			      }
			}
	}
	
	

}
