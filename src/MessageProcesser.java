import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.File;
import java.io.IOException;

public class MessageProcesser {
    
	public void WhichType(String type){
	
	}
	//登陆
	public static void LoginMessage(String name,String password,String ip,int port){
		Constant.outfo = new String[6];
		Constant.outfo[0] = "6";
		Constant.outfo[1] = String.valueOf(2);
		Constant.outfo[2] = name;
		Constant.outfo[3] = password;
		Constant.outfo[4] = ip;
		Constant.outfo[5] = String.valueOf(port);
	}
	//退出登录
	public static void LogoutMessage(String name,String ip,int port){
		Constant.outfo = new String[6];
		Constant.outfo[0] = "6";
		Constant.outfo[1] = String .valueOf(9);
		Constant.outfo[2] = name;
		Constant.outfo[4] = ip;
		Constant.outfo[5] = String.valueOf(port);
	}
	//得到对应用户的ip
	public static void GetipMessage(String name,String ip,int port){
		Constant.outfo = new String[6];
		Constant.outfo[0] = "6";
		Constant.outfo[1] = String .valueOf(10);
		Constant.outfo[2] = name;
		Constant.outfo[4] = ip;
		Constant.outfo[5] = String.valueOf(port);
	}
	public static void Getipreturn(String ip){
		Constant.outfo1 = new String[3];
		Constant.outfo1[0] = "3";
		Constant.outfo1[1] = "RETURNIP";
		Constant.outfo1[2] = ip;
	}
	//登陆反馈
	public static void statusMessage(String judge,String reason){
		Constant.outfo1 = new String[4];
		Constant.outfo1[0] = "4";
		Constant.outfo1[1] = "STATUS";
		Constant.outfo1[2] = judge;
		Constant.outfo1[3] = reason;
	}
	//注册
	public static void RegisterMessage(String name,String password,String ip,int port){
		Constant.outfo = new String[6];
		Constant.outfo[0] = "6";
		Constant.outfo[1] = String.valueOf(3);
		Constant.outfo[2] = name;
		Constant.outfo[3] = password;
		Constant.outfo[4] = ip;
		Constant.outfo[5] = String.valueOf(port);
	}
	//注册反馈
	public static void Registerback(String judge){
		Constant.outfo1 = new String[3];
		Constant.outfo1[0] = "3";
		Constant.outfo1[1] = "RegisterBack";
		Constant.outfo1[2] = judge;
		
	}
	public void LogoutMessage(){
		
	}
	public static void HeartBeatMessage(String name,String ip,int port){
		Constant.outfo2 = new String[6];
		Constant.outfo2[0] = "6";
		Constant.outfo2[1] = String.valueOf(4);
		Constant.outfo2[2] = name;
		Constant.outfo2[4] = ip;
		Constant.outfo2[5] = String.valueOf(port);
	}
	public static void GetList(String name,String ip,int port){
		Constant.outfo = new String[6];
		Constant.outfo[0] = "6";
		Constant.outfo[1] = String.valueOf(5);
		Constant.outfo[2] = name;
		Constant.outfo[4] = ip;
		Constant.outfo[5] = String.valueOf(port);
	}
	public void PictureMessage(){
		
	}
	public void FileMessage(){
		
	}
	public static void MessageMessage(String str,String name) throws UnsupportedEncodingException{
		Constant.outfo = new String[6];
		Constant.outfo[0] = "6";
		Constant.outfo[1] = "P2PMESSAGE";
		Constant.outfo[2] = name;
		Constant.outfo[3] = "text";
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

		String dateStr = sdf.format(calendar.getTime());
		
		Constant.outfo[4] =dateStr ;
		Constant.outfo[5] = URLEncoder.encode(str, "utf-8");
	}

	public static void AddFriendMessage(String name,String friend,String ip,int port){
		Constant.outfo = new String[6];
		Constant.outfo[0] = "6";
		Constant.outfo[1] = String.valueOf(6);
		Constant.outfo[2] = name;
		Constant.outfo[3] = friend;
		Constant.outfo[4]  =ip;
		Constant.outfo[5] = String.valueOf(port);
		
	}
	public static void AddFriendreturn(String judge,String reason){
		Constant.outfo1 = new String[4];
		Constant.outfo1[0] = "4";
		Constant.outfo1[1] = "ADDFRIENDRETURN";
		Constant.outfo1[2] = judge;
		Constant.outfo1[3] = reason;
	}
	public static void DeleteFriendMessage(String name,String friend,String ip,int port){
		Constant.outfo = new String[6];
		Constant.outfo[0] = "6";
		Constant.outfo[1] = String.valueOf(7);
		Constant.outfo[2] = name;
		Constant.outfo[3] = friend;
		Constant.outfo[4] = ip;
		Constant.outfo[5] = String.valueOf(port);
	}
	public static void FileMessage(String name,String ip,int port){
		Constant.outfo = new String[5];
		Constant.outfo[0] = "5";
		Constant.outfo[1] = "FILEHAND";
		Constant.outfo[2] = name;
		Constant.outfo[3] = ip;
		Constant.outfo[4] = String.valueOf(port);
	} 
	public static void ImageMessage(String name,String ip,int port){
		Constant.outfo = new String[5];
		Constant.outfo[0] = "5";
		Constant.outfo[1] = "IMAGEHAND";
		Constant.outfo[2] = name;
		Constant.outfo[3] = ip;
		Constant.outfo[4] = String.valueOf(port);
	}
	public static void FileFeedback(String port){
		Constant.outfo = new String[3];
		Constant.outfo[0] = "3";
		Constant.outfo[1] = "FILE";
		Constant.outfo[2] = port;
	}
	public static void ImageFeedback(String port){
		Constant.outfo = new String[3];
		Constant.outfo[0] = "3";
		Constant.outfo[1] = "IMAGE";
		Constant.outfo[2] = port;
	}
	public static void DeleteFriendreturn(String judge){
		Constant.outfo1 = new String[3];
		Constant.outfo1[0] = "3";
		Constant.outfo1[1] = "DELETEFRIENDRETURN";
		Constant.outfo1[2] = judge;
	}
	public void UpdateFriendMessage(){
		
	}
	public static void GroupMessage(String name,String ip,String message,int port) throws UnsupportedEncodingException{
		Constant.outfo = new String[6];
		Constant.outfo[0]="6";
		Constant.outfo[1] = String .valueOf(8);
		Constant.outfo[2] = name;
		Constant.outfo[3] = URLEncoder.encode(message, "utf-8");
		Constant.outfo[4] = ip;
		Constant.outfo[5] = String.valueOf(port);
	}
	public static void CSMessage(String name,String message){
		Constant.outfo1 = new String[5];
		Constant.outfo1[0] = "5";
		Constant.outfo1[1] = "CSMESSAGE";
		Constant.outfo1[2] = name;
		Constant.outfo1[3] = message;
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

		String dateStr = sdf.format(calendar.getTime());
		
		Constant.outfo[4] =dateStr ;
	}
	
}
