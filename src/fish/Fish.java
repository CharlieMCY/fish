package fish;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fish {
	//定义一个对象存储鱼
	BufferedImage fish;
	//定义一个数组存储鱼的动作
	BufferedImage fishact[] = new BufferedImage[8];
	BufferedImage fishact2[] = new BufferedImage[10];
	BufferedImage fishact3[] = new BufferedImage[12];
	int fx, fy;
	int temp;
	int speed;
	int j;
	int num;
	int count = 0;
	int flag = 0;
	boolean isLife;
	public Fish(boolean isGood) {
		super();
		try {
			int g = (int)(Math.random() * 100 );
			if (g >= 0 && g < 90) {
				this.j = (int)(Math.random() * 6 ) + 1;
			}
			else if (g >= 90 && g <= 98) {
				this.j = (int)(Math.random() * 4 ) + 7;
			}
			else this.j = 11;
			if (isGood == true) this.j = 12;
			this.fish = ImageIO.read(new File("images/fish" + this.j + ".png"));
			if (this.j <= 5) {
				this.temp = this.fish.getHeight() / fishact.length;
				for (int i = 0; i < this.fishact.length; i++) {
					this.fishact[i] = this.fish.getSubimage(0, this.temp * i, this.fish.getWidth(), this.temp);
				}
				this.fx = -this.fishact[0].getWidth();
				this.fy = (int)(Math.random() * (330));
			} else if (this.j <= 7){
				this.temp = this.fish.getHeight() / fishact2.length;
				for (int i = 0; i < this.fishact2.length; i++) {
					this.fishact2[i] = this.fish.getSubimage(0, this.temp * i, this.fish.getWidth(), this.temp);
				}
				this.fx = -this.fishact2[0].getWidth();
				this.fy = (int)(Math.random() * (250));
			} else {
				this.temp = this.fish.getHeight() / fishact3.length;
				for (int i = 0; i < this.fishact3.length; i++) {
					this.fishact3[i] = this.fish.getSubimage(0, this.temp * i, this.fish.getWidth(), this.temp);
				}
				this.fx = -this.fishact3[0].getWidth();
				this.fy = (int)(Math.random() * (250));
			}
			if (this.j == 11 || this.j == 12) {
				this.speed = 4;
				this.fy = (int)(Math.random() * (150));
			}
			else this.speed = 1;
			this.isLife = true;
			this.num = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void drawFish(Graphics g) {
		if (j <= 5) g.drawImage(fishact[num], fx, fy, null);
		else if (j <= 7) g.drawImage(fishact2[num], fx, fy, null);
		else g.drawImage(fishact3[num], fx, fy, null);
	}
	
	public void fishGet(int grade) {
		int g = (int)(Math.random() * j * 25);
		if (g < grade * 5) {
			isLife = false;
		}
	}
	
	public boolean moveFish() {
		count++;
		if (count == Integer.MAX_VALUE) count = 0;
		if (isLife) {
			if (j == 3 || j == 4 || j == 6 || j == 7 || j == 8 || j == 11 || j == 12)
				fx += speed;
			else if (count % 3 == 0)
				fx += speed;
			if (count % 10 == 0) {
				num++;
			}
			if (num == 4 && j <= 5) num = 0;
			if (num == 5 && j <= 10) num = 0;
			if (num == 8 ) num = 0;
			if (fx >= 900) {
				return true;
			}
		} else {
			if (flag == 0) {
				count = 0;
				if (j <= 5) num = 3;
				else if (j <= 7) num = 5;
				else num = 7;
				flag = 1;
			}
			if (count % 20 == 0) {
				num++;
			}
			if (num == 8 && j <= 5) {
				return true;
			}
			if (num == 10 && j <= 10) {
				return true;
			}
			if (num == 12 ) {
				return true;
			}
		}
		return false;
	}
}
