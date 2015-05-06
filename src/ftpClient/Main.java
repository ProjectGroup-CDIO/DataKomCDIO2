package ftpClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

//import weightClient.WeightClient;

public class Main {

	private static boolean active = true;
	private static Scanner keyb = new Scanner(System.in);
	public static boolean isActive() {
		return active;
	}

	public static void setActive(boolean active) {
		Main.active = active;
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		FTPClient ftp = null;

		boolean connected = false;

		while(!connected){
			System.out.println("IP address of the FTP server");
			String IPaddr = keyb.nextLine();
			System.out.println("Portnumber of FTP server");
			String PortNr = keyb.nextLine();
			ftp = new FTPClient(IPaddr, Integer.parseInt(PortNr));
			if(ftp.getSocket().isConnected()){
				connected = true;
			}

		}
		ftp.login();
		ftp.startEar();
		while(active){
			String input = " ";
			System.out.println("Press 1 for FTP Client");
			System.out.println("Press 2 for Weight Client");
			System.out.println("Press Q to quit");
			try {
				input = keyb.nextLine();
			} catch (NoSuchElementException e1) {
				System.out.println("Error: "+e1.getMessage());
			}
			if(!(input.isEmpty())){	
				if(input.equals("1")){
					ftpCommands(ftp);
				}
				else if(input.equals("2")) {
					ftp.productMani();
					active = true;
					continue;
				}
				else if(input.equals("Q")){
					System.out.println("Ending program");
					ftp.getSocket().close();
					active = false;
					break;
					
				}else{
					active = true;
					continue;
				}
			}
		}
		keyb.close();
	}

	private static void ftpCommands(FTPClient ftp) throws IOException {
		try {
			Thread.sleep(200);
			while(true){
				if(loginAccepted(ftp)){
					ftp.printMenu();
					System.out.println("Choose 1, 2, or B to exit");
					String input = keyb.nextLine();

					if(input.equals("1") || input.equals("2")){
						ftp.makeRequest(input);
						ftp.sendRequest();
					}else if(input.equals("B")){
						System.out.println("breaks ftpCommands loop");
						break;
					}else{
						System.out.println("Command not recognized try again!");
					}
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	private static boolean loginAccepted(FTPClient ftp) {
		if(ftp.getEar().line.contains("incorrect")){

			return false;

		}
		return true;
	}

}
