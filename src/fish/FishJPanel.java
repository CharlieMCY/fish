package fish;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FishJPanel extends JPanel implements MouseMotionListener, MouseListener {
	List<Fish> fishes = new ArrayList<Fish>();
	List<Bullet> bullets = new ArrayList<Bullet>();
	List<Coin> coins = new ArrayList<Coin>();
	Image bgImg, pImg, butImgAdd, butImgSub;
	BufferedImage cImg;
	BufferedImage conact[] = new BufferedImage[5];
	BufferedImage sImg;
	BufferedImage scs[] = new BufferedImage[10];
	BufferedImage eImg;
	//定义炮位置
	int cx = 400, cy = 390;
	//定义加减位置
	int ax, ay, sx, sy;
	//定义炮旋转角度
	int theta = 0;
	//定义炮等级
	int grade = 1;
	int flag = 0;
	//定义分数
	int score = 200;
	int energy = 1;
	
	public FishJPanel() {
		bgImg = new ImageIcon("images/bg_03.png").getImage();
		butImgAdd = new ImageIcon("images/cannon_plus.png").getImage();
		butImgSub = new ImageIcon("images/cannon_minus.png").getImage();
		try {
			pImg = ImageIO.read(new File("images/bottom-bar.png"));
			cImg = ImageIO.read(new File("images/cannon" + grade + ".png"));
			sImg = ImageIO.read(new File("images/number_black.png"));
			eImg = ImageIO.read(new File("images/energy-bar.png"));
			ax = cx + cImg.getWidth() / 2 + 30;
			sx = cx + cImg.getWidth() / 2 - 75;
			for (int i = 0; i < this.conact.length; i++) {
				int temp = cImg.getHeight() / conact.length;
				conact[i] = cImg.getSubimage(0, temp * i, cImg.getWidth(), temp);
				ay = cy + 25;
				sy = ay;
			}
			for (int i = 0; i < this.scs.length; i++) {
				int temp = sImg.getHeight() / scs.length;
				int j = scs.length - 1 - i;
				scs[j] = sImg.getSubimage(0, temp * i, sImg.getWidth(), temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public void addFish(boolean isGood) {
		Fish fh = new Fish(isGood);
		fishes.add(fh);
	}
	
	public void addBullet() {
		int bx = cx + cImg.getWidth() / 2;
		int by = cy;
		Bullet bul = new Bullet(bx, by, grade, theta);
		score -= grade;
		if (score <= 0) score = 0;
		bullets.add(bul);
	}
	
	public void drawCon(Graphics2D g) {
		g.rotate(Math.toRadians(theta), cx + conact[0].getWidth(null) / 2, cy + conact[0].getHeight(null) / 2);
		g.drawImage(conact[0], cx, cy - 10, null);
		if (flag == 1) {
			for (int num = 0; num < conact.length; num++) {
				g.drawImage(conact[num], cx, cy - 10, null);
			}
			flag = 0;
		}
		g.rotate(-Math.toRadians(this.theta), cx + conact[0].getWidth(null) / 2, cy + conact[0].getHeight(null) / 2);
	}
	
	int scx = 145, scy = 428;
	public void drawScore(Graphics g) {
		int temp = score, j = 0;
		while (j <= 5) {
			int i = temp % 10;
			g.drawImage(scs[i], scx - (sImg.getWidth(null) + 3) * j, scy, null);
			temp /= 10;
			j++;
		}
	}
	
	public void drawEnergy(Graphics g) {
		BufferedImage ene = eImg.getSubimage(0, 0, eImg.getWidth() * energy / 200, eImg.getHeight());
		g.drawImage(ene, 553, 430, null);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(bgImg, 0, 0, null);
		g.drawImage(pImg, 10, 385, null);
		drawScore(g);
		drawEnergy(g);
		for (int i = 0; i < fishes.size(); i++) {
			Fish fh = fishes.get(i);
			fh.drawFish(g);
		}
		for (int i = 0; i < coins.size(); i++) {
			Coin co = coins.get(i);
			co.drawCoin(g);
		}
		Graphics2D g2 = (Graphics2D) g;
		drawCon(g2);
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bul = bullets.get(i);
			bul.drawBullet(g2, cx, cy, conact[0]);
		}
		g.drawImage(butImgSub, sx, sy, null);
		g.drawImage(butImgAdd, ax, ay, null);
	}
	
	int count = 0;
	int isGet = 0;
	public void startFish() {
		new Thread() {
			public void run() {
				while (true) {
					//1
					count++;
					if (count == Integer.MAX_VALUE) count = 0;
					for (int i = 0; i < fishes.size(); i++) {
						Fish fh = fishes.get(i);
						if (fh.moveFish()) {
							if (!fh.isLife) {
								energy += fh.j;
								if (energy >= 200) energy = 200;
								int cox = fh.fx + fh.fish.getWidth();
								int coy = fh.fy;
								Coin co = new Coin(cox, coy, fh.j);
								coins.add(co);
							}
							fishes.remove(i);
							break;
						}
					}
					for (int i = 0; i < coins.size(); i++) {
						Coin co = coins.get(i);
						if (co.moveCoin()) {
							score += co.numbers * 10;
							coins.remove(i);
							break;
						}
					}
					for (int i = 0; i < bullets.size(); i++) {
						Bullet bul = bullets.get(i);
						if (bul.moveBullet(theta)) {
							bullets.remove(i);
							break;
						}
					}
					if (count % 200 == 0) {
						addFish(false);
					}
					if (count % 500 == 0) {
						if (score < 200)
							score += 10;
					}
					for (int i = 0; i < bullets.size(); i++) {
						Bullet bul = bullets.get(i);
						for (int j = 0; j < fishes.size(); j++) {
							Fish fh = fishes.get(j);
							FishCollision fc = new FishCollision();
							if (fc.collision(bul, fh, isGet)) {
								if (fh.isLife) {
									fh.fishGet(grade);
									isGet = 1;
								}
							}
						}
						if (isGet == 1) {
							bul.bulGet();
							isGet = 0;
							break;
						}
					}
					if (energy == 200) {
						for (int j = 0; j < fishes.size(); j++) {
							Fish fh = fishes.get(j);
							fh.fishGet(grade);
						}
						addFish(true);
						energy = 1;
					}
					//2
					repaint();
					//3
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int mx = arg0.getX() - (cx + cImg.getWidth(null) / 2);
		int my = arg0.getY() - (cy + cImg.getHeight(null) / 2);
		double mz = Math.sqrt(mx * mx + my * my);
		theta = Math.round((float)(Math.asin(mx / mz) / Math.PI * 180));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		if (x >= ax && x <= ax + butImgAdd.getWidth(null) &&
				y >= ay && y <= ay + butImgAdd.getHeight(null)) {
			butImgAdd = new ImageIcon("images/cannon_plus_down.png").getImage();
			grade++;
			if (grade == 8) grade = 1;
			try {
				cImg = ImageIO.read(new File("images/cannon" + grade + ".png"));
				for (int i = 0; i < this.conact.length; i++) {
					int temp = cImg.getHeight() / 5;
					conact[i] = cImg.getSubimage(0, temp * i, cImg.getWidth(), temp);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (x >= sx && x <= sx + butImgSub.getWidth(null) &&
				y >= sy && y <= sy + butImgSub.getHeight(null)) {
			butImgSub = new ImageIcon("images/cannon_minus_down.png").getImage();
			grade--;
			if (grade == 0) grade = 7;
			try {
				cImg = ImageIO.read(new File("images/cannon" + grade + ".png"));
				for (int i = 0; i < this.conact.length; i++) {
					int temp = cImg.getHeight() / 5;
					conact[i] = cImg.getSubimage(0, temp * i, cImg.getWidth(), temp);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else {
			if (score >= grade * 10) {
				flag = 1;
				addBullet();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		if (x >= ax && x <= ax + butImgAdd.getWidth(null) &&
				y >= ay && y <= ay + butImgAdd.getHeight(null)) {
			butImgAdd = new ImageIcon("images/cannon_plus.png").getImage();
		}
		if (x >= sx && x <= sx + butImgSub.getWidth(null) &&
				y >= sy && y <= sy + butImgSub.getHeight(null)) {
			butImgSub = new ImageIcon("images/cannon_minus.png").getImage();
		}
	}
}
