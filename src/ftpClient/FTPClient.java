/**
 * 
 */
package ftpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
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
	BufferedReader in; 
	DataOutputStream out;
	Scanner keyb = new Scanner(System.in);
	private BufferedReader storeText;
	String request;
	String response;
	
	
	public FTPClient(String serverIP, int port) throws IOException, UnknownHostException {
		socket = new Socket(serverIP, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
	
	public void getResponse() throws IOException {
		response = in.readLine();
	}
	
	public void useResponse() {
		
	}
	
	public void printMenu() {
		System.out.println("Tast 1 for at overføre en fil til ZYBO board");
		System.out.println("Tast 2 for at sende en kommando til ZYBO board");
		
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
