
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JTextField;



public class Myclient extends Thread{
    public String address;
    public static InetAddress ipaddress;
    public static Socket clientSocket;
    public ServerSocket welcomeSocket;
    
    public static MiRoad MR;
    public static LogMiRoad LMR;
    public static int port = 8081;
    public static String friendip;
    public static int friendport;
    public static String hostip = "172.18.32.240";

	Myclient() throws IOException{
		//welcomeSocket = new ServerSocket(Constant.port);
		Constant.ip = InetAddress.getLocalHost().getHostAddress().toString();
		for(int i = 5000;i<9000;i++){
			try{
				welcomeSocket = new ServerSocket(i);
				Constant.port = i;
			  
				break;
			}
			catch(IOException e){
				System.out.println("socket fail!");
			}
		}
	}
	

	public void setMiRoad(MiRoad miroad){
		this.MR = miroad;
	}
	public void setLogMiRoad(LogMiRoad LMR){
		this.LMR = LMR;
	}
	
	//心跳
	public static void HeartBeat(final String name){
	    Timer time = new Timer();
		MessageProcesser.HeartBeatMessage(name,Constant.ip,Constant.port);
	    TimerTask task = new TimerTask() {   
	    	public void run() {   
	        try {
				clientheartPackets(hostip,port);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    	
	    	}   
	    	};   
	 long delay = 10000;        
	 time.schedule(task, 0,delay);
	}
	
	public void SendMessage(String ip,int port,String myname) throws UnknownHostException, IOException{
		//welcomeSocket = new ServerSocket(Constant.port);
		MessageProcesser.MessageMessage(Constant.Message,myname);
		clientPackets(ip,port);
	}
	public static void SendPicture(String ip,int port,File myfile){
		 int length = 0;
	        byte[] sendByte = null;
	        Socket socket = null;
	        DataOutputStream dout = null;
	        FileInputStream fin = null;
	        try {
	          try {
	            socket = new Socket();
	            socket.connect(new InetSocketAddress(ip, port),10 * 1000);
	            dout = new DataOutputStream(socket.getOutputStream());
	            fin = new FileInputStream(myfile);
	            sendByte = new byte[1024];
	            dout.writeUTF(myfile.getName());
	            while((length = fin.read(sendByte, 0, sendByte.length))>0){
	                dout.write(sendByte,0,length);
	                dout.flush();
	            }
	            //显示自己发送的图片
	            MR.sendPicture(Constant.file_path, null, 0, 1,Constant.friend_name,"images/11.jpg");
	            } catch (Exception e) {

	            } finally{
	                if (dout != null)
	                    dout.close();
	                if (fin != null)
	                    fin.close();
	                if (socket != null)
	                    socket.close();
	        }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	public static void SendFile(String ip,int port,File myfile){
	    
	    int length = 0;
        byte[] sendByte = null;
        Socket socket = null;
        DataOutputStream dout = null;
        FileInputStream fin = null;
        try {
          try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port),10 * 1000);
            dout = new DataOutputStream(socket.getOutputStream());
            fin = new FileInputStream(myfile);
            sendByte = new byte[1024];
            dout.writeUTF(myfile.getName());
            while((length = fin.read(sendByte, 0, sendByte.length))>0){
                dout.write(sendByte,0,length);
                dout.flush();
            }
            } catch (Exception e) {

            } finally{
                if (dout != null)
                    dout.close();
                if (fin != null)
                    fin.close();
                if (socket != null)
                    socket.close();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public void SendFileHand(String ip,int port,String myname,File myfile) throws UnknownHostException, IOException{
		MessageProcesser.FileMessage(myname,Constant.ip,Constant.port);
		clientPackets(ip,port);
	    Constant.myfile = myfile;
	    friendip = ip;
	    friendport = port;
	}
	public void SendPictureHand(String ip,int port,String myname,File myfile) throws UnknownHostException, IOException{
		MessageProcesser.ImageMessage(myname,Constant.ip,Constant.port);
		clientPackets(ip,port);
	    Constant.myfile = myfile;
	    friendip = ip;
	    friendport = port;
	}
	public static void ReceiveFile(ServerSocket Socket) throws IOException {
		byte[] inputByte = null;
		int length = 0;
		DataInputStream din = null;
		FileOutputStream fout = null;
		Socket connectionSocket;
		try {
			connectionSocket = Socket.accept();
			din = new DataInputStream(connectionSocket.getInputStream());
			JFileChooser jfilechooser = new JFileChooser();
			String filename = din.readUTF();
			int is_receive = MR.isReceive(filename, Constant.friend_name);
			if (is_receive == 0) {
				JTextField text;
				text=MR.getTextField(jfilechooser);
				System.out.println(filename);
				text.setText(filename);
				int result = jfilechooser.showSaveDialog(MR.panel);// 弹出文件保存窗口
				String path = "";
				if (result == JFileChooser.APPROVE_OPTION) {
					path = jfilechooser.getSelectedFile().getPath();
					//System.out.println(path);
					fout = new FileOutputStream(new File(path));
					inputByte = new byte[1024];
					MR.Tips("开始接收文件...");
					//System.out.println("开始接收数据...");
					while (true) {
						if (din != null) {
							length = din.read(inputByte, 0, inputByte.length);
						}
						if (length == -1) {
							break;
						}
						System.out.println(length);
						fout.write(inputByte, 0, length);
						fout.flush();
					}
					System.out.println("完成接收");
					MR.Tips("文件接收完毕！");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fout != null)
				fout.close();
			if (din != null)
				din.close();

		}
	}
	public static void ReceiveImage(ServerSocket Socket) throws IOException {
		byte[] inputByte = null;
        int length = 0;
        DataInputStream din = null;
        FileOutputStream fout = null;
        Socket connectionSocket;
        try {
        	connectionSocket = Socket.accept();
            din = new DataInputStream(connectionSocket.getInputStream());
            String filename = din.readUTF();
            File tempFile = new File("pictures/"+filename);
            fout = new FileOutputStream(tempFile);
            inputByte = new byte[1024];
            System.out.println("开始接收数据...");
            while (true) {
                if (din != null) {
                    length = din.read(inputByte, 0, inputByte.length);
                }
                if (length == -1) {
                    break;
                }
                System.out.println(length);
                fout.write(inputByte, 0, length);
                fout.flush();
            }
            System.out.println("完成接收");
            MR.sendPicture("pictures/"+filename, null, 0, 2, Constant.friend_name,"images/12.jpg");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fout != null)
                fout.close();
            if (din != null)
                din.close();

        }
    }
		
		

	
	public void Login(String name ,String password) throws UnknownHostException, IOException{ 
		//Constant.ip = "172.18.32.240";
		MessageProcesser.LoginMessage(name,password,Constant.ip,Constant.port);
		clientPackets(hostip,port);
	}
	public void Register(String name,String password) throws UnknownHostException, IOException{
		//Constant.ip = "172.18.32.240";
		MessageProcesser.RegisterMessage(name, password, Constant.ip, Constant.port);
		clientPackets(hostip,port);
	}
	public void Logout(String name) throws UnknownHostException, IOException{
		//Constant.ip = "172.18.32.240";
	    MessageProcesser.LogoutMessage(name, Constant.ip, Constant.port);
	    clientPackets(hostip,port);
	}
	public void Getip(String name) throws UnknownHostException, IOException{
		//Constant.ip = "172.18.32.240";
		MessageProcesser.GetipMessage(name,Constant.ip,Constant.port);
		clientPackets(hostip,port);
	}

	public void ReceiveMessage() throws IOException{
		Socket socket = welcomeSocket.accept();
		new Thread(new Task(socket,true,false)).start();
	}
	

	public static void GetFriend(String name) throws UnknownHostException, IOException{
		//Constant.ip = "172.18.32.240";
		MessageProcesser.GetList(name,Constant.ip,Constant.port);
		clientPackets(hostip,port);
		
	}
	public void AddFriend(String name,String friend) throws UnknownHostException, IOException{
		//Constant.ip = "172.18.32.240";
		MessageProcesser. AddFriendMessage(name,friend,Constant.ip,Constant.port);
		clientPackets(hostip,port);
	}
	
	public void DeleteFriend(String name,String friend) throws UnknownHostException, IOException{
		//Constant.ip = "172.18.32.240";
		MessageProcesser.DeleteFriendMessage(name,friend,Constant.ip,Constant.port);
		clientPackets(hostip,port);
	}
	public void UpdateFriend(){
		
	}
	public void GroupMessage(String name,String ip,String message) throws UnknownHostException, IOException{
		MessageProcesser.GroupMessage(name,ip,message,Constant.port);
		clientPackets(hostip,port);
	}
	public static void clientPackets(String ipaddr,int port) throws UnknownHostException, IOException{
		//通过ip地址创建 InetAddress对象
		//System.out.println("Send!");
		   ipaddress=InetAddress.getByName(ipaddr);   
		   clientSocket  = new Socket(ipaddress,port);
		//输出数据流
		
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		//发信息给服务器
		
		int length_1 = Integer.parseInt(Constant.outfo[0]);
		outToServer.writeBytes(Constant.outfo[0]+'\n');
//		System.out.println(length_1);
		for(int i=1;i<length_1;i++){
			outToServer.writeBytes(Constant.outfo[i]+'\n');
		   //System.out.println(Constant.outfo[i]);
		}
		 clientSocket.setSoTimeout(10*1000);
		 clientSocket.close();
	}
    
	
	public static void clientheartPackets(String ipaddr,int port) throws UnknownHostException, IOException{
		//通过ip地址创建 InetAddress对象
		//System.out.println("Send!");
		   ipaddress=InetAddress.getByName(ipaddr);   
		   clientSocket  = new Socket(ipaddress,port);
		//输出数据流
		
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		//发信息给服务器
		
		int length_1 = Integer.parseInt(Constant.outfo2[0]);
		outToServer.writeBytes(Constant.outfo2[0]+'\n');
//		System.out.println(length_1);
		for(int i=1;i<length_1;i++){
			outToServer.writeBytes(Constant.outfo2[i]+'\n');
		    //System.out.println(Constant.outfo2[i]);
		}
		 clientSocket.setSoTimeout(10*1000);
		 clientSocket.close();
	}
	

	

	public static void serverPackets(Socket connectionSocket,String[] info) throws IOException {
		// while(true){
		// System.out.println("Receiver!");
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(
				connectionSocket.getInputStream()));
		        info[0] = inFromClient.readLine();
		        if(info[0] == null){
		        	return;
		        }
		      
		int length = Integer.parseInt(info[0]);

		URLDecoder ud = new URLDecoder();
		for (int i = 1; i < length; i++) {
			info[i] = inFromClient.readLine();
			// System.out.println(ud.decode(Constant.info[i], "utf-8"));
			
		}
		if (info[1].equals("P2PMESSAGE")) {
			MR.sendMes(ud.decode(info[5], "utf-8"),
					ud.decode(info[4], "utf-8"), "images/12.jpg",null,null, 0,ud.decode(info[2], "utf-8"));
			return;
			//ReceiveMessage();
		}
		if (info[1].equals("CSMESSAGE")) {
			
			MR.sendMes(ud.decode(info[3], "utf-8"),
					ud.decode(info[4], "utf-8"), "images/12.jpg",null, null,0,ud.decode(info[2], "utf-8"));
			return;
			//ReceiveMessage();
		}
            if(info[1].equals("STATUS")){
            	if(info[2].equals("true")){
            		MR.setName(Constant.name);
            		LMR.canLog();
            		MR.addAction();
            		GetFriend(Constant.name);
            		new Thread(new Task(null,false,false)).start();
            		//ReceiveMessage();
            	}
            	else{
            		LMR.show.setText(ud.decode(info[3], "utf-8"));
            	}
            	return;
            }
            if(info[1].equals("RegisterBack")){
            	if(info[2].equals("true")){
            		//System.out.println("canLog"); 
            		MR.setName(Constant.name);
            		LMR.canLog();
            		MR.addAction();
            		GetFriend(Constant.name);
            		new Thread(new Task(null,false,false)).start();
            		//HeartBeat(Constant.name);
            		//ReceiveMessage();
            	}
            	else{
            		LMR.show.setText("用户名已存在");
            	}
            	return;
            }
            
		if (info[1].equals("LIST")) {
			String[] name = new String[(length - 2)/2];
			String[] Icon = new String[(length - 2)/2];
			boolean[] isOnline = new boolean[(length - 2)/2];
			int index = 0;
			for (int i = 2; i < length; i += 2) {
				name[index] = info[i];
				if (info[i + 1].equals("true")) {
					Icon[index] = "images/2.jpg";
					isOnline[index++] = true;
				} else {
					Icon[index] = "images/2_gray.jpg";
					isOnline[index++] = false;
				}
			}
			MR.removeAction();
			MR.initFriends((length - 2)/2, Icon, name, isOnline);
			MR.addAction();
			return;
			// ReceiveMessage();
		}
		
		if (info[1].equals("UPDATE")) {
			String[] name = new String[(length - 2)/2];
			String[] Icon = new String[(length - 2)/2];
			boolean[] isOnline = new boolean[(length - 2)/2];
			int index = 0;
			for (int i = 2; i < length; i += 2) {
				name[index] = info[i];
				if (info[i + 1].equals("true")) {
					Icon[index] = "images/2.jpg";
					isOnline[index++] = true;
				} else {
					Icon[index] = "images/2_gray.jpg";
					isOnline[index++] = false;
				}
			}
			MR.removeAction();
			MR.updateFriends((length - 2)/2, Icon, name, isOnline);
			MR.addAction();
			return;
			// ReceiveMessage();
		}
		
            
		if (info[1].equals("ADDFRIENDRETURN")) {
			if (info[2].equals("true")) {
				String[] name = new String[1];
				String[] Icon = new String[1];
				boolean[] isOnLine = new boolean[1];
				name[0] = Constant.addFriendName;
				if(info[4].equals("true")){
				Icon[0] = "images/2.jpg";
				isOnLine[0] = true;
				}
				else{
					Icon[0] = "images/2_gray.jpg";
					isOnLine[0] = false;
				}
				MR.removeAction();
				MR.addFriends(1, Icon, name,isOnLine);
				MR.addAction();
			}
			else{
				MR.Tips(ud.decode(info[3], "utf-8"));
			}
			return;
			// ReceiveMessage();
		}
		
		if (info[1].equals("DELETEFRIENDRETURN")) {
			if (info[2].equals("true")) {
				MR.removeAction();
				MR.deleteFriend();
				MR.addAction();
			}
			return;
			// ReceiveMessage();
		}
		if (info[1].equals("RETURNIP")) {
			if (info[2] != null) {
				Constant.P2P_IP = info[2];	
				if(Constant.action_type == 0){
				MR.sendMes(MR.addText.getText(),null,"images/11.jpg",info[2],info[3],1,null);
				return;
				}
				if(Constant.action_type == 1){
					MR.sendFile(Constant.file_path,Constant.P2P_IP,Integer.parseInt(info[3]));
					return;
				}
				if(Constant.action_type == 2){
					MR.sendPicture(Constant.file_path, Constant.P2P_IP, Integer.parseInt(info[3]),0,null,null);
					return;
					}
			}
			return;
			// ReceiveMessage();
		}
		if (info[1].equals("FILEHAND")){
		    Constant.SendFileType = "FILE";
			new Thread(new Task(null,true,true)).start();
			Constant.friend_name = ud.decode(info[2], "utf-8");
			friendip = info[3];
			friendport = Integer.parseInt(info[4]);
			System.out.println(friendport);
			return;
		}
		if(info[1].equals("IMAGEHAND")){
			Constant.SendFileType = "IMAGE";
			new Thread(new Task(null,true,true)).start();
			Constant.friend_name = ud.decode(info[2], "utf-8");
			friendip = info[3];
			friendport = Integer.parseInt(info[4]);
			System.out.println(friendport);
			return;
		}
		if(info[1].equals("FILE")){
			
			SendFile(friendip,Integer.parseInt(info[2]),Constant.myfile);
		}
		if(info[1].equals("IMAGE")){		
			SendPicture(friendip,Integer.parseInt(info[2]),Constant.myfile);
		}
	}
	   static class Task implements Runnable {
			 
		      private Socket socket;
		      private boolean flag; 
		      private boolean issendfile;
		      public Task(Socket socket,boolean flag,boolean issendfile) {
		         this.socket = socket;
		         this.flag = flag;
		         this.issendfile =issendfile;
		      }
		      
		      public void run() {
		    	  
		    	  if(!issendfile){
		         try {
		            handleSocket();
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		    	  }
		         else{
		        	 ServerSocket filesocket = null;
		        
		        	int newport = 0;
		     		for(int i = 5000;i<9000;i++){
		    			try{
		    				
		    				filesocket = new ServerSocket(i);
		    				newport = i;
		    			
		    				break;
		    			}
		    			catch(IOException e){
		    				System.out.println("socket fail!");
		    			}
		    		}
		     		if((Constant.SendFileType).equals("FILE")){
		     		MessageProcesser.FileFeedback(String.valueOf(newport));
		     		}
		     		if((Constant.SendFileType).equals("IMAGE")){
		     	    MessageProcesser.ImageFeedback(String.valueOf(newport));
		     		}
		     		try {
		     			System.out.println(friendip);
		     			System.out.println(friendport);
						clientPackets(friendip,friendport);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		     		try {
		     			if((Constant.SendFileType).equals("FILE")){
						ReceiveFile(filesocket);
		     			}
		     			if((Constant.SendFileType).equals("IMAGE")){
						ReceiveImage(filesocket);
		     			}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
		         }
		      }
		      
		      /**
		       * 跟客户端Socket进行通信
		      * @throws Exception
		       */
		private void handleSocket() throws Exception {
			if (flag) {
				String uimessage[] = new String[100];
				serverPackets(socket, uimessage);
				socket.close();
			} else {
				HeartBeat(Constant.name);
			}
		}
	}
    }
