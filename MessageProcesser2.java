import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MessageProcesser2 {
    
	public void WhichType(String type){
	
	}
	//登陆
	public static void LoginMessage(String name,String password,String ip,int port,String outfo[]){

		outfo[0] = "6";
		outfo[1] = String.valueOf(2);
		outfo[2] = name;
		outfo[3] = password;
		outfo[4] = ip;
		outfo[5] = String.valueOf(port);
		
	}
	//退出登录
	public static void LogoutMessage(String name,String ip,int port,String outfo[]){

	    outfo[0] = "6";
		outfo[1] = String .valueOf(9);
		outfo[2] = name;
		outfo[4] = ip;
		outfo[5] = String.valueOf(port);
	}
	//得到对应用户的ip
	public static void GetipMessage(String name,String ip,int port,String outfo[]){

		outfo[0] = "6";
		outfo[1] = String .valueOf(10);
		outfo[2] = name;
		outfo[4] = ip;
		outfo[5] = String.valueOf(port);
	}
	public static void Getipreturn(String ip,int port,String outfo[]){
	
		outfo[0] = "4";
		outfo[1] = "RETURNIP";
		outfo[2] = ip;
		outfo[3] = String.valueOf(port);
	}
	//登陆反馈
	public static  void statusMessage(String judge,String reason,String outfo[]){
		outfo[0] = "4";
		outfo[1] = "STATUS";
		outfo[2] = judge;
		outfo[3] = reason;
	}
	//注册
	public static void RegisterMessage(String name,String password,String ip,int port,String outfo[]){

		outfo[0] = "6";
		outfo[1] = String.valueOf(3);
		outfo[2] = name;
		outfo[3] = password;
		outfo[4] = ip;
		outfo[5] = String.valueOf(port);
	}
	//注册反馈
	public static void Registerback(String judge,String outfo[]){

		outfo[0] = "3";
		outfo[1] = "RegisterBack";
		outfo[2] = judge;
		
	}
	public void LogoutMessage(){
		
	}
	public static void HeartBeatMessage(String name,String ip,String outfo[]){

		outfo[0] = "5";
		outfo[1] = String.valueOf(4);
		outfo[2] = name;
		outfo[4] = ip;
	}
	public static void GetList(String name,String ip,String outfo[]){
	
		outfo[0] = "5";
		outfo[1] = String.valueOf(5);
		outfo[2] = name;
		outfo[4] = ip;
	}
	public void PictureMessage(){
		
	}
	public void FileMessage(){
		
	}
	public static void MessageMessage(String str,String outfo[]) throws UnsupportedEncodingException{
	
		outfo[0] = "6";
		outfo[1] = "P2PMESSAGE";
		outfo[2] = "DAMON";
		outfo[3] = "text";
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

		String dateStr = sdf.format(calendar.getTime());
		
		outfo[4] =dateStr ;
		outfo[5] = URLEncoder.encode(str, "utf-8");
	}

	public static void AddFriendMessage(String name,String friend,String ip,String outfo[]){
	
		outfo[0] = "5";
		outfo[1] = String.valueOf(6);
		outfo[2] = name;
		outfo[3] = friend;
		outfo[4]  =ip;
		
		
		
	}
	public static void AddFriendreturn(String judge,String reason,String status,String outfo[]){

		outfo[0] = "5";
		outfo[1] = "ADDFRIENDRETURN";
		outfo[2] = judge;
		outfo[3] = reason;
		outfo[4] = status;
	}
	public static void DeleteFriendMessage(String name,String friend,String ip,String outfo[]){
	
		outfo[0] = "5";
		outfo[1] = String.valueOf(7);
		outfo[2] = name;
		outfo[3] = friend;
		outfo[4] = ip;
	}
	public static void DeleteFriendreturn(String judge,String outfo[]){

		outfo[0] = "3";
		outfo[1] = "DELETEFRIENDRETURN";
		outfo[2] = judge;
	}
	public void UpdateFriendMessage(){
		
	}
	public static void GroupMessage(String name,String message,String outfo[]){

		outfo[0]="4";
		outfo[1] = String .valueOf(8);
		outfo[2] = name;
		outfo[3] = message;
		
	}
	public static void CSMessage(String name,String message,String outfo[]){
	
		outfo[0] = "5";
		outfo[1] = "CSMESSAGE";
		outfo[2] = name;
		outfo[3] = message;
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

		String dateStr = sdf.format(calendar.getTime());
		
		outfo[4] =dateStr ;
	}
	
}
