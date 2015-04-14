package ftpClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

//import weightClient.WeightClient;

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
		//WeightClient weightc = new WeightClient();
/*		ftp.login();

		ftp.startEar();*/
		
		while(active){
			active = false; 
			Scanner keyb = new Scanner(System.in);
			System.out.println("Press 1 for FTP Client");
			System.out.println("Press 2 for Weight Client");
			String input = " ";
			try {
				input = keyb.nextLine();
			} catch (NoSuchElementException e1) {
				System.out.println("Error: "+e1.getMessage());
				//e1.printStackTrace();
			}
	
			if(!(input.isEmpty())) {
				if(input.equals("1")) {
					while(true){
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						active = true;
						while(active){
							active = false;
							ftp.login();
							ftp.startEar();
							ftp.printMenu();
							ftp.makeRequest();
							ftp.sendRequest();
						}
					}
				}
				if(input.equals("2")) {
					ftp.productMani();
					active = true;
					continue;
				}else{
					active = true;
					continue;
				}
				
			}
			keyb.close();
		}
		
	}

}
