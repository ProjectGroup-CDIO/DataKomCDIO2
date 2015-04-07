package weightClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.*;

public class Kommu {

//	@SuppressWarnings("deprecation")
	public void kommu() {
		
		//Data input
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input the server IP address: ");
		String IPinput = (String) scanner.nextLine();
		System.out.println("Input the server application port number: ");
		String PortAddress = (String) scanner.nextLine();
		
//		JFrame frame = new JFrame("asd");
		
//		String IPinput = (String) JOptionPane.showInputDialog(frame, "input IP address");
//		String PortAdrres = (String) JOptionPane.showInputDialog(frame, "input port address");

		Socket s = null;  
		DataOutputStream outputStream = null;
		InputStreamReader inputstream = null;
		
		System.out.println("IP Adress: " +IPinput);
		System.out.println("Port: " + PortAddress);

		try {
			s = new Socket(IPinput,Integer.parseInt(PortAddress));
			
			outputStream = new DataOutputStream(s.getOutputStream());
			
			inputstream = new InputStreamReader(s.getInputStream());
			
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: hostname");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		BufferedReader d = new BufferedReader(inputstream);
		
		//Check for om ports og sockets ikke er null, hvis de ikke er 
		if(s != null && outputStream != null && inputstream != null){
			System.out.println("Connection Established");
			printMenu();
			//scanner tjekker for om der er en string
			while(scanner.hasNext()){
				
				PrintWriter w = new PrintWriter(outputStream,true);
				
				String dataInput = scanner.nextLine();
//				System.out.println(dataInput);
				
				try{
					
					w.print(dataInput + "\r\n");
					w.flush();
					
					
					//this get the reply from the server if any
					String ReplyMess = d.readLine();
					if(ReplyMess != null){
					System.out.println(ReplyMess);
					}
					
				} catch (UnknownHostException e) {
					System.err.println("Trying to connect to unknown host: " + e);
				} catch (IOException e) {
					System.err.println("IOException:  " + e);
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
	
//	public void selectInput(input) {
//		switch (input) {
//		case S: 
//		}
//	}
	
}
