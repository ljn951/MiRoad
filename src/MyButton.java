import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Transparency;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

// 按压效果按钮类
class MyButton extends JButton {
	String show, in, down;// 一个按钮的3中状态图片路径

	public MyButton(String show, String in, String down) {
		super();// 调用父类构造
		setContentAreaFilled(false);// 不画按钮背景
		setBorder(null);// 不画边框
		this.show = show;
		this.in = in;
		this.down = down;
	}

	public void paintComponent(Graphics g2) {
		Image img1 = new ImageIcon(show).getImage(); // 读取本地图片
		Image img2 = new ImageIcon(in).getImage(); // 读取本地图片
		Image img3 = new ImageIcon(down).getImage(); // 读取本地图片
		MediaTracker mt = new MediaTracker(this);// 为此按钮添加媒体跟踪器
		mt.addImage(img1, 0);// 在跟踪器添加图片，下标为0
		try {
			mt.waitForAll(); // 等待加载
		} catch (Exception e) {
			e.printStackTrace();
		}

		int w = img1.getWidth(this);// 读取图片长度
		int h = img1.getHeight(this);// 读取图片宽度

		GraphicsConfiguration gc = new JFrame().getGraphicsConfiguration(); // 本地图形设备

		Image image1 = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);// 建立透明画布
		Graphics2D g = (Graphics2D) image1.getGraphics(); // 在画布上创建画笔
		// Composite alpha =
		// AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
		// //指定透明度为半透明90%
		// g.setComposite(alpha);
		g.drawImage(img1, 0, 0, this);// 将img1画到透明画布上

		Image image2 = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);// 建立透明画布
		g = (Graphics2D) image2.getGraphics(); // 在画布上创建画笔
		// alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		// 0.6f); //指定透明度为半透明90%
		// g.setComposite(alpha);
		g.drawImage(img2, 0, 0, this);// 将img2画到透明画布上

		Image image3 = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);// 建立透明画布
		g = (Graphics2D) image3.getGraphics(); // 在画布上创建画笔
		// alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		// 0.6f); //指定透明度为半透明90%
		// g.setComposite(alpha);
		g.drawImage(img3, 0, 0, this);// 将img3画到透明画布上

		if (getModel().isArmed())// 按下按钮
		{
			g2.drawImage(image3, 0, 0, this);
		} else {
			if (this.getMousePosition() != null)// 鼠标指针进入按钮范围
			{
				g2.drawImage(image2, 0, 0, this);
			} else// 松开按钮
			{
				g2.drawImage(image1, 0, 0, this);
			}
		}
	}
}
