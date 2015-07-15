import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.MouseInputListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class MiRoad extends JFrame implements ActionListener,
		MouseInputListener {
	public JPanel panel;// 面板
	private MyButton close, small;// 最小化和关闭按钮(这些按钮为半透明)
	private Point point = new Point(0, 0);// 坐标指针
	private JLabel title;// 窗体标题，本人名字
	private JScrollPane topScrollPane = null, bottomScrollPane = null;// 滚动
	private JScrollPane rightScrollPane = null;
	private JScrollBar rightbar = null,topbar=null;
	private MyButton send = null, remove = null, sendIcon = null,sendFile = null,
			groupsend = null,sendMail = null;
	private MyButton addFriends = null, deleteFriends = null;
	public JTextPane text = null, addText = null;
	private Box friendBox;
	private JLabel[] friendsLabel = null;
	private String[] friendsIcon;
	private boolean[] isClickFriend;// 判断某个好友是否被点击
	private boolean[] isOnLine;// 好友是否在线
	public int firendsNum = 0;
	int whichclick = -1;// 记录那个好友处于被点击状态

	// 字体名称;字号大小;文字样式;文字颜色;文字背景颜色
	private MyButton fontName = null, fontSize = null, fontStyle = null,
				fontColor = null, fontBackColor = null;
	private StyledDocument doc = null; // 非常重要插入文字样式就靠它了
	
	
	private String s_fontName = "宋体",s_fontSize = "22", s_fontStyle = "常规",
			s_fontColor = "白色", s_fontBackColor = "无色";

	public String imagePath = "";// 背景图片路径
	public String icon = "";// 本人头像路径
	public String name = "";// 本人网名
	Object[] str_name = { "宋体", "黑体", "Dialog", "Gulim" };
	Object[] str_Size = { "22", "30", "40" };
	Object[] str_Style = {"常规", "斜体", "粗体", "粗斜体" };
	Object[] str_Color = {"白色", "红色", "蓝色", "黄色", "绿色" };
	Object[] str_BackColor = {"无色", "灰色", "淡红", "淡蓝", "淡黄", "淡绿" };

	private JTextPane[] multext = null;
	private StyledDocument[] muldoc = null;

	private Myclient client;

	public void setLookAndFeel() {
		try {// 设置Windows观感
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel Exception");
			System.exit(0);
		}
	}

	// 构造方法
	public MiRoad(String name, final String imagePath, int width, int height,
			final String icon) {
		super();// 调用父类构造方法
		this.setIconImage(Toolkit.getDefaultToolkit().createImage(icon));
		this.setUndecorated(true);// 去掉JFrame标题栏
		this.setSize(width, height);// 设置大小
		this.setLocationRelativeTo(null);// 在屏幕正中间显示
		// 设置该窗体为圆角窗体
		com.sun.awt.AWTUtilities.setWindowShape(
				this,
				new RoundRectangle2D.Double(0, 0, this.getWidth(), this
						.getHeight(), 15, 15));

		this.addMouseListener(this);// 添加鼠标事件
		this.addMouseMotionListener(this);// 添加鼠标动作事件

		this.imagePath = imagePath;
		this.icon = icon;
		this.name = name;
		setLookAndFeel();
		buildGUI();
		addNoChangeAction();
		//UpdateMyGUI();
	}

	// 构造函数到此结束

	public void buildGUI() {
		fontName = new MyButton("images/font.png","images/font_on.png","images/font.png"); // 字体名称
		// fontName.setBackground(Color.CYAN);
		fontSize = new MyButton("images/size.png","images/size_on.png","images/size.png"); // 字号
		fontStyle = new MyButton("images/style.png","images/style_on.png","images/style.png"); // 样式
		fontColor = new MyButton("images/color.png","images/color_on.png","images/color.png"); // 颜色
		fontBackColor = new MyButton("images/backcolor.png","images/backcolor_on.png","images/backcolor.png"); // 背景颜色
		sendIcon = new MyButton("images/sendpicture.png", "images/sendpicture_on.png", "images/sendpicture.png");
		sendFile = new MyButton("images/sendfile.png", "images/sendfile_on.png", "images/sendfile.png");
		sendMail = new MyButton("images/send_mail.png", "images/send_mail_on.png", "images/send_mail.png");

		panel = new JPanel() {
			// 重写画组件方法
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(imagePath).getImage(), 0, 0, this);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.CENTER_BASELINE, 16));
				// g.drawString("x", this.getWidth() - 25, 14);
				// g.drawString("_", this.getWidth() - 60, 10);

				if (icon != null)// 如果要设置标题栏图标
				{
					g.drawImage(new ImageIcon(icon).getImage(), 5, 5, this);
					// 设置名字位置
					title.setBounds(280, 10, 100, 20);
				}
			}

			// 重写画边框方法
			public void paintBorder(Graphics g) {
				// 无边框
			}
		};

		close = new MyButton("images/close_normal.png", "images/close_press.png", "images/close_normal.png");
		small = new MyButton("images/small_normal.png", "images/small_press.png", "images/small_normal.png");
		close.addActionListener(this);
		small.addActionListener(this);

		// 设置聊天面板
		text = new JTextPane();
		text.setBorder(null);
		text.setEditable(false); // 不可录入
		text.setOpaque(false);
		doc = text.getStyledDocument(); // 获得JTextPane的Document

		topScrollPane = new JScrollPane();
		topScrollPane.getViewport().add(text);

		topScrollPane.setBorder(null);
		
		//以下两句话是透明的关键,但是内容变化时，界面会有bug
		//我们可以监听它的变化，再update界面即可
		topScrollPane.setBackground(new Color(255, 255, 255, 0));
		topScrollPane.getViewport().setOpaque(false);
		addText = new JTextPane();
		addText.setBorder(null);

		// 设置输入文字面板
		bottomScrollPane = new JScrollPane(addText);
		bottomScrollPane.setBorder(null);
		addText.setForeground(Color.WHITE);
		addText.setOpaque(false);

		
		
		bottomScrollPane.setBackground(new Color(255, 255, 255, 0));
		bottomScrollPane.getViewport().setOpaque(false);

		// 设置关闭和最小化窗口
		panel.setLayout(null);
		panel.add(close);
		close.setBounds(this.getWidth() - 50, 1, 48, 48);
		panel.add(small);
		small.setBounds(this.getWidth() - 95, 1, 48, 48);

		// 设定内容输入区和显示区的位置
		panel.add(topScrollPane);
		topScrollPane.setBounds(2, 70, 500, 380);
		panel.add(bottomScrollPane);
		bottomScrollPane.setBounds(2, 480, 500, 100);

		// 设置好友列表

		friendBox = Box.createVerticalBox();
		friendBox.setBounds(3, 0, 300, 800);

		rightScrollPane = new JScrollPane();
		rightScrollPane.setBorder(null);
		//rightScrollPane.setLayout();
		rightScrollPane.getViewport().add(friendBox);
		rightScrollPane.setBackground(new Color(255, 255, 255, 0));
		rightScrollPane.getViewport().setOpaque(false);

		rightbar = rightScrollPane.getVerticalScrollBar();
		topbar = topScrollPane.getVerticalScrollBar();
		
		panel.add(rightScrollPane);
		rightScrollPane.setBounds(502, 70, 200, 380);

		// 发送清空按钮
		send = new MyButton("images/send_normal.png", "images/send_press.png", "images/send_normal.png");
		remove = new MyButton("images/remove_normal.png", "images/remove_press.png", "images/remove_normal.png");
		groupsend = new MyButton("images/groupsend_normal.png", "images/groupsend_press.png", "images/groupsend_normal.png");

		panel.add(groupsend);
		groupsend.setBounds(300, 585, 60, 28);
		
		panel.add(send);
		send.setBounds(370, 585, 60, 28);

		panel.add(remove);
		remove.setBounds(440, 585, 60, 28);

		// 添加删除好友
		addFriends = new MyButton("images/addFriend_normal.png",
				"images/addFriend_press.png", "images/addFriend_up.png");
		deleteFriends = new MyButton("images/deleteFriend_normal.png",
				"images/deleteFriend_press.png", "images/deleteFriend_up.png");
		panel.add(addFriends);
		panel.add(deleteFriends);
		addFriends.setBounds(550, 480, 111, 45);
		deleteFriends.setBounds(550, 540, 111, 45);

		/*
		// 中间工具栏
		Box box_1 = Box.createHorizontalBox(); // 横结构
		// 开始将所需组件加入容器
		box_1.add(fontName); // 加入组件
		box_1.add(Box.createHorizontalStrut(0)); // 间距
		box_1.add(fontStyle);
		box_1.add(Box.createHorizontalStrut(0));
		box_1.add(fontSize);
		box_1.add(Box.createHorizontalStrut(0));
		box_1.add(fontColor);
		box_1.add(Box.createHorizontalStrut(0));
		box_1.add(fontBackColor);
		box_1.add(Box.createHorizontalStrut(0));

		panel.add(box_1);
		box_1.setBounds(1, 450, 400, 30);
		
		*/
		
		panel.add(fontName);
		fontName.setBounds(0, 450, 72, 30);
		
		panel.add(fontSize);
		fontSize.setBounds(72, 450, 72, 30);
		
		panel.add(fontStyle);
		fontStyle.setBounds(144, 450, 72, 30);
		
		panel.add(fontColor);
		fontColor.setBounds(216, 450, 72, 30);
		
		panel.add(fontBackColor);
		fontBackColor.setBounds(288, 450, 72, 30);

		panel.add(sendIcon);
		sendIcon.setBounds(360, 450, 72, 30);
		
		panel.add(sendFile);
		sendFile.setBounds(432, 450, 70, 30);
		
		panel.add(sendMail);
		sendMail.setBounds(560, 2, 30, 30);

		title = new JLabel(name);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("dialog", Font.BOLD, 20));
		panel.add(title);// 添加标题
		// title.setBounds(10,5,100,15);
		this.add(panel);// 将此面板添加到窗体上
	}

	public void Tips(String tips){
			JOptionPane.showMessageDialog(panel, tips,
					"MiRoad", JOptionPane.WARNING_MESSAGE);
	}
	
	public int isReceive(String fileName,String friendName){
		return JOptionPane.showConfirmDialog(panel, "是否接受来自好友:"+friendName+"的文件\n"+fileName, "MiRoad", JOptionPane.YES_NO_OPTION);
	}
	
	public JTextField getTextField(Container c) {
        JTextField textField = null;
        for (int i = 0; i < c.getComponentCount(); i++) {
            Component cnt = c.getComponent(i);
            if (cnt instanceof JTextField) {
                return (JTextField) cnt;
            }
            if (cnt instanceof Container) {
                textField = getTextField((Container) cnt);
                if (textField != null) {
                    return textField;
                }
            }
        }
        return textField;
    }
	
	public void UpdateMyGUI(){
		 Timer time = new Timer();
		 TimerTask task = new TimerTask() {   
		    	public void run() {   
		    		panel.updateUI();
		    		//System.out.println("UpdateGUI");
		    	}
		    	};   
		 long delay = 1000;        
		 time.schedule(task,0,delay);
	}
	public void addNoChangeAction(){
		
		send.addActionListener(new ActionListener() { // 插入文字的事件
			public void actionPerformed(ActionEvent e) {
				try {
					addText.setText(addText.getText() + "\n");
					Constant.action_type = 0;
					client.Getip(findClickName());
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// sendMes(addText.getText(),null,"11.jpg",true);
				//
			}
		});
		
		sendFile.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfilechooser = new JFileChooser(); // 查找文件
				int returnVal = jfilechooser.showDialog(panel,
						"选择发送的文件");// 打开文件对话框
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						// 得到选择的文件名并传递给文件处理类处理
						Constant.action_type = 1;
						Constant.file_path = jfilechooser.getSelectedFile().getPath();
						client.Getip(findClickName());
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		
		
		sendIcon.addActionListener(new ActionListener() { // 插入图片事件
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfilechooser = new JFileChooser(); // 查找文件
				int returnVal = jfilechooser.showDialog(panel,
						"选择发送的图片");// 打开文件对话框
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						// 得到选择的文件名并传递给文件处理类处理
						Constant.action_type = 2;
						Constant.file_path = jfilechooser.getSelectedFile().getPath();
						client.Getip(findClickName());
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//insertIcon(f.getSelectedFile().getPath()); // 插入图片
			}
		});
		
		sendMail.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MiMail MM = new MiMail("images/11.jpg","images/MiMailBackGround.png","MiMail",400,500);
				MM.setVisible(true);
			}
			
		});
		
		remove.addActionListener(new ActionListener() { // 清除事件
			public void actionPerformed(ActionEvent e) {
				addText.setText("");
			}
		});

		addFriends.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Constant.addFriendName = JOptionPane.showInputDialog(panel,
							"请输入查找的好友名");
					client.AddFriend(name, Constant.addFriendName);
					// client.ReceiveMessage();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		deleteFriends.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whichclick = -1;
				try {
					client.DeleteFriend(name, findClickName());
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				panel.updateUI();
			}

		});

		groupsend.addActionListener(new ActionListener() { // 群发事件
					public void actionPerformed(ActionEvent e) {
						try {
							client.GroupMessage(name, Constant.ip,
									addText.getText());
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// 显示在本地
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"hh:mm:ss");
						String dateStr = sdf.format(calendar.getTime());
						sendMes(addText.getText(), dateStr, "images/11.jpg", null,
								null, 2, null);
						addText.setText("");
					}
				});

		//防止好友列表因拖动bar而出现的背景变化
		rightbar.addAdjustmentListener(new AdjustmentListener(){
			public void adjustmentValueChanged(AdjustmentEvent e) {
				panel.updateUI();
			}	
		});
		
		topbar.addAdjustmentListener(new AdjustmentListener(){
			public void adjustmentValueChanged(AdjustmentEvent e) {
				panel.updateUI();
			}	
		});
		text.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				panel.updateUI();
			}

			public void mousePressed(MouseEvent e) {
				panel.updateUI();
			}

			public void mouseReleased(MouseEvent e) {
				panel.updateUI();
			}

			public void mouseEntered(MouseEvent e) {
				panel.updateUI();
			}

			public void mouseExited(MouseEvent e) {
				panel.updateUI();
			}
			
		});
		
		addText.addCaretListener(new CaretListener(){
			public void caretUpdate(CaretEvent e) {
				panel.updateUI();
			}
			
		});
		
		text.addCaretListener(new CaretListener(){
			public void caretUpdate(CaretEvent e) {
				panel.updateUI();
			}
			
		});
		
		addText.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						Constant.action_type = 0;
						client.Getip(findClickName());
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// sendMes(addText.getText(),null,"11.jpg",true);
					// System.out.println("enter key invoke!");
				}
			}

		});
		
		
		fontName.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				int index = JOptionPane.showOptionDialog(panel, "请选择字体",
						"MiRaod", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, str_name,
						str_name[0]);
				if(index !=0){
					s_fontName=(String) str_name[index];
				}
			}			
		});
		
		
		fontSize.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				int index = JOptionPane.showOptionDialog(panel, "请选择字体大小",
						"MiRoad", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, str_Size,
						str_Size[0]);
				if(index !=0){
					s_fontSize=(String) str_Size[index];
				}
			}			
		});
		
		fontStyle.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				int index = JOptionPane.showOptionDialog(panel, "请选择字体样式",
						"MiRoad", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, str_Style,
						str_Style[0]);
				if(index !=0){
					s_fontStyle=(String) str_Style[index];
				}
			}			
		});
		
		fontColor.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				int index = JOptionPane.showOptionDialog(panel, "请选择字体颜色",
						"MiRoad", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, str_Color,
						str_Color[0]);
				if(index !=0){
					s_fontColor=(String) str_Color[index];
				}
			}			
		});
		
		fontBackColor.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				int index = JOptionPane.showOptionDialog(panel, "请选择聊天文字底色",
						"MiRoad", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, str_BackColor,
						str_BackColor[0]);
				if(index !=0){
					s_fontBackColor=(String) str_BackColor[index];
				}
			}			
		});
		
		
	}
	public void addAction() {
		// 点击变色
		for (int i = 0; i < firendsNum; i++) {
			final int me = i;
			friendsLabel[i].addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1 && (!isClickFriend[me])) {
						
						// 该Label变色并设置头像，好友名字
						friendsLabel[me].setOpaque(true);
						friendsLabel[me].setBackground(Color.CYAN);
						isClickFriend[me] = true;

						// 设置该好友的图片和名字到任务栏
						setIconImage(Toolkit.getDefaultToolkit().createImage(
								friendsIcon[me]));
						setTitle(friendsLabel[me].getText());

						// 把原来被点击的好友恢复过来
						if (whichclick != -1) {
							panel.updateUI();
							friendsLabel[whichclick].setOpaque(false);
							isClickFriend[whichclick] = false;
							//friendsLabel[whichclick].setBackground(new Color(
							//		240, 240, 240));// 原背景色
						}
						whichclick = me;
						panel.updateUI();
					} else if (e.getClickCount() == 1 && (isClickFriend[me])) {
						// 该Label变色
						panel.updateUI();
						friendsLabel[me].setOpaque(false);
						isClickFriend[me] = false;
						whichclick = -1;
						//friendsLabel[me]
							//	.setBackground(new Color(240, 240, 240));// 原背景色
					} 
				}

				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	public void removeAction() {
		for (int i = 0; i < this.firendsNum; i++) {
			if (friendsLabel != null) {
				if (friendsLabel[i] != null) {
					if (friendsLabel[i].getMouseListeners() != null) {
						friendsLabel[i].removeMouseListener(friendsLabel[i]
								.getMouseListeners()[0]);
					}
				}
			}
		}
	}

	public void setClient(Myclient client) {
		this.client = client;
	}

	public void updateFriends(int length, String[] Icon, String[] name,
			boolean[] isOnLine) {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (this.friendsLabel != null) {
					if (this.friendsLabel[j] != null) {
						if (name[i].equals(this.friendsLabel[j].getText())) {
							this.friendsLabel[j]
									.setIcon(new ImageIcon(Icon[i]));
							this.isOnLine[j] = isOnLine[i];
							break;
						}
					}
				}
			}
		}
		int clickIndex = findClickIndex();
		if (clickIndex != -1) {
			friendsLabel[clickIndex].setOpaque(true);
			friendsLabel[clickIndex].setBackground(Color.CYAN);
			isClickFriend[clickIndex] = true;

			// 设置该好友的图片和名字到任务栏
			setIconImage(Toolkit.getDefaultToolkit().createImage(
					friendsIcon[clickIndex]));
			setTitle(friendsLabel[clickIndex].getText());
		}
		panel.updateUI();
	}

	// 插入图片
	public void insertIcon(String path) {
		text.setCaretPosition(doc.getLength()); // 设置插入位置
		ImageProcess IMP = new ImageProcess();
		Image showImage = IMP.doScale(new ImageIcon(path).getImage(), 300, 300);
		text.insertIcon(new ImageIcon(showImage)); // 插入图片
		text.setCaretPosition(doc.getLength());
	}

	// 将文本插入JTextPane
	public void insert(FontAttrib attrib) {
		try { // 插入文本
			doc.insertString(doc.getLength(), attrib.getText(),
					attrib.getAttrSet());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void sendFile(String path,String ip,int port){
		File myfile = new File(path);
		try {
			client.SendFileHand(ip, port, name, myfile);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendPicture(String path, String ip, int port, int ActionType,
			String friend_name, String ImagePath) {
		if (ActionType == 0) {
			File myfile = new File(path);
			try {
				client.SendPictureHand(ip, port, name, myfile);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {// 显示对方的图片
			insertIcon(ImagePath);
			if (ActionType == 2) {//显示好友发来的图片
				insert(getFontAttrib("From:" + friend_name + "\n", 18));
			}
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			String dateStr = sdf.format(calendar.getTime());
			insert(getFontAttrib(dateStr + "\n", 18));
			insertIcon(path);
			insert(getFontAttrib("\n", 0)); // 这样做可以换行
			text.setCaretPosition(doc.getLength());
		}
	}
	
	
	public void sendMes(String mes, String data, String ImagePath, String ip,
			String port, int whichtype, String friendName) {
		// 插入头像
		insertIcon(ImagePath);
		// 在此处发送文字给对方
		if (whichtype == 1 || whichtype == 2) {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			String dateStr = sdf.format(calendar.getTime());
			insert(getFontAttrib(dateStr + "\n", 18));
			insert(getFontAttrib(mes + "\n", 0));
			text.setCaretPosition(doc.getLength());
			panel.updateUI();
			Constant.Message = mes;
			if (whichtype == 1) {
				try {
					client.SendMessage(ip, Integer.parseInt(port), name);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				addText.setText("");
			}
		}
		// 接收信息并显示
		else {
			insert(getFontAttrib("From:" + friendName + "\n", 18));
			insert(getFontAttrib(data + "\n", 18));
			insert(getFontAttrib(mes + "\n", 0));
			text.setCaretPosition(doc.getLength());
		}
		panel.updateUI();
	}

	public void setName(String myname) {
		this.name = myname;
		title.setText(myname);
		panel.updateUI();
	}

	public void initFriends(int friendsNum, String[] friendsIcon,
			String[] friendsName, boolean[] isonline) {
		friendBox.removeAll();
		this.firendsNum = friendsNum;
		friendsLabel = new JLabel[friendsNum];
		isClickFriend = new boolean[friendsNum];
		this.isOnLine = new boolean[friendsNum];
		this.friendsIcon = new String[friendsNum];
		for (int i = 0; i < friendsNum; i++) {
			friendsLabel[i] = new JLabel();
			this.friendsIcon[i] = friendsIcon[i];
			friendsLabel[i].setIcon(new ImageIcon(friendsIcon[i]));
			friendsLabel[i].setText(friendsName[i]);
			friendsLabel[i].setForeground(Color.WHITE);
			this.isOnLine[i] = isonline[i];
			friendBox.add(friendsLabel[i]);
			friendBox.add(Box.createVerticalStrut(1)); // 两行的间距
		}
		panel.updateUI();
	}

	public void addFriends(int friendsNum, String[] friendsIcon,
			String[] friendsName, boolean[] isonline) {
		JLabel[] newfriendsLabel = null;
		boolean[] newisClickFriend = null;
		String[] newfriendsIcon = null;
		boolean[] newisonline = null;
		if (this.firendsNum != 0) {
			newfriendsLabel = new JLabel[this.firendsNum];
			newisClickFriend = new boolean[this.firendsNum];
			newfriendsIcon = new String[this.firendsNum];
			newisonline = new boolean[this.firendsNum];
			for (int i = 0; i < this.firendsNum; i++) {
				newfriendsLabel[i] = new JLabel();
				newfriendsLabel[i] = friendsLabel[i];
				newfriendsIcon[i] = this.friendsIcon[i];
				newisClickFriend[i] = isClickFriend[i];
				newisonline[i] = this.isOnLine[i];
			}
		}

		friendsLabel = new JLabel[friendsNum + this.firendsNum];
		isClickFriend = new boolean[friendsNum + this.firendsNum];
		this.friendsIcon = new String[friendsNum + this.firendsNum];
		this.isOnLine = new boolean[friendsNum + this.firendsNum];
		for (int i = 0; i < this.firendsNum; i++) {
			friendsLabel[i] = new JLabel();
			friendsLabel[i] = newfriendsLabel[i];
			isClickFriend[i] = newisClickFriend[i];
			this.friendsIcon[i] = newfriendsIcon[i];
			this.isOnLine[i] = newisonline[i];
		}

		for (int i = this.firendsNum; i < friendsNum + this.firendsNum; i++) {
			friendsLabel[i] = new JLabel();
			this.friendsIcon[i] = friendsIcon[i - this.firendsNum];
			friendsLabel[i].setIcon(new ImageIcon(friendsIcon[i
					- this.firendsNum]));
			friendsLabel[i].setText(friendsName[i - this.firendsNum]);
			friendsLabel[i].setForeground(Color.WHITE);
			this.isOnLine[i] = isonline[i - this.firendsNum];
		}

		friendBox.removeAll();
		for (int i = 0; i < friendsNum + this.firendsNum; i++) {
			friendBox.add(friendsLabel[i]);
			friendBox.add(Box.createVerticalStrut(1)); // 两行的间距
		}
		this.firendsNum = friendsNum + this.firendsNum;
		panel.updateUI();
	}

	public String findClickName() {
		for (int i = 0; i < this.firendsNum; i++) {
			if (isClickFriend[i]) {
				return friendsLabel[i].getText();
			}
		}
		return null;
	}

	public int findClickIndex() {
		for (int i = 0; i < this.firendsNum; i++) {
			if (isClickFriend[i]) {
				return i;
			}
		}
		return -1;
	}

	public void deleteFriend() {
		int deleteIndex = 0;
		String[] newfriendsIcon = new String[this.firendsNum - 1];
		String[] newfriendsName = new String[this.firendsNum - 1];
		boolean[] newisonline = new boolean[this.firendsNum - 1];
		for (int i = 0; i < this.firendsNum; i++) {
			if (isClickFriend[i]) {
				continue;
			} else {
				newfriendsIcon[deleteIndex] = friendsIcon[i];
				newfriendsName[deleteIndex] = friendsLabel[i].getText();
				newisonline[deleteIndex] = this.isOnLine[i];
				deleteIndex++;
			}
		}
		friendBox.removeAll();
		// 不加这两句话的时候有bug,即最后一个人被删除了还是会显示但实际上是被删除了的
		this.setExtendedState(JFrame.ICONIFIED);// 最小化窗体
		this.setExtendedState(JFrame.NORMAL);// 复原
		this.firendsNum = this.firendsNum - 1;
		initFriends(this.firendsNum, newfriendsIcon, newfriendsName,
				newisonline);
	}

	// 获取所需要的文字设置
	public FontAttrib getFontAttrib(String message, int size) {
		FontAttrib att = new FontAttrib();
		att.setText(message);
		att.setName(s_fontName);
		if (size != 0) {
			att.setSize(size);
		}else {
			att.setSize(Integer.parseInt(s_fontSize));
		}
		String temp_style =s_fontStyle;
		if (temp_style.equals("常规")) {
			att.setStyle(FontAttrib.GENERAL);
		} else if (temp_style.equals("粗体")) {
			att.setStyle(FontAttrib.BOLD);
		} else if (temp_style.equals("斜体")) {
			att.setStyle(FontAttrib.ITALIC);
		} else if (temp_style.equals("粗斜体")) {
			att.setStyle(FontAttrib.BOLD_ITALIC);
		}
		String temp_color =s_fontColor;
		if (temp_color.equals("白色")) {
			att.setColor(new Color(255, 255, 255));
		} else if (temp_color.equals("红色")) {
			att.setColor(new Color(255, 0, 0));
		} else if (temp_color.equals("蓝色")) {
			att.setColor(new Color(0, 0, 255));
		} else if (temp_color.equals("黄色")) {
			att.setColor(new Color(255, 255, 0));
		} else if (temp_color.equals("绿色")) {
			att.setColor(new Color(0, 255, 0));
		}
		String temp_backColor = s_fontBackColor;
		if (!temp_backColor.equals("无色")) {
			if (temp_backColor.equals("灰色")) {
				att.setBackColor(new Color(200, 200, 200));
			} else if (temp_backColor.equals("淡红")) {
				att.setBackColor(new Color(255, 200, 200));
			} else if (temp_backColor.equals("淡蓝")) {
				att.setBackColor(new Color(200, 200, 255));
			} else if (temp_backColor.equals("淡黄")) {
				att.setBackColor(new Color(255, 255, 200));
			} else if (temp_backColor.equals("淡绿")) {
				att.setBackColor(new Color(200, 255, 200));
			}
		}
		return att;
	}

	// 字体的属性类

	public class FontAttrib {
		public static final int GENERAL = 0; // 常规

		public static final int BOLD = 1; // 粗体

		public static final int ITALIC = 2; // 斜体

		public static final int BOLD_ITALIC = 3; // 粗斜体

		private SimpleAttributeSet attrSet = null; // 属性集

		private String text = null, name = null; // 要输入的文本和字体名称

		private int style = 0, size = 0; // 样式和字号

		private Color color = null, backColor = null; // 文字颜色和背景颜色

		public FontAttrib() {
		}

		public SimpleAttributeSet getAttrSet() {
			attrSet = new SimpleAttributeSet();
			if (name != null)
				StyleConstants.setFontFamily(attrSet, name);
			if (style == FontAttrib.GENERAL) {
				StyleConstants.setBold(attrSet, false);
				StyleConstants.setItalic(attrSet, false);
			} else if (style == FontAttrib.BOLD) {
				StyleConstants.setBold(attrSet, true);
				StyleConstants.setItalic(attrSet, false);
			} else if (style == FontAttrib.ITALIC) {
				StyleConstants.setBold(attrSet, false);
				StyleConstants.setItalic(attrSet, true);
			} else if (style == FontAttrib.BOLD_ITALIC) {
				StyleConstants.setBold(attrSet, true);
				StyleConstants.setItalic(attrSet, true);
			}
			StyleConstants.setFontSize(attrSet, size);
			if (color != null)
				StyleConstants.setForeground(attrSet, color);
			if (backColor != null)
				StyleConstants.setBackground(attrSet, backColor);
			return attrSet;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public void setBackColor(Color backColor) {
			this.backColor = backColor;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public void setStyle(int style) {
			this.style = style;
		}
	}

	// 重写鼠标方法
	// 为关闭最小化按钮添加的监听
	public void mousePressed(MouseEvent e)// 按下鼠标事件
	{
		point = e.getPoint();// 获取当前鼠标坐标
	}

	public void mouseDragged(MouseEvent e)// 拖动鼠标事件
	{
		Point newPoint = e.getPoint();// 获取新坐标
		this.setLocation(this.getX() + (newPoint.x - point.x), this.getY()
				+ (newPoint.y - point.y)); // 设置新位置
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

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == close) {
			try {
				client.Logout(name);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.setVisible(false);// 窗体不可视
			this.dispose();// 释放窗体资源
			System.exit(0);
		} else if (e.getSource() == small) {
			this.setExtendedState(JFrame.ICONIFIED);// 最小化窗体
			// this.setExtendedState(JFrame.NORMAL);
			// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}

}
