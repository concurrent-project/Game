package project.entities;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class AmmoPack extends Item {
	
	public int amount;
	
	public AmmoPack(int x, int y, int amount){
		super(x, y);
		
		this.amount = amount;
		this.color = Color.GRAY;
		
		
		try {
			this.image = ImageIO.read(new File("project/images/ammo_pack.bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Rectangle bounds(){
		return new Rectangle(cPos.x, cPos.y, 7, 7);
	}

}
