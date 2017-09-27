package fish;

import java.awt.Image;

import javax.swing.ImageIcon;

public class FishCollision {
	public boolean collision(Bullet bul, Fish fh, int isGet) {
		int x = bul.bx + bul.bImg.getWidth(null) / 2 + (int)((480 - (bul.by + bul.bImg.getHeight(null) / 2)) * Math.sin(Math.toRadians(bul.theta)));
		int y = 480 - (int)((480 - bul.by) * Math.cos(Math.toRadians(bul.theta)));
		//Image wImg = new ImageIcon("images/web" + bul.grade + ".png").getImage();
		if (isGet == 0) {
			if (x <= (fh.fx + fh.fish.getWidth() / 4 * 3) &&
					x >= (fh.fx + fh.fish.getWidth() / 4) &&
					y <= (fh.fy + fh.temp / 4 * 3) &&
					y >= (fh.fy + fh.temp / 4)) {
				return true;
			}
		} else {
			if ((x - bul.grade * 20 / 2) <= (fh.fx + fh.fish.getWidth() /  2) &&
					(x + bul.grade * 20 / 2) >= (fh.fx + fh.fish.getWidth() / 2) &&
					(y - bul.grade * 20 / 2) <= (fh.fy + fh.temp /  2) &&
					(y + bul.grade * 20 / 2) >= (fh.fy + fh.temp / 2)) {
				return true;
			}
		}
		return false;
	}
}
