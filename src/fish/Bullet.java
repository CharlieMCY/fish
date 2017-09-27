package fish;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet {
	int bx, by;
	int grade;
	Image bImg;
	int theta;
	boolean isGet = false;
	public Bullet(int bx, int by, int grade, int theta) {
		super();
		this.grade = grade;
		this.theta = theta;
		try {
			bImg = ImageIO.read(new File("images/bullet" + grade + ".png"));
			this.bx = bx - bImg.getWidth(null) / 2;
			this.by = by - bImg.getHeight(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void drawBullet(Graphics2D g, int cx, int cy, BufferedImage conact) {
		g.rotate(Math.toRadians(this.theta), cx + conact.getWidth(null) / 2, cy + conact.getHeight(null) / 2);
		g.drawImage(bImg, bx, by, null);
		g.rotate(-Math.toRadians(this.theta), cx + conact.getWidth(null) / 2, cy + conact.getHeight(null) / 2);
	}
	
	int count = 0;
	public boolean moveBullet(int theta) {
		if (isGet) {
			count++;
			if (count == 30) return true;
		} else {
			by -= grade;
			if (by <= -200 || bx >= 900 || bx <= -100) {
				return true;
			}
		}
		return false;
	}
	
	public void bulGet() {
		try {
			int tempx = bImg.getWidth(null);
			int tempy = bImg.getHeight(null);
			bImg = ImageIO.read(new File("images/web" + grade + ".png"));
			bx = bx + tempx / 2 - bImg.getWidth(null) / 2;
			by = by + tempy / 2 - bImg.getHeight(null) / 2;
			isGet = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
