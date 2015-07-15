import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.MouseInputListener;


public class MiMail extends JFrame implements ActionListener,
MouseInputListener{

	public JPanel panel;// ���
	private Point point = new Point(0, 0);// ����ָ��
	private MyButton close, small,send,clear;// ��С���͹رհ�ť(��Щ��ťΪ��͸��)
	private String icon;
	private String name;
	private String imagePath;
	private JLabel title;// ������⣬��������
	
	private JTextField myMail,friendMail,subject;
	private JScrollPane scrollPane = null;
	private JTextPane text = null;
	private JPasswordField password;

	public MiMail(String icon,String imagePath,String name,int width,int height){
		super();// ���ø��๹�췽��
		this.setIconImage(Toolkit.getDefaultToolkit().createImage(icon));
		this.setUndecorated(true);// ȥ��JFrame������
		this.setSize(width, height);// ���ô�С
		this.setLocationRelativeTo(null);// ����Ļ���м���ʾ
		// ���øô���ΪԲ�Ǵ���
		com.sun.awt.AWTUtilities.setWindowShape(
				this,
				new RoundRectangle2D.Double(0, 0, this.getWidth(), this
						.getHeight(), 15, 15));
		this.imagePath = imagePath;
		this.icon = icon;
		this.name = name;
		this.addMouseListener(this);// �������¼�
		this.addMouseMotionListener(this);// �����궯���¼�
		buildGUI();
		addAction();
	}
	
	public void buildGUI(){
		panel = new JPanel() {
			// ��д���������
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(imagePath).getImage(), 0, 0, this);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.CENTER_BASELINE, 12));
				g.drawString("�������������:", 10, 70);
				g.drawString("������Է�������:", 10, 100);
				g.drawString("�������ʼ�����:", 10, 130);
				g.drawString("����������������:", 140, 160);

				if (icon != null)// ���Ҫ���ñ�����ͼ��
				{
					g.drawImage(new ImageIcon(icon).getImage(), 5, 5, this);
					// ��������λ��
					g.setFont(new Font("dialog", Font.CENTER_BASELINE, 16));
					title.setBounds(150, 10, 100, 20);
				}
			}

			// ��д���߿򷽷�
			public void paintBorder(Graphics g) {
				// �ޱ߿�
			}
		};

		close = new MyButton("images/close_normal.png", "images/close_press.png", "images/close_normal.png");
		small = new MyButton("images/small_normal.png", "images/small_press.png", "images/small_normal.png");
		send =  new MyButton("images/send_normal.png", "images/send_press.png", "images/send_normal.png");
		clear = new MyButton("images/remove_normal.png", "images/remove_press.png", "images/remove_normal.png");
		
		password = new JPasswordField();
		panel.setLayout(null);
		small.setBounds(320, 1, 33, 33);
		close.setBounds(360, 1, 33, 33);
		send.setBounds(320, 450, 80, 80);
		clear.setBounds(240, 450, 80, 80);
		panel.add(close);
		panel.add(small);
		panel.add(send);
		panel.add(clear);
		
		myMail = new JTextField();
		friendMail = new JTextField();
		subject = new JTextField();
		
		myMail.setBounds(120, 50, 300, 30);
		friendMail.setBounds(120, 80, 300, 30);
		subject.setBounds(120, 110, 300, 30);
		
		myMail.setBorder(null);
		friendMail.setBorder(null);
		subject.setBorder(null);
		
		myMail.setOpaque(false);
		friendMail.setOpaque(false);
		subject.setOpaque(false);
		
		panel.add(myMail);
		panel.add(friendMail);
		panel.add(subject);
		
		text = new JTextPane();
		text.setForeground(Color.WHITE);
		text.setOpaque(false);
		scrollPane = new JScrollPane(text);
		scrollPane.setBorder(null);
		scrollPane.setBackground(new Color(255, 255, 255, 0));
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds(10, 180, 375, 250);
		panel.add(scrollPane);
		
		title = new JLabel(name);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("dialog", Font.BOLD, 20));
		panel.add(title);// ��ӱ���
		// title.setBounds(10,5,100,15);
		this.add(panel);// ���������ӵ�������		
	}
	public void addAction(){
		close.addActionListener(this);
		small.addActionListener(this);
		send.addActionListener(this);
		clear.addActionListener(this);
		text.addCaretListener(new CaretListener(){
			public void caretUpdate(CaretEvent e) {
				panel.updateUI();
			}			
		});
	}
	
	public String getMyMail(){
		return myMail.getText();
	}
	
	public String getFriendMail(){
		return friendMail.getText();
	}
	
	public String getSubject(){
		return subject.getText();
	}
	
	public String getText(){
		return text.getText();
	}
	
	
	
	
	
	// ��д��귽��
	// Ϊ�ر���С����ť��ӵļ���
	public void mousePressed(MouseEvent e)// ��������¼�
	{
		point = e.getPoint();// ��ȡ��ǰ�������
	}

	public void mouseDragged(MouseEvent e)// �϶�����¼�
	{
		Point newPoint = e.getPoint();// ��ȡ������
		this.setLocation(this.getX() + (newPoint.x - point.x), this.getY()
				+ (newPoint.y - point.y)); // ������λ��
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}
    public void sendmimail(SendMail sMail,boolean Auth,String Mymail,String Friendmail,String password,String Subject,String Body){
    	 sMail.setNeedAuth(Auth);
         //������������Ϣ
         sMail.setNamePass(Mymail, password);
         sMail.setSubject(Subject);
         sMail.setBody(Body);
         sMail.setFrom(Mymail);
         sMail.setTo(Friendmail);
         sMail.sendout();             
    }
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == close) {
			this.setVisible(false);// ���岻����
			this.dispose();// �ͷŴ�����Դ
			//System.exit(0);
		} else if (e.getSource() == small) {
			this.setExtendedState(JFrame.ICONIFIED);// ��С������
		}else if(e.getSource() == send){
			JOptionPane.showMessageDialog(null, password, "����������", JOptionPane.PLAIN_MESSAGE);
			panel.updateUI();
			SendMail sMail=new SendMail(this);
			sendmimail(sMail,true,getMyMail(),getFriendMail(),String.valueOf(password.getPassword()),getSubject(),getText());

//			System.out.println(password.getPassword());
		}else if(e.getSource() == clear){
			text.setText("");
		}		
	}

}
