/**
 * 
 */
package ftpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	public void useResponse() {
	}
	
	public void printMenu() {
		System.out.println("Tast 1 for at overføre en fil til ZYBO board");
		System.out.println("Tast 2 for at sende en kommando til ZYBO board");		
	}
	
	public void run() {
		
	}
	
	
}
