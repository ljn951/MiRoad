
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;

//定义该类继承自JFrame，实现ActionListener接口
public class LogMiRoad extends JFrame implements ActionListener,FocusListener
{
	public String iconBackground;
	public String iconTips;
	//创建JPanel对象
	private JPanel jp;
	//创建3个标并加入数组
	//JLabel name = new JLabel("请输入用户名");
	//JLabel password = new JLabel("请输入密码");
	JLabel show = new JLabel("");
	//private JLabel[] jl={name,password,show};
	private MyButton close;//关闭按钮(这些按钮为半透明)
	//创建登陆和重置按扭并加入数组
	JButton login ,regis;
	//JButton reset = new JButton("清空");
	//private JButton[] jb={login};
	
	//创建文本框以及密码框
	private JTextField jName;
	private JPasswordField jPassword;
	private JTextField fakePassword;
	private MiRoad miroad;
	private Myclient client;
	public LogMiRoad(String iconBackground,String iconTips)
	{
		super();// 调用父类构造方法
		//this.setIconImage(Toolkit.getDefaultToolkit().createImage(icon));
		this.setUndecorated(true);// 去掉JFrame标题栏
		this.setSize(250, 440);// 设置大小
		this.setLocationRelativeTo(null);// 在屏幕正中间显示
		this.miroad = miroad;
		// 设置该窗体为圆角窗体
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
			// 重写画组件方法
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.CENTER_BASELINE, 16));
				g.drawImage(new ImageIcon(iconBackground).getImage(), 0, 0, this);
				g.drawImage(new ImageIcon(iconTips).getImage(), 60, 80, this);
				
			}

			// 重写画边框方法
			public void paintBorder(Graphics g) {
				// 无边框
			}
		};
		
				jp.setLayout(null);
					//设置标签和按扭的位置与大小
					//jl[i].setBounds(30,20+40*i,180,20);
					//name.setBounds(50,100,180,20);
					login.setBounds(38,300,200,50);
					regis.setBounds(38,355,200,50);
					//添加标签和按扭到JPanel容器中
					//jp.add(name);
					jp.add(login);
					jp.add(regis);
					//为2个按钮注册动作事件监听器
					login.addActionListener(this);			
					regis.addActionListener(this);

					jp.add(close);
					
					jName = new JTextField("请输入用户名");
					jPassword = new JPasswordField();
					fakePassword = new  JTextField("请输入密码");
					jName.addFocusListener(this);
					jName.addActionListener(this);
					fakePassword.addFocusListener(this);

					
				//设置文本框的位置和大小
				jName.setBounds(50,230,150,30);
				//添加文本框到JPanel容器中
				jp.add(jName);
				//设置密码框的位置和大小
				jPassword.setBounds(50,258,150,30);
				fakePassword.setBounds(50,258,150,30);
				jp.add(fakePassword);
				//添加密码框到JPanel容器中
				//jp.add(jPassword);
				//设置密码框中的回显字符
				jPassword.setEchoChar('*');
				//为密码框注册动作事件监听器
				jPassword.addActionListener(this);		
				//设置用于显示登陆状态的标签大小位置，并将其添加进JPanel容器
				show.setBounds(36,415,300,20);
				jp.add(show);
				//添加JPanel容器到窗体中
				this.add(jp);
				//设置窗体的标题、位置、大小、可见性及关闭动作
				this.setTitle("登陆窗口");
				//this.setBounds(200,200,270,250);
				this.setVisible(true);
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//实现动作监听器接口中的方法actionPerformed
	public void actionPerformed(ActionEvent e)
	{
		//如果事件源为登陆按钮，则判断登录名和密码是否正确
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
			this.setVisible(false);// 窗体不可视
			this.dispose();// 释放窗体资源
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
//					show.setText("登陆成功，欢迎您的到来！");
//					return true;
//				}
//				else
//				{
//					show.setText("对不起，您的用户名或密码错误！");
//					return false;
//				}
		
		
		miroad.setVisible(true);
		this.setVisible(false);// 窗体不可视
		this.dispose();// 释放窗体资源
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
