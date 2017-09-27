package fish;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Coin {
	int cox, coy;
	BufferedImage coImg;
	BufferedImage coins[] = new BufferedImage[10];
	int num = 0;
	int numbers;
	
	public Coin(int cox, int coy, int numbers) {
		super();
		try {
			this.coImg = ImageIO.read(new File("images/coinAni2.png"));
			int temp = this.coImg.getHeight() / coins.length;
			for (int i = 0; i < this.coins.length; i++) {
				this.coins[i] = this.coImg.getSubimage(0, temp * i, this.coImg.getWidth(), temp);
			}
			this.cox = cox - coImg.getWidth();
			this.coy = coy;
			this.numbers = numbers;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void drawCoin(Graphics g) {
		g.drawImage(coins[num], cox, coy, null);
		if (doub == 0) {
			Image xImg = new ImageIcon("images/x.png").getImage();
			g.drawImage(xImg, cox + coImg.getWidth(), coy, null);
			if (numbers >= 10) {
				int temp = numbers;
				int i = temp / 10;
				Image nImg = new ImageIcon("images/" + i + ".png").getImage();
				g.drawImage(nImg, cox + coImg.getWidth() + xImg.getWidth(null), coy, null);
				temp = temp % 10;
				Image nImg2 = new ImageIcon("images/" + temp + ".png").getImage();
				g.drawImage(nImg2, cox + coImg.getWidth() + xImg.getWidth(null) * 2, coy, null);
			} else {
				Image nImg = new ImageIcon("images/" + numbers + ".png").getImage();
				g.drawImage(nImg, cox + coImg.getWidth() + xImg.getWidth(null), coy, null);
			}
		}
	}
	
	int count = 0;
	int doub = 0;
	public boolean moveCoin() {
		if (num <= 9 && doub == 0) {
			count++;
			if (count == Integer.MAX_VALUE) count = 0;
			if (count % 4 == 0) num++;
			if (num == 10) {
				num = 0;
				doub = 1;
			}
		} else {
			coy += 5;
		}
		if (coy >= 500) return true;
		return false;
	}
}
