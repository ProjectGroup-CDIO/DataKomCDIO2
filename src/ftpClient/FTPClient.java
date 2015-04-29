/**
 * 
 */
package ftpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


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
		if(request.toUpperCase().equals("1")){
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

		}else if(request.toUpperCase().equals("2")){
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
		if(!getEar().isActive()){
		getEar().start();
		getEar().setActive(true);
		}
	}


	public void printResponse() {
		System.out.println(response);
	}

	public void useResponse() throws UnknownHostException, IOException {

	}

	public void printMenu() {
		System.out.println("Tast 1 for at hente en fil");
		System.out.println("Tast 2 for at se en liste over filer og mapper");		
	}


	public void login() throws IOException {
		Main.setActive(false);
		System.out.println("Please write your pc login name e.g currently active user");
		String userName=keyb.nextLine();
		System.out.println("Please enter the password for the current user: " +userName);
		String passWd = keyb.nextLine();
		
		out.writeBytes("user "+userName  + "\r\n");
		out.writeBytes("pass "+passWd + "\r\n");
		Main.setActive(true);

	}


	public void run() {



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
			System.out.println("Port that stuff is sent over: "+portNumber);
		}
	}

	public void setupSocket() throws UnknownHostException, IOException{
		dataSocket = new Socket(socket.getInetAddress(), portNumber);

	}
	
	public void setupInstreamData() throws IOException{
		dataIn =  new DataInputStream(dataSocket.getInputStream());
		//InputStream inputstream = new FileInputStream("c:\\data\\input-text.txt");


	}
	
public void writeFileOutStream() throws FileNotFoundException{
		System.out.println("write a system path for file placement: fx /Users/username/file.filetype");
		String fileDest = keyb.nextLine();
		fileOut= new FileOutputStream(new File(fileDest));
	}
	
	public void readData(String request2) throws IOException, InterruptedException{
		Thread.sleep(50);
		int fileSize;

		if(request2.startsWith("2")){
			setupSocket();
			out.writeBytes("TYPE A\r\n");
			BufferedReader br = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			out.writeBytes("LIST\r\n");
			String text;
			//checks if the buffered reader is ready for input
			//	System.out.println(br.ready());
			while((text = br.readLine()) != null){
				System.out.println(text);
			}
			br.close();
			dataSocket.close();
			Main.setActive(true);
		}


		//this one moves a file.
		if(request2.toUpperCase().equals("1")){
			setupSocket();
			out.writeBytes("TYPE I\r\n");
			System.out.print("Indtast sti til ønskede fil: ");
			String sti = keyb.nextLine();
			System.out.println();
			//set connection to binary data  - sends both text and pictures.
			out.writeBytes("SIZE "+sti.trim()+"\r\n");
			Thread.sleep(100);
			System.out.println("Byte size of file transfered :"+(getEar().getLine().substring(4,getEar().getLine().length())));
			fileSize =(int) 2 *Integer.parseInt(getEar().getLine().substring(4,getEar().getLine().length()).trim());
			byte[] buf = new byte[fileSize];
			setupInstreamData();
			try {
				writeFileOutStream();
			} catch (FileNotFoundException e) {
				System.out.println("File/Directory not found.");

				//e.printStackTrace();
			}
			out.writeBytes("RETR "+sti.trim()+"\r\n");
			Thread.sleep(50);
			System.out.println("DataAvail: " +dataIn.available());
			int i= 0;
			while(dataIn.available()> 0){
				buf[i] =dataIn.readByte();
				i++;
			}
			//		Path path = Paths.get(fileSource);
			//		buf = Files.readAllBytes(path);
			try {
				fileOut.write(buf);
				fileOut.flush();

				fileOut.close();
				dataSocket.close();
			} catch (Exception e) {
				System.out.println("Error: null");
			}
			Main.setActive(true);


		}


	}

	public void productMani() {
			try {
				BufferedReader storeText = new BufferedReader(new FileReader("Store.txt"));
				String line;String input = "";
				while ((line = storeText.readLine()) != null) input += line + '\n';
			
			    storeText.close();
			    System.out.println(input);			        
			    String vare[] = input.split("\n");
			    int nrOfProducts = vare.length;
			    System.out.println("Vaelg et vare nr");
			    String inline = keyb.nextLine();
			    
			    System.out.println("Du har valgt: "+vare[Integer.parseInt(inline)-1]);
			    String valgt[] = vare[Integer.parseInt(inline)-1].split(",");
			    System.out.println("Vælg ny vaegt for "+valgt[1]);
			    String nyVaegt = keyb.nextLine();
			    String gamleVaegt =valgt[2];
			    valgt[2]=nyVaegt;
			    vare[Integer.parseInt(inline)-1]=valgt[0]+","+valgt[1]+","+valgt[2];
			    
			    //System.out.println(vare[Integer.parseInt(inline)]);
			    FileWriter output = new FileWriter("Store.txt");
			    try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Store.txt", true)))) {
			    	for(int i = 0; nrOfProducts > i; i++){
			    		if(i!=nrOfProducts-1){
			    			out.println(vare[i]);
			    		}else{
			    			out.print(vare[i]);
			    		}
			    	}UserCommandLog.UpdateLog(valgt[1], gamleVaegt, valgt[2]);
			    	
			    }catch (Exception e) {
			    					// TODO Auto-generated catch block
			    					e.printStackTrace();
			    }
			    output.close();
			    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public Socket getSocket() {
		
		return this.socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}


}
