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
	private BufferedReader in; 
	private DataOutputStream out;
	private FileOutputStream fileOut;
	private Scanner keyb = new Scanner(System.in);
	private String request;
	private String response;


	public FTPClient(String serverIP, int port) throws IOException, UnknownHostException {
		socket = new Socket(serverIP, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new DataOutputStream(socket.getOutputStream());
	}

	public void makeRequest() {
		request = keyb.nextLine(); 
	}

	public void sendRequest() throws IOException {
		//			byte[] sendReq = new byte[request.length()];
		//			sendReq = request.getBytes(); 
		//			out.write(sendReq, 0, sendReq.length);
		out.writeBytes(request);
	}

	public void getResponse() throws IOException {
		response = in.readLine();
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
				System.err.println("Error009: "+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Error007: "+e.getMessage());
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.println("Error011: "+e.getMessage());
			}
		}
		printMenu();
		int input = Integer.parseInt(keyb.nextLine());
		switch(input) {
		case 1: //transfer file to server
			while(true) {
				System.out.print("Indtast sti til fil der skal overføres: ");
				String file = keyb.nextLine();
				System.out.println();
				try {
					ftp.writeFile(new File(file));			
				} catch (FileNotFoundException e) {
					System.out.println("Error010: "+e.getMessage());
				}
			}
		case 2: 
		default: 
		}
	}


}
