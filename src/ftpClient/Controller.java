package ftpClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Controller {
	
	Scanner keyb = new Scanner(System.in);
	FTPClient ftp;
	
	public void run() {
		System.out.print("Indtast server IP-adresse: ");
		String serverIP = keyb.nextLine();
		System.out.print("\nIndtast server port#: ");
		int port = Integer.parseInt(keyb.nextLine());
		try {
			ftp = new FTPClient(serverIP, port);
		} catch (UnknownHostException e) {
			System.out.println("Error004: "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error005: "+e.getMessage());
			e.printStackTrace();
		}
		
	}

}
