import java.io.IOException;

public class RunMiRoad {
	public static void main(String[] args) throws IOException {

		Myclient client = new Myclient();
		MiRoad win = new MiRoad("MiRoad", "images/MiRoadBackGround.png", 700, 620, "images/6.jpg");
		LogMiRoad LMR = new LogMiRoad("images/loginbackground.png", "images/loginheader.png");

		LMR.setMiRoad(win);
		LMR.setClient(client);
		win.setClient(client);
		client.setMiRoad(win);
		client.setLogMiRoad(LMR);

	while (true) {
		client.ReceiveMessage();
		}			

	}		
}
