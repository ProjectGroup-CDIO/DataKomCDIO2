/**
 * 
 */
package ftpClient;

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

public class FTPClient {
	
	Socket socket;
	InputStreamReader in;
	DataOutputStream out;
	Scanner keyb = new Scanner(System.in);
	String request;
	String response;
	
	
	public FTPClient(String serverIP, int port) throws IOException, UnknownHostException {
		socket = new Socket(serverIP, port);
		in = new InputStreamReader(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}
	
	public void makeRequest() {
		request = keyb.nextLine(); 
	}
	
	public void sendRequest() {
		try {
			byte[] sendReq = new byte[request.length()];
			sendReq = request.getBytes(); 
			out.write(sendReq, 0, sendReq.length);
		} catch (IOException e) {
			System.out.println("Error003: "+e.getMessage()+". Error in sendReq.");
		}
	}
	
	public void getResponse() {
		
	}
	
	public void useResponse() {
		
	}
	
	public void printMenu() {
		System.out.println("Tast 1 for at overføre en fil til ZYBO board");
		System.out.println("Tast 2 for at sende en kommando til ZYBO board");
		
	}
	
	
}
