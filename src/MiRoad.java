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
	public JPanel panel;// ���
	private MyButton close, small;// ��С���͹رհ�ť(��Щ��ťΪ��͸��)
	private Point point = new Point(0, 0);// ����ָ��
	private JLabel title;// ������⣬��������
	private JScrollPane topScrollPane = null, bottomScrollPane = null;// ����
	private JScrollPane rightScrollPane = null;
	private JScrollBar rightbar = null,topbar=null;
	private MyButton send = null, remove = null, sendIcon = null,sendFile = null,
			groupsend = null,sendMail = null;
	private MyButton addFriends = null, deleteFriends = null;
	public JTextPane text = null, addText = null;
	private Box friendBox;
	private JLabel[] friendsLabel = null;
	private String[] friendsIcon;
	private boolean[] isClickFriend;// �ж�ĳ�������Ƿ񱻵��
	private boolean[] isOnLine;// �����Ƿ�����
	public int firendsNum = 0;
	int whichclick = -1;// ��¼�Ǹ����Ѵ��ڱ����״̬

	// ��������;�ֺŴ�С;������ʽ;������ɫ;���ֱ�����ɫ
	private MyButton fontName = null, fontSize = null, fontStyle = null,
				fontColor = null, fontBackColor = null;
	private StyledDocument doc = null; // �ǳ���Ҫ����������ʽ�Ϳ�����
	
	
	private String s_fontName = "����",s_fontSize = "22", s_fontStyle = "����",
			s_fontColor = "��ɫ", s_fontBackColor = "��ɫ";

	public String imagePath = "";// ����ͼƬ·��
	public String icon = "";// ����ͷ��·��
	public String name = "";// ��������
	Object[] str_name = { "����", "����", "Dialog", "Gulim" };
	Object[] str_Size = { "22", "30", "40" };
	Object[] str_Style = {"����", "б��", "����", "��б��" };
	Object[] str_Color = {"��ɫ", "��ɫ", "��ɫ", "��ɫ", "��ɫ" };
	Object[] str_BackColor = {"��ɫ", "��ɫ", "����", "����", "����", "����" };

	private JTextPane[] multext = null;
	private StyledDocument[] muldoc = null;

	private Myclient client;

	public void setLookAndFeel() {
		try {// ����Windows�۸�
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel Exception");
			System.exit(0);
		}
	}

	// ���췽��
	public MiRoad(String name, final String imagePath, int width, int height,
			final String icon) {
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

		this.addMouseListener(this);// �������¼�
		this.addMouseMotionListener(this);// �����궯���¼�

		this.imagePath = imagePath;
		this.icon = icon;
		this.name = name;
		setLookAndFeel();
		buildGUI();
		addNoChangeAction();
		//UpdateMyGUI();
	}

	// ���캯�����˽���

	public void buildGUI() {
		fontName = new MyButton("images/font.png","images/font_on.png","images/font.png"); // ��������
		// fontName.setBackground(Color.CYAN);
		fontSize = new MyButton("images/size.png","images/size_on.png","images/size.png"); // �ֺ�
		fontStyle = new MyButton("images/style.png","images/style_on.png","images/style.png"); // ��ʽ
		fontColor = new MyButton("images/color.png","images/color_on.png","images/color.png"); // ��ɫ
		fontBackColor = new MyButton("images/backcolor.png","images/backcolor_on.png","images/backcolor.png"); // ������ɫ
		sendIcon = new MyButton("images/sendpicture.png", "images/sendpicture_on.png", "images/sendpicture.png");
		sendFile = new MyButton("images/sendfile.png", "images/sendfile_on.png", "images/sendfile.png");
		sendMail = new MyButton("images/send_mail.png", "images/send_mail_on.png", "images/send_mail.png");

		panel = new JPanel() {
			// ��д���������
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(imagePath).getImage(), 0, 0, this);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.CENTER_BASELINE, 16));
				// g.drawString("x", this.getWidth() - 25, 14);
				// g.drawString("_", this.getWidth() - 60, 10);

				if (icon != null)// ���Ҫ���ñ�����ͼ��
				{
					g.drawImage(new ImageIcon(icon).getImage(), 5, 5, this);
					// ��������λ��
					title.setBounds(280, 10, 100, 20);
				}
			}

			// ��д���߿򷽷�
			public void paintBorder(Graphics g) {
				// �ޱ߿�
			}
		};

		close = new MyButton("images/close_normal.png", "images/close_press.png", "images/close_normal.png");
		small = new MyButton("images/small_normal.png", "images/small_press.png", "images/small_normal.png");
		close.addActionListener(this);
		small.addActionListener(this);

		// �����������
		text = new JTextPane();
		text.setBorder(null);
		text.setEditable(false); // ����¼��
		text.setOpaque(false);
		doc = text.getStyledDocument(); // ���JTextPane��Document

		topScrollPane = new JScrollPane();
		topScrollPane.getViewport().add(text);

		topScrollPane.setBorder(null);
		
		//�������仰��͸���Ĺؼ�,�������ݱ仯ʱ���������bug
		//���ǿ��Լ������ı仯����update���漴��
		topScrollPane.setBackground(new Color(255, 255, 255, 0));
		topScrollPane.getViewport().setOpaque(false);
		addText = new JTextPane();
		addText.setBorder(null);

		// ���������������
		bottomScrollPane = new JScrollPane(addText);
		bottomScrollPane.setBorder(null);
		addText.setForeground(Color.WHITE);
		addText.setOpaque(false);

		
		
		bottomScrollPane.setBackground(new Color(255, 255, 255, 0));
		bottomScrollPane.getViewport().setOpaque(false);

		// ���ùرպ���С������
		panel.setLayout(null);
		panel.add(close);
		close.setBounds(this.getWidth() - 50, 1, 48, 48);
		panel.add(small);
		small.setBounds(this.getWidth() - 95, 1, 48, 48);

		// �趨��������������ʾ����λ��
		panel.add(topScrollPane);
		topScrollPane.setBounds(2, 70, 500, 380);
		panel.add(bottomScrollPane);
		bottomScrollPane.setBounds(2, 480, 500, 100);

		// ���ú����б�

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

		// ������հ�ť
		send = new MyButton("images/send_normal.png", "images/send_press.png", "images/send_normal.png");
		remove = new MyButton("images/remove_normal.png", "images/remove_press.png", "images/remove_normal.png");
		groupsend = new MyButton("images/groupsend_normal.png", "images/groupsend_press.png", "images/groupsend_normal.png");

		panel.add(groupsend);
		groupsend.setBounds(300, 585, 60, 28);
		
		panel.add(send);
		send.setBounds(370, 585, 60, 28);

		panel.add(remove);
		remove.setBounds(440, 585, 60, 28);

		// ���ɾ������
		addFriends = new MyButton("images/addFriend_normal.png",
				"images/addFriend_press.png", "images/addFriend_up.png");
		deleteFriends = new MyButton("images/deleteFriend_normal.png",
				"images/deleteFriend_press.png", "images/deleteFriend_up.png");
		panel.add(addFriends);
		panel.add(deleteFriends);
		addFriends.setBounds(550, 480, 111, 45);
		deleteFriends.setBounds(550, 540, 111, 45);

		/*
		// �м乤����
		Box box_1 = Box.createHorizontalBox(); // ��ṹ
		// ��ʼ�����������������
		box_1.add(fontName); // �������
		box_1.add(Box.createHorizontalStrut(0)); // ���
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
		panel.add(title);// ��ӱ���
		// title.setBounds(10,5,100,15);
		this.add(panel);// ���������ӵ�������
	}

	public void Tips(String tips){
			JOptionPane.showMessageDialog(panel, tips,
					"MiRoad", JOptionPane.WARNING_MESSAGE);
	}
	
	public int isReceive(String fileName,String friendName){
		return JOptionPane.showConfirmDialog(panel, "�Ƿ�������Ժ���:"+friendName+"���ļ�\n"+fileName, "MiRoad", JOptionPane.YES_NO_OPTION);
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
		
		send.addActionListener(new ActionListener() { // �������ֵ��¼�
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
				JFileChooser jfilechooser = new JFileChooser(); // �����ļ�
				int returnVal = jfilechooser.showDialog(panel,
						"ѡ���͵��ļ�");// ���ļ��Ի���
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						// �õ�ѡ����ļ��������ݸ��ļ������ദ��
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
		
		
		
		sendIcon.addActionListener(new ActionListener() { // ����ͼƬ�¼�
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfilechooser = new JFileChooser(); // �����ļ�
				int returnVal = jfilechooser.showDialog(panel,
						"ѡ���͵�ͼƬ");// ���ļ��Ի���
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						// �õ�ѡ����ļ��������ݸ��ļ������ദ��
						Constant.action_type = 2;
						Constant.file_path = jfilechooser.getSelectedFile().getPath();
						client.Getip(findClickName());
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//insertIcon(f.getSelectedFile().getPath()); // ����ͼƬ
			}
		});
		
		sendMail.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MiMail MM = new MiMail("images/11.jpg","images/MiMailBackGround.png","MiMail",400,500);
				MM.setVisible(true);
			}
			
		});
		
		remove.addActionListener(new ActionListener() { // ����¼�
			public void actionPerformed(ActionEvent e) {
				addText.setText("");
			}
		});

		addFriends.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Constant.addFriendName = JOptionPane.showInputDialog(panel,
							"��������ҵĺ�����");
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

		groupsend.addActionListener(new ActionListener() { // Ⱥ���¼�
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
						// ��ʾ�ڱ���
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"hh:mm:ss");
						String dateStr = sdf.format(calendar.getTime());
						sendMes(addText.getText(), dateStr, "images/11.jpg", null,
								null, 2, null);
						addText.setText("");
					}
				});

		//��ֹ�����б����϶�bar�����ֵı����仯
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
				int index = JOptionPane.showOptionDialog(panel, "��ѡ������",
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
				int index = JOptionPane.showOptionDialog(panel, "��ѡ�������С",
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
				int index = JOptionPane.showOptionDialog(panel, "��ѡ��������ʽ",
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
				int index = JOptionPane.showOptionDialog(panel, "��ѡ��������ɫ",
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
				int index = JOptionPane.showOptionDialog(panel, "��ѡ���������ֵ�ɫ",
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
		// �����ɫ
		for (int i = 0; i < firendsNum; i++) {
			final int me = i;
			friendsLabel[i].addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1 && (!isClickFriend[me])) {
						
						// ��Label��ɫ������ͷ�񣬺�������
						friendsLabel[me].setOpaque(true);
						friendsLabel[me].setBackground(Color.CYAN);
						isClickFriend[me] = true;

						// ���øú��ѵ�ͼƬ�����ֵ�������
						setIconImage(Toolkit.getDefaultToolkit().createImage(
								friendsIcon[me]));
						setTitle(friendsLabel[me].getText());

						// ��ԭ��������ĺ��ѻָ�����
						if (whichclick != -1) {
							panel.updateUI();
							friendsLabel[whichclick].setOpaque(false);
							isClickFriend[whichclick] = false;
							//friendsLabel[whichclick].setBackground(new Color(
							//		240, 240, 240));// ԭ����ɫ
						}
						whichclick = me;
						panel.updateUI();
					} else if (e.getClickCount() == 1 && (isClickFriend[me])) {
						// ��Label��ɫ
						panel.updateUI();
						friendsLabel[me].setOpaque(false);
						isClickFriend[me] = false;
						whichclick = -1;
						//friendsLabel[me]
							//	.setBackground(new Color(240, 240, 240));// ԭ����ɫ
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

			// ���øú��ѵ�ͼƬ�����ֵ�������
			setIconImage(Toolkit.getDefaultToolkit().createImage(
					friendsIcon[clickIndex]));
			setTitle(friendsLabel[clickIndex].getText());
		}
		panel.updateUI();
	}

	// ����ͼƬ
	public void insertIcon(String path) {
		text.setCaretPosition(doc.getLength()); // ���ò���λ��
		ImageProcess IMP = new ImageProcess();
		Image showImage = IMP.doScale(new ImageIcon(path).getImage(), 300, 300);
		text.insertIcon(new ImageIcon(showImage)); // ����ͼƬ
		text.setCaretPosition(doc.getLength());
	}

	// ���ı�����JTextPane
	public void insert(FontAttrib attrib) {
		try { // �����ı�
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
		} else {// ��ʾ�Է���ͼƬ
			insertIcon(ImagePath);
			if (ActionType == 2) {//��ʾ���ѷ�����ͼƬ
				insert(getFontAttrib("From:" + friend_name + "\n", 18));
			}
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			String dateStr = sdf.format(calendar.getTime());
			insert(getFontAttrib(dateStr + "\n", 18));
			insertIcon(path);
			insert(getFontAttrib("\n", 0)); // ���������Ի���
			text.setCaretPosition(doc.getLength());
		}
	}
	
	
	public void sendMes(String mes, String data, String ImagePath, String ip,
			String port, int whichtype, String friendName) {
		// ����ͷ��
		insertIcon(ImagePath);
		// �ڴ˴��������ָ��Է�
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
		// ������Ϣ����ʾ
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
			friendBox.add(Box.createVerticalStrut(1)); // ���еļ��
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
			friendBox.add(Box.createVerticalStrut(1)); // ���еļ��
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
		// ���������仰��ʱ����bug,�����һ���˱�ɾ���˻��ǻ���ʾ��ʵ�����Ǳ�ɾ���˵�
		this.setExtendedState(JFrame.ICONIFIED);// ��С������
		this.setExtendedState(JFrame.NORMAL);// ��ԭ
		this.firendsNum = this.firendsNum - 1;
		initFriends(this.firendsNum, newfriendsIcon, newfriendsName,
				newisonline);
	}

	// ��ȡ����Ҫ����������
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
		if (temp_style.equals("����")) {
			att.setStyle(FontAttrib.GENERAL);
		} else if (temp_style.equals("����")) {
			att.setStyle(FontAttrib.BOLD);
		} else if (temp_style.equals("б��")) {
			att.setStyle(FontAttrib.ITALIC);
		} else if (temp_style.equals("��б��")) {
			att.setStyle(FontAttrib.BOLD_ITALIC);
		}
		String temp_color =s_fontColor;
		if (temp_color.equals("��ɫ")) {
			att.setColor(new Color(255, 255, 255));
		} else if (temp_color.equals("��ɫ")) {
			att.setColor(new Color(255, 0, 0));
		} else if (temp_color.equals("��ɫ")) {
			att.setColor(new Color(0, 0, 255));
		} else if (temp_color.equals("��ɫ")) {
			att.setColor(new Color(255, 255, 0));
		} else if (temp_color.equals("��ɫ")) {
			att.setColor(new Color(0, 255, 0));
		}
		String temp_backColor = s_fontBackColor;
		if (!temp_backColor.equals("��ɫ")) {
			if (temp_backColor.equals("��ɫ")) {
				att.setBackColor(new Color(200, 200, 200));
			} else if (temp_backColor.equals("����")) {
				att.setBackColor(new Color(255, 200, 200));
			} else if (temp_backColor.equals("����")) {
				att.setBackColor(new Color(200, 200, 255));
			} else if (temp_backColor.equals("����")) {
				att.setBackColor(new Color(255, 255, 200));
			} else if (temp_backColor.equals("����")) {
				att.setBackColor(new Color(200, 255, 200));
			}
		}
		return att;
	}

	// �����������

	public class FontAttrib {
		public static final int GENERAL = 0; // ����

		public static final int BOLD = 1; // ����

		public static final int ITALIC = 2; // б��

		public static final int BOLD_ITALIC = 3; // ��б��

		private SimpleAttributeSet attrSet = null; // ���Լ�

		private String text = null, name = null; // Ҫ������ı�����������

		private int style = 0, size = 0; // ��ʽ���ֺ�

		private Color color = null, backColor = null; // ������ɫ�ͱ�����ɫ

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
			this.setVisible(false);// ���岻����
			this.dispose();// �ͷŴ�����Դ
			System.exit(0);
		} else if (e.getSource() == small) {
			this.setExtendedState(JFrame.ICONIFIED);// ��С������
			// this.setExtendedState(JFrame.NORMAL);
			// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}

}
