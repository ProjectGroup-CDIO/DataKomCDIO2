package weightClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class WeightClient {

//	@SuppressWarnings("deprecation")
	public void run() {
		
		DataOutputStream outputStream = null;
		InputStreamReader inputstream = null;
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);
		
		//Data input
		System.out.println("Input the server IP address: ");
		String ip = (String) scanner.nextLine();
		System.out.println("Input the server application port number: ");
		String portDest = (String) scanner.nextLine();
		
		System.out.println("IP Address: " +ip);
		System.out.println("Port#: " + portDest);

		try {
			socket = new Socket(ip,Integer.parseInt(portDest));
			outputStream = new DataOutputStream(socket.getOutputStream());		
			inputstream = new InputStreamReader(socket.getInputStream());		
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: hostname");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		BufferedReader read = new BufferedReader(inputstream);
		PrintWriter write = new PrintWriter(outputStream,true);
		
		//Check for om ports og sockets ikke er null, hvis de ikke er 
		if(socket != null && outputStream != null && inputstream != null){
			System.out.println("Connection Established");
			printMenu();
			//scanner tjekker for om der er en string
			while(scanner.hasNext()){				
				String dataInput = scanner.nextLine();				
				try{				
					write.print(dataInput + "\r\n");
					write.flush();				
					//this gets the reply from the server if any
					String replyMess = read.readLine();
					if(replyMess != null){
					System.out.println(replyMess);
					}				
				} catch (UnknownHostException e) {
					System.err.println("Error001: "+e.getMessage());
				} catch (BindException e) {
					System.err.println("Error008: "+e.getMessage());
				} catch (IOException e) {
					System.err.println("Error002: "+e.getMessage());
				}
			}
			scanner.close();
		} else System.out.println("Unable to establish connection");
	}
	public void printMenu() {
		System.out.println("Press S and ENTER to read scale");
		System.out.println("Press T and ENTER to tare scale");
		System.out.println("Press Z and ENTER to zero scale");
		System.out.println("Write D \"<message>\" and ENTER to display message on weight display");
		System.out.println("Write DW and ENTER to display weight on scale"); //doesnt work?
	}	
}
