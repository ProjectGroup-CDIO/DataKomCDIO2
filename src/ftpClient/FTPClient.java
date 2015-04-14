/**
 * 
 */
package ftpClient;

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
import java.util.Scanner;

import weightClient.WeightClient;


/**
 * @author Thomas
 *
 */

public class FTPClient extends Thread {

	private Socket socket;
	public static BufferedReader in; 
	public DataOutputStream out;
	private Scanner keyb = new Scanner(System.in);
	private String request;
	private String response;
	private ServerListener ear = new ServerListener();
	private int portNumber;
	private Socket dataSocket = null;
	//	private ServerSocket serverSocket = new ServerSocket(portNumber);
	FTPClient FTPCOne = null;
	private String PASV = "";
	private DataInputStream dataIn = null;
	private FileOutputStream fileOut =null;




	public FTPClient(String serverIP, int port) throws IOException, UnknownHostException {
		socket = new Socket(serverIP, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new DataOutputStream(socket.getOutputStream()); 
	}

	public synchronized void makeRequest() {
		String input = keyb.nextLine();
		request = input;
	}

	public synchronized void sendRequest() throws IOException {
		System.out.println(request);
		if(request.toUpperCase().startsWith("RETR ")){
			out.writeBytes("PASV"+"\r\n");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			getPASV();
			try {
				readData(request);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}else if(request.toUpperCase().startsWith("LIST")){
			out.writeBytes("PASV"+"\r\n");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			getPASV();
			try {
				readData(request);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}



	}

	public void startEar() throws IOException {
		getEar().start();
	}


	public void printResponse() {
		System.out.println(response);
	}

	public void useResponse() throws UnknownHostException, IOException {

	}

	public void printMenu() {
		System.out.println("Tast 1 for at overføre en fil til ZYBO board");
		System.out.println("Tast 2 for at sende en kommando til ZYBO board");		
	}

	public void writeFile(File file) throws FileNotFoundException {
		//	fileOut = new FileOutputStream(file);
	}

	public void login() throws IOException {
		out.writeBytes("user clausstaffe" + "\r\n");
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

	public ServerListener getEar() {
		return ear;
	}

	public void setEar(ServerListener ear) {
		this.ear = ear;
	}

	public void getPASV(){
		if(getEar().getLine().contains("227 Entering")){
			Main.setActive(false);
			PASV = getEar().getLine().substring(getEar().getLine().indexOf('(')+1, getEar().getLine().indexOf(')'));
			String[] box = PASV.split(",");
			portNumber = Integer.parseInt(box[4])*256 +Integer.parseInt(box[5]);
			System.out.println(portNumber);
		}
	}

	public void setupSocket() throws UnknownHostException, IOException{
		dataSocket = new Socket("127.0.0.1", portNumber);

	}
	public void setupInstreamData() throws IOException{
		dataIn =  new DataInputStream(dataSocket.getInputStream());
		//InputStream inputstream = new FileInputStream("c:\\data\\input-text.txt");


	}
	public void writeFileOutStream() throws FileNotFoundException{
		System.out.println("write a system path for file placement: fx /Users/clausstaffe");
		String fileDest = keyb.nextLine();
		fileOut= new FileOutputStream(new File(fileDest));
	}
	public void readData(String request2) throws IOException, InterruptedException{
		Thread.sleep(50);
		int fileSize;
		
		if(request2.startsWith("LIST")){
			setupSocket();
			out.writeBytes("TYPE A\r\n");
			BufferedReader br = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			out.writeBytes("LIST\r\n");
			String text;
			System.out.println(br.ready());
			while((text = br.readLine()) != null){
				System.out.println(text);
			}
			br.close();
			dataSocket.close();
			Main.setActive(true);
		}

	
		//this one moves a file.
		if(request2.toUpperCase().startsWith("RETR ")){

			setupSocket();

			
			//set connection to binary data  - sends both text and pictures.
			out.writeBytes("TYPE I\r\n");
			System.out.println("PLease write TestKitten.jpeg or store.txt");
			String fileSource = keyb.nextLine();
			out.writeBytes("SIZE "+fileSource+"\r\n");
			Thread.sleep(10);
			System.out.println((getEar().getLine().substring(4,getEar().getLine().length())));

			fileSize =(int) 2 *Integer.parseInt(getEar().getLine().substring(4,getEar().getLine().length()));
			byte[] buf = new byte[fileSize];
			setupInstreamData();
			writeFileOutStream();
			out.writeBytes("RETR "+fileSource+"\r\n");
			Thread.sleep(50);
			System.out.println(dataIn.available());
			int i= 0;
			while(dataIn.available()> 0){
				buf[i] =dataIn.readByte();
				i++;
			}
			//		Path path = Paths.get(fileSource);
			//		buf = Files.readAllBytes(path);
			fileOut.write(buf);
			fileOut.flush();
			fileOut.close();
			dataSocket.close();
			Main.setActive(true);

		}
		

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
