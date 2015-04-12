/**
 * 
 */
package ftpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


/**
 * @author Thomas
 *
 */

public class FTPClient extends Thread {

	private Socket socket;
	public static BufferedReader in; 
	private DataOutputStream out;
	private PrintWriter writeOut;
	private FileOutputStream fileOut;
	private Scanner keyb = new Scanner(System.in);
	private String request;
	private String response;
	private ServerListener ear = new ServerListener();

	public FTPClient(String serverIP, int port) throws IOException, UnknownHostException {
		socket = new Socket(serverIP, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new DataOutputStream(socket.getOutputStream());
		 
	}

	public void makeRequest() {
		String input = keyb.nextLine();
		if(input.toLowerCase().equals("ls") || input.toLowerCase().equals("list")) {
			request = "list";
		}else if(input.toLowerCase().equals("get") || input.toLowerCase().equals("retr")) {
			request = "retr";
		}
		request = input;
	}

	public void sendRequest() throws IOException {

		
		System.out.println(request);
		out.writeBytes(request + "\r\n");
		
	}

	public void getResponse() throws IOException {

		ear.start();

	}
	
	
	public void printResponse() {
		System.out.println(response);
	}

	public void useResponse() {
		
	}

	public void printMenu() {
		System.out.println("Tast 1 for at overføre en fil til ZYBO board");
		System.out.println("Tast 2 for at sende en kommando til ZYBO board");		
	}

	public void writeFile(File file) throws FileNotFoundException {
		fileOut = new FileOutputStream(file);
	}

	public void run() {
		FTPClient ftp;
		while(true) {
			System.out.print("Indtast IP du vil forbinde til: ");
			String IP = keyb.nextLine();
			System.out.print("\nIndtast port# du vil forbinde til: ");
			System.out.println();
			try {
				int port = Integer.parseInt(keyb.nextLine());
				ftp = new FTPClient(IP, port);
				break;
			} catch (UnknownHostException e) {
				System.out.println("Error006: "+e.getMessage());
				e.printStackTrace();
			} catch (BindException e) {
				System.out.println("Error009: "+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Error007: "+e.getMessage());
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.println("Error011: "+e.getMessage());
			} 
		}

		while(true) {
			printMenu();
			int input = Integer.parseInt(keyb.nextLine());
			switch(input) {
			case 1: //transfer file to server
				while(true) {
					System.out.print("Indtast sti til fil der skal overføres: ");
					String file = keyb.nextLine();
					System.out.println();
					if(file.equals("q") || file.equals("quit")) {
						break;
					} else {
						try {
							ftp.writeFile(new File(file));	
							break;
						} catch (FileNotFoundException e) {
							System.out.println("Error010: "+e.getMessage());
						}
					}
				}
			case 2: 
			default: 
				System.out.println("Prøv igen");
			}
		}
	}

	public void Login() {
		// TODO Auto-generated method stub
		
	}




	//	private int productVerification(String inline,int nrOfProducts) {
	//		try {
	//			storeText = new BufferedReader(new FileReader("Store.txt"));
	//			String line = "";
	//			
	//			if(nrOfProducts >= Integer.parseInt(inline)){
	//				for(int i = 1; i <= Integer.parseInt(inline); i++){
	//					line = storeText.readLine();
	//				}
	//				outstream.writeBytes("You have choosen:");
	//				outstream.writeBytes(line);
	//
	//				outstream.writeBytes("  "+line.indexOf(",") + "\n\r");
	//				outstream.writeBytes("  "+(line.substring(line.indexOf(",")+1, line.length()).indexOf(",")+line.indexOf(",")+1) + "\n\r");
	//				
	//				
	////				for(){
	////					
	////				}
	//				//String ProduktNr
	//				//produkt nr
	//				// produkt name
	//				//produkt total weight
	//
	//
	//			}else{
	//				return 0;
	//			}
	//
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		return 0;
	//
	//
	//	}


	//	try {
	//		
	//
	//		storeText = new BufferedReader(new FileReader("Store.txt"));
	//
	//
	//		String line = storeText.readLine();
	//		
	//		int nrOfProducts = 0;
	//		while (line != null) {
	//			outstream.writeBytes(line +"\n\r");
	//			line = storeText.readLine();
	//			nrOfProducts++;
	//		}
	//		outstream.writeBytes("Please write a produkt ID - its a nr.");
	//		inline = instream.readLine().toUpperCase();
	//		if((inline.matches("[0-9]+"))){
	//			productVerification(inline, nrOfProducts);
	//		}else{
	//			System.out.println("Wrong input!");
	//		}
	//
	//	} catch (Exception e2) {
	//		// TODO Auto-generated catch block
	//		e2.printStackTrace();
	//	}


}
