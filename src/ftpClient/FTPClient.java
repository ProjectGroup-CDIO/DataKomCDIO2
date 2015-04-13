/**
 * 
 */
package ftpClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


/**
 * @author Thomas
 *
 */

public class FTPClient extends Thread {

	private Socket socket; 
	private static BufferedReader in; 
	private DataOutputStream out;
	private DataInputStream fileIn;
	private FileOutputStream fileOut;
	private Scanner keyb = new Scanner(System.in);
	private String request;
	private String response;
	private ServerListener ear = new ServerListener();

	public FTPClient(String serverIP) throws IOException, UnknownHostException {
		socket = new Socket(serverIP, 21);
		//		datasocket = new Socket(serverIP, 20);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new DataOutputStream(socket.getOutputStream());
	}

	public static BufferedReader getIn() {
		return in;
	}

	public void makeRequest() {
		String input = keyb.nextLine();
//		if(input.toLowerCase().equals("ls") || input.toLowerCase().equals("list")) {
//			request = "list";
//		}else if(input.toLowerCase().equals("get") || input.toLowerCase().equals("retr")) {
//			request = "retr";
//		}else
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

	public void useResponse() throws IOException {
		fileIn = new DataInputStream(socket.getInputStream());
	}

	public void printMenu() {
		System.out.println("Tast 1 for at overføre en fil til ZYBO board");
		System.out.println("Tast 2 for at sende en kommando til ZYBO board");		
	}

	public void writeFile() throws UnknownHostException, IOException {
		
	    DataInputStream in = null;
	    
	    in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	    
	    System.out.print("Indtast sti til fil der skal overføres: ");
		String filePath = keyb.nextLine();
		System.out.println();
		File file = new File(filePath); 
		Path path = Paths.get(filePath);
		
	    // Get the size of the file
	    long length = file.length();
	    
	    if (length > Integer.MAX_VALUE) {
	        System.out.println("File is too large.");
	    }
	    
	    byte[] bytes = new byte[(int) length];    
	    bytes = Files.readAllBytes(path); 
	    System.out.println(bytes);
	    out.writeBytes("put ");
	    out.write(bytes);
	    out.writeBytes("\r\n");
	    
	    out.flush();
	    out.close();
	    in.close();
	    

	}
	
	public void Login() throws IOException {
		out.writeBytes("user helmut" + "\r\n");
	//	Thread.sleep(100);
		out.writeBytes("pass 123" + "\r\n");

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
				ftp = new FTPClient(IP);
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
							ftp.writeFile();	
							break;
						} catch (FileNotFoundException e) {
							System.out.println("Error010: "+e.getMessage());
							e.printStackTrace();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			case 2: 
			default: 
				System.out.println("Prøv igen");
			}
		}
	}





	//	private int productVerification(String inline,int nrOfProducts) {
	//		try {
	//			storeText = new BufferedReader(new FileReader("Store.txt"));
	//			String line;String input = "";
	//			while ((line = storeText.readLine()) != null) input += line + '\n';
	//		
	//		    storeText.close();
	//		    //System.out.println(input);
	//		    			        
	//		    String vare[] = input.split("\n");
	//		    //System.out.println(vare[Integer.parseInt(inline)]);
	//		    String valgt[] = vare[Integer.parseInt(inline)].split(",");
	//		    valgt[2]="150"; ///////// Her skal der stå nyVaegt
	//		    vare[Integer.parseInt(inline)]=valgt[0]+","+valgt[1]+","+valgt[2];
	//		    
	//		    //System.out.println(vare[Integer.parseInt(inline)]);
	//		    FileWriter output = new FileWriter("Store.txt");
	//		    try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Store.txt", true)))) {
	//		    	for(int i = 0; nrOfProducts-1 >= i;i++){
	//		    		if(i!=nrOfProducts-1){
	//		    			out.println(vare[i]);
	//		    		}else{
	//		    			out.print(vare[i]);
	//		    		}
	//		    	}
	//		    }catch (Exception e) {
	//		    					// TODO Auto-generated catch block
	//		    					e.printStackTrace();
	//		    }
	//		    output.close();
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
