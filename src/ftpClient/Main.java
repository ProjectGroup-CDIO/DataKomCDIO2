package ftpClient;

import java.io.IOException;
import java.net.UnknownHostException;

import weightClient.WeightClient;

public class Main {

	private static boolean active = true;
	
	public static boolean isActive() {
		return active;
	}

	public static void setActive(boolean active) {
		Main.active = active;
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		FTPClient ftp = new FTPClient("127.0.0.1", 21);
		WeightClient weightc = new WeightClient();
		ftp.login();

		ftp.startEar();


		while(true){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(active){
				active = false;
				System.out.println("Please write a command");
				ftp.makeRequest();
				ftp.sendRequest();
				

			}
		}



	}

}
