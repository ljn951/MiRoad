
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;

//�������̳���JFrame��ʵ��ActionListener�ӿ�
public class LogMiRoad extends JFrame implements ActionListener,FocusListener
{
	public String iconBackground;
	public String iconTips;
	//����JPanel����
	private JPanel jp;
	//����3���겢��������
	//JLabel name = new JLabel("�������û���");
	//JLabel password = new JLabel("����������");
	JLabel show = new JLabel("");
	//private JLabel[] jl={name,password,show};
	private MyButton close;//�رհ�ť(��Щ��ťΪ��͸��)
	//������½�����ð�Ť����������
	JButton login ,regis;
	//JButton reset = new JButton("���");
	//private JButton[] jb={login};
	
	//�����ı����Լ������
	private JTextField jName;
	private JPasswordField jPassword;
	private JTextField fakePassword;
	private MiRoad miroad;
	private Myclient client;
	public LogMiRoad(String iconBackground,String iconTips)
	{
		super();// ���ø��๹�췽��
		//this.setIconImage(Toolkit.getDefaultToolkit().createImage(icon));
		this.setUndecorated(true);// ȥ��JFrame������
		this.setSize(250, 440);// ���ô�С
		this.setLocationRelativeTo(null);// ����Ļ���м���ʾ
		this.miroad = miroad;
		// ���øô���ΪԲ�Ǵ���
		com.sun.awt.AWTUtilities.setWindowShape(
				this,
				new RoundRectangle2D.Double(0, 0, this.getWidth(), this
						.getHeight(), 15, 15));
		this.iconBackground = iconBackground;
		this.iconTips = iconTips;
		buildGUI();
	}
	
	public void buildGUI(){
		
		
		close = new MyButton("images/close.png", "images/close.png", "images/close.png");
		close.addActionListener(this);
		close.setBounds(180, -10, 80, 80);
		login = new MyButton("images/login_buttonnormal.png", "images/login_buttonpress.png", "images/login_buttonup.png");
		regis = new MyButton("images/regis_buttonnormal.png", "images/regis_buttonpress.png", "images/regis_buttonup.png");

		jp = new JPanel() {
			// ��д���������
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.CENTER_BASELINE, 16));
				g.drawImage(new ImageIcon(iconBackground).getImage(), 0, 0, this);
				g.drawImage(new ImageIcon(iconTips).getImage(), 60, 80, this);
				
			}

			// ��д���߿򷽷�
			public void paintBorder(Graphics g) {
				// �ޱ߿�
			}
		};
		
				jp.setLayout(null);
					//���ñ�ǩ�Ͱ�Ť��λ�����С
					//jl[i].setBounds(30,20+40*i,180,20);
					//name.setBounds(50,100,180,20);
					login.setBounds(38,300,200,50);
					regis.setBounds(38,355,200,50);
					//��ӱ�ǩ�Ͱ�Ť��JPanel������
					//jp.add(name);
					jp.add(login);
					jp.add(regis);
					//Ϊ2����ťע�ᶯ���¼�������
					login.addActionListener(this);			
					regis.addActionListener(this);

					jp.add(close);
					
					jName = new JTextField("�������û���");
					jPassword = new JPasswordField();
					fakePassword = new  JTextField("����������");
					jName.addFocusListener(this);
					jName.addActionListener(this);
					fakePassword.addFocusListener(this);

					
				//�����ı����λ�úʹ�С
				jName.setBounds(50,230,150,30);
				//����ı���JPanel������
				jp.add(jName);
				//����������λ�úʹ�С
				jPassword.setBounds(50,258,150,30);
				fakePassword.setBounds(50,258,150,30);
				jp.add(fakePassword);
				//��������JPanel������
				//jp.add(jPassword);
				//����������еĻ����ַ�
				jPassword.setEchoChar('*');
				//Ϊ�����ע�ᶯ���¼�������
				jPassword.addActionListener(this);		
				//����������ʾ��½״̬�ı�ǩ��Сλ�ã���������ӽ�JPanel����
				show.setBounds(36,415,300,20);
				jp.add(show);
				//���JPanel������������
				this.add(jp);
				//���ô���ı��⡢λ�á���С���ɼ��Լ��رն���
				this.setTitle("��½����");
				//this.setBounds(200,200,270,250);
				this.setVisible(true);
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//ʵ�ֶ����������ӿ��еķ���actionPerformed
	public void actionPerformed(ActionEvent e)
	{
		//����¼�ԴΪ��½��ť�����жϵ�¼���������Ƿ���ȷ
		if(e.getSource() == login)
		{   
			try {
				Constant.name = jName.getText();
				client.Login(jName.getText(),  String.valueOf(jPassword.getPassword()));
				//client.ReceiveMessage();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//canLog();
		}
		else if(e.getSource() == regis){
			try {
				Constant.name = jName.getText();
				client.Register(Constant.name,
						String.valueOf(jPassword.getPassword()));
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == close) {
			this.setVisible(false);// ���岻����
			this.dispose();// �ͷŴ�����Դ
			System.exit(0);
		} 
	}
	
	public void setMiRoad(MiRoad miroad){
		this.miroad = miroad;
	}
	public void setClient(Myclient client){
		this.client = client;
	}

	public void canLog(){
//		if(jName.getText().equals("123")&&
//				   String.valueOf(jPassword.getPassword()).equals("123"))
//				{
//					show.setText("��½�ɹ�����ӭ���ĵ�����");
//					return true;
//				}
//				else
//				{
//					show.setText("�Բ��������û������������");
//					return false;
//				}
		
		
		miroad.setVisible(true);
		this.setVisible(false);// ���岻����
		this.dispose();// �ͷŴ�����Դ
		//System.out.println("Log Done");
	}	

	
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jName){
				jName.setText("");
		}
		else if(e.getSource() ==fakePassword ){
			jp.remove(fakePassword);
			jp.add(jPassword);
			jPassword.requestFocus();
		}
	}

	
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	}
}
