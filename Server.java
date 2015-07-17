
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Server {
	public static Connection conn;
	   public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException {
		   
		   JFrame jf=new JFrame("server");
			 JPanel panel = new JPanel(new GridLayout());
			 JButton b2 = new JButton("stop");
			 b2.addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent ae) {
					System.out.println("exist server");
					System.exit(0);
				 }
				 });
			
			 panel.add(b2);
			    jf.add(panel);
				jf.setSize(600,600);
				jf.setVisible(true);
			String driver = "com.mysql.jdbc.Driver"; 
			String url = "jdbc:mysql://127.0.0.1:3306/miroad";
			String user = "root";
			String password = "1993520";

				// ������������

				Class.forName(driver);

				// �������ݿ�

			    conn = DriverManager.getConnection(url, user, password);

				if(!conn.isClosed())

				System.out.println("Succeeded connecting to the Database!");
	      //Ϊ�˼���������е��쳣��Ϣ��������
	     int port = 8081;
	      //����һ��ServerSocket�����ڶ˿�8899��
	     ServerSocket server = new ServerSocket(port);
	      while (true) {
	         //server���Խ�������Socket����������server��accept����������ʽ��
	         Socket socket = server.accept();
	         //ÿ���յ�һ��Socket�ͽ���һ���µ��߳���������
	         new Thread(new Task(socket)).start();
	      }
	   }
	   
	   public static void whichtype(int type,String[] info1,String[] outfo1,Socket socket) throws SQLException, NumberFormatException, IOException{
			 switch(type){
			 case 2:check_login(info1[2],info1[3],info1[4],info1[5],outfo1,socket);
			 break;
			 case 3:register(info1[2],info1[3],info1[4],Integer.parseInt(info1[5]),outfo1,socket);
			 break;
			 case 4:HeartBeat(info1[2],outfo1,socket);
			 break;
			 case 5:returnlist(info1[2],outfo1,socket);
			 break;
			 case 6:AddFriends(info1[2],info1[3],outfo1,socket);
			 break;
			 case 7:DeleteFriends(info1[2],info1[3],outfo1,socket);
			 break;
			 case 8:GroupMessage(info1[2],info1[3],outfo1,socket);
			 break;
			 case 9:check_logout(info1[2]);
			 break;
			 case 10:return_ip(info1[2],outfo1,socket);
			 break;
			 }
			 
		 }
		 public static void check_login(String name1,String password1,String ip,String port,String[] outfo1,Socket clientSocket) throws SQLException, NumberFormatException, IOException{
			 Statement statement = conn.createStatement();
			 String sql = "SELECT * FROM users WHERE name ='"+name1+"';";
			 ResultSet rs=statement.executeQuery(sql);
			 //û�и��û���
			 if(!rs.next()){
				 MessageProcesser2.statusMessage("false", "name doesn't exit",outfo1);
				 clientPackets(clientSocket,outfo1);
				 return;
			 }
			 else {
	        String password2   =   rs.getString("password");
	       // System.out.println(password2);
	        //�ҵ�ƥ����˻�
	        if(password2.equals(password1)){
	          
	           if(rs.getString("status").equals("false")){
	           MessageProcesser2.statusMessage("true", "",outfo1);
	           clientPackets(clientSocket,outfo1);
	           int port1 = Integer.parseInt(port);
	           statement.execute("UPDATE users SET status = 'true' WHERE name='"+name1+"';");
	           statement.execute("UPDATE users SET ip = '"+ip+"' WHERE name='"+name1+"';");
	           statement.execute("UPDATE users SET port = '"+port1+"' WHERE name='"+name1+"';");
	           feedbackfriendsname(name1,outfo1,clientSocket);
	           return;
	           } else{
	        	   MessageProcesser2.statusMessage("false", "user has already login",outfo1);
	        	   clientPackets(clientSocket,outfo1);
	        	   return;
	           }
	        } 
			 
		 }
			 //�û������ڵ��������
			 MessageProcesser2.statusMessage("false", "wrong password",outfo1);
			 clientPackets(clientSocket,outfo1);
			 
		 }
		 
		 //�û��ֶ���ѯ���ߺ���
		 public static void returnlist(String name1,String[] outfo1,Socket socket) throws SQLException, NumberFormatException, IOException{
			  feedbackfriendsname(name1,outfo1,socket);
		 }
		 public static void check_logout(String name1) throws SQLException{
			 Statement statement = conn.createStatement();
			 statement.execute("UPDATE  users set status = 'false' WHERE name='"+name1+"';");
			 
		 }
		 public static void returnupdatelist(String name1,String[] outfo1,Socket socket) throws SQLException, NumberFormatException, IOException{
			  updatefriendsname(name1,outfo1,socket);
		 }
		 //�û���һ�ε�½���������������ͺ����б�
		 public static void feedbackfriendsname(String name1,String[] outfo1,Socket clientsocket) throws SQLException, NumberFormatException, IOException{
			outfo1[1] = "LIST";
			Statement statement = conn.createStatement();
			Statement statement1 = conn.createStatement();
			String sql = "SELECT * FROM friends WHERE name ='"+name1+"';";
			ResultSet rs=statement.executeQuery(sql);
			int i=2;
			while(rs.next()){
			String name2 = rs.getString("friend");
			String sql2 = "SELECT * FROM users WHERE name = '"+name2+"'; ";
			ResultSet rs1 = statement1.executeQuery(sql2);
     		while(rs1.next()){
	        outfo1[i++] = rs1.getString("name");
	        outfo1[i++] = rs1.getString("status");
			}
			}
			outfo1[0] = String.valueOf(i);
			clientPackets(clientsocket,outfo1);
		
		 }
		 
		 public static void updatefriendsname(String name1,String[] outfo1,Socket clientsocket) throws SQLException, NumberFormatException, IOException{
			 outfo1[1] = "UPDATE";
				Statement statement = conn.createStatement();
				Statement statement1 = conn.createStatement();
				String sql = "SELECT * FROM friends WHERE name ='"+name1+"';";
				ResultSet rs=statement.executeQuery(sql);
				int i=2;
				while(rs.next()){
				String name2 = rs.getString("friend");
				String sql2 = "SELECT * FROM users WHERE name = '"+name2+"'; ";
				ResultSet rs1 = statement1.executeQuery(sql2);
				while(rs1.next()){
		        outfo1[i++] = rs1.getString("name");
		        outfo1[i++] = rs1.getString("status");
				}
				}
				outfo1[0] = String.valueOf(i);
				clientPackets(clientsocket,outfo1);
		 }
		 
		 //�û�ע��
		 public static void register(String name1,String password1,String ip1,int port1,String[] outfo1,Socket clientSocket) throws SQLException, NumberFormatException, IOException{
			 Statement statement = conn.createStatement();
			 String judge = "true";
			 String sql = "INSERT INTO users(name,password,ip,port,status)VALUES('"+name1+"','"+password1+"','"+ip1+"','"+port1+"','false'); ";
			// statement.execute(sql);
			 try{
			 statement.execute(sql);
			 }
			 catch(Exception e) {
			 judge = "false";
			 }
			 MessageProcesser2.Registerback(judge,outfo1);
			 clientPackets(clientSocket,outfo1);
			 return;
		 }
		 
		 //�յ�������Ϣ�Ժ󽫵�½״̬��Ϊtrue
		 public static void HeartBeat(final String name1,String[] outfo1,Socket socket) throws SQLException, NumberFormatException, IOException{
			 Statement statement = conn.createStatement();
			 statement.execute("UPDATE  users set status = 'true' WHERE name='"+name1+"';");
			 returnupdatelist(name1,outfo1,socket);
			 Timer time = new Timer();
			 TimerTask task = new TimerTask() {   
			    	public void run() {   
			   		 Statement statement = null;
					try {
						statement = conn.createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					 try {
						statement.execute("UPDATE  users set status = 'false' WHERE name='"+name1+"';");
						System.out.println("set false");
					} catch (SQLException e) {
						e.printStackTrace();
				}
			    	
			    	}   
			    	};   
			 long delay = 9000;        
			 time.schedule(task,delay);
		 }
		 //�Ӻ���
		 
		 public static void AddFriends(String name1,String name2,String[] outfo1,Socket clientSocket) throws SQLException, NumberFormatException, IOException{
			 Statement statement = conn.createStatement();
			 Statement statement1 = conn.createStatement();
			 Statement statement2 = conn.createStatement();
			 String sql = "SELECT * FROM friends WHERE name = '"+name1+"' AND friend = '"+name2+"';";
			 String sql1 = "SELECT * FROM users WHERE name = '"+name2+"';";
			 ResultSet rs=statement.executeQuery(sql);
			 if(rs.next()){
				 MessageProcesser2.AddFriendreturn("false", "you have already added this person","nothing",outfo1);
				 clientPackets(clientSocket,outfo1);
				 return;
			 }
			 else{
				 ResultSet rs2 = statement1.executeQuery(sql1);
				 if(rs2.next()){
				 statement1.execute("INSERT INTO friends(name,friend) VALUES('"+name1+"','"+name2+"');");
				 MessageProcesser2.AddFriendreturn("true", "successful!","false",outfo1);
				 clientPackets(clientSocket,outfo1);
				 return;
			 } else{
				 MessageProcesser2.AddFriendreturn("false", "don't have this user","nothing",outfo1);
				 clientPackets(clientSocket,outfo1); 
			     return;
			 }
			 }
		 }
		 //ɾ������
		 public static void DeleteFriends(String name1,String name2,String[] outfo1,Socket clientSocket) throws SQLException, NumberFormatException, IOException{
			 Statement statement = conn.createStatement();
			 statement.execute("DELETE  FROM friends  WHERE name = '"+name1+"' AND friend = '"+name2+"';");
			 MessageProcesser2.DeleteFriendreturn("true",outfo1);
			 clientPackets(clientSocket,outfo1);
			 
		 }
		 //Ⱥ����Ϣ
		 public static void GroupMessage(String name1,String message,String[] outfo1,Socket clientSocket) throws SQLException, NumberFormatException, IOException{
			 MessageProcesser2.CSMessage(name1,message,outfo1);
			 Statement statement = conn.createStatement();
			 String sql = "SELECT * FROM users WHERE status = 'true';";
			 ResultSet rs=statement.executeQuery(sql);
			 while(rs.next()){
				 if(!rs.getString("name").equals(name1)){
			
				 String ip = rs.getString("ip");
				 int port = rs.getInt("port");
				 InetAddress ipaddress=InetAddress.getByName(ip);   
				 Socket threadclientSocket  = new Socket(ipaddress,port);
				 clientPackets(threadclientSocket,outfo1);
		
				 threadclientSocket.close();
				 }
			 }
			 
		 }
		 public static void return_ip(String name1,String[] outfo1,Socket clientSocket) throws SQLException, NumberFormatException, IOException{
			 Statement statement = conn.createStatement();
			 String sql = "SELECT * FROM users WHERE name = '"+name1+"';";
			 ResultSet rs = statement.executeQuery(sql);
			 String ip = null;
			 int port = 0;
			 while(rs.next()){
			  ip = rs.getString("ip");
			  port = rs.getInt("port");
			 }
			
			 MessageProcesser2.Getipreturn(ip,port,outfo1);
			 clientPackets(clientSocket,outfo1);
		 }
	   public static void clientPackets(Socket clientSocket,String[] outfo1) throws NumberFormatException, IOException{
			//ͨ��ip��ַ���� InetAddress����

		//���������
		
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		
		
		//����Ϣ��������
		
		int length_1 = Integer.parseInt(outfo1[0]);
		//System.out.println(length_1);
		outToServer.writeBytes(outfo1[0]+'\n');
		for(int i=1;i<length_1;i++){
			outToServer.writeBytes(outfo1[i]+'\n');
		
		}
		 clientSocket.setSoTimeout(10*1000);
	   }
	  
	  
	  public static void serverPackets(Socket connectionSocket,String[] info1) throws IOException, NumberFormatException, SQLException{	
	    		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	    		info1[0] = inFromClient.readLine();
	    		int length  = Integer.parseInt(info1[0]);
//	    	    System.out.println(length);
	    		for(int i=1;i<length;i++){
	    		 info1[i] = inFromClient.readLine();
	    		   //System.out.println(Constant.info1[i]);
	    		}
	 		  InetAddress ipaddress=InetAddress.getByName(info1[4]);   
	 		  int port = Integer.parseInt(info1[5]);
			  Socket clientSocket  = new Socket(ipaddress,port);
			  String outfo1[] = new String[100];
	    	  whichtype(Integer.parseInt(info1[1]),info1,outfo1,clientSocket);
	    	  
	    	  clientSocket.close();
	    }
	   
	   /**
	    * ��������Socket�����
	   */
	   static class Task implements Runnable {
	 
	      private Socket socket;
	      
	      public Task(Socket socket) {
	         this.socket = socket;
	      }
	      
	      public void run() {
	         try {
	            handleSocket();
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	      }
	      
	      /**
	       * ���ͻ���Socket����ͨ��
	      * @throws Exception
	       */
	      private void handleSocket() throws Exception {
	    	String uimessage[] = new String[100];
	        serverPackets(socket,uimessage);
	      }
	   }
	}
