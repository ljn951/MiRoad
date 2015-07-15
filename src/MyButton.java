import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Transparency;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

// ��ѹЧ����ť��
class MyButton extends JButton {
	String show, in, down;// һ����ť��3��״̬ͼƬ·��

	public MyButton(String show, String in, String down) {
		super();// ���ø��๹��
		setContentAreaFilled(false);// ������ť����
		setBorder(null);// �����߿�
		this.show = show;
		this.in = in;
		this.down = down;
	}

	public void paintComponent(Graphics g2) {
		Image img1 = new ImageIcon(show).getImage(); // ��ȡ����ͼƬ
		Image img2 = new ImageIcon(in).getImage(); // ��ȡ����ͼƬ
		Image img3 = new ImageIcon(down).getImage(); // ��ȡ����ͼƬ
		MediaTracker mt = new MediaTracker(this);// Ϊ�˰�ť���ý�������
		mt.addImage(img1, 0);// �ڸ��������ͼƬ���±�Ϊ0
		try {
			mt.waitForAll(); // �ȴ�����
		} catch (Exception e) {
			e.printStackTrace();
		}

		int w = img1.getWidth(this);// ��ȡͼƬ����
		int h = img1.getHeight(this);// ��ȡͼƬ���

		GraphicsConfiguration gc = new JFrame().getGraphicsConfiguration(); // ����ͼ���豸

		Image image1 = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);// ����͸������
		Graphics2D g = (Graphics2D) image1.getGraphics(); // �ڻ����ϴ�������
		// Composite alpha =
		// AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
		// //ָ��͸����Ϊ��͸��90%
		// g.setComposite(alpha);
		g.drawImage(img1, 0, 0, this);// ��img1����͸��������

		Image image2 = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);// ����͸������
		g = (Graphics2D) image2.getGraphics(); // �ڻ����ϴ�������
		// alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		// 0.6f); //ָ��͸����Ϊ��͸��90%
		// g.setComposite(alpha);
		g.drawImage(img2, 0, 0, this);// ��img2����͸��������

		Image image3 = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);// ����͸������
		g = (Graphics2D) image3.getGraphics(); // �ڻ����ϴ�������
		// alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		// 0.6f); //ָ��͸����Ϊ��͸��90%
		// g.setComposite(alpha);
		g.drawImage(img3, 0, 0, this);// ��img3����͸��������

		if (getModel().isArmed())// ���°�ť
		{
			g2.drawImage(image3, 0, 0, this);
		} else {
			if (this.getMousePosition() != null)// ���ָ����밴ť��Χ
			{
				g2.drawImage(image2, 0, 0, this);
			} else// �ɿ���ť
			{
				g2.drawImage(image1, 0, 0, this);
			}
		}
	}
}
