package project.entities;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HealthPack extends Item {

	public int health;
	
	public HealthPack(int x, int y, int health){
		super(x, y);
		
		this.health = health;
		this.color = Color.RED;
		
		
		try {
			this.image = ImageIO.read(new File("project/images/health_pack.bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Rectangle bounds(){
		return new Rectangle(cPos.x, cPos.y, 7, 7);
	}

}
