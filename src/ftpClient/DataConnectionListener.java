package ftpClient;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import weightClient.WeightClient;


public class DataConnectionListener extends Thread {

	private int portNumber;
	private Socket dataSocket = null;
	//	private ServerSocket serverSocket = new ServerSocket(portNumber);
	FTPClient FTPCOne = null;
	private String PASV = "";
	private DataInputStream dataIn = null;
	private FileOutputStream fileOut =null;
	private Scanner keyb = new Scanner(System.in);
	private boolean setupCon = false;
	private boolean checkForFileSize = false;


	public DataConnectionListener(FTPClient fTPCOne) {
		FTPCOne = fTPCOne;
	}

	public void getPASV(){
		if(FTPCOne.getEar().getLine().contains("227 Entering")){
			WeightClient.setActive(false);
			PASV = FTPCOne.getEar().getLine().substring(FTPCOne.getEar().getLine().indexOf('(')+1, FTPCOne.getEar().getLine().indexOf(')'));
			//System.out.println(PASV);
			String[] box = PASV.split(",");
			portNumber = Integer.parseInt(box[4])*256 +Integer.parseInt(box[5]);

			//System.out.println(PASV);
			//System.out.println(portNumber);
			//System.out.println(portNumber);
			setupCon = true;

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
	public void readData() throws IOException, InterruptedException{
		
		setupSocket();
		int fileSize;
		//set connection to binary data  - sends both text and pictures.
		FTPCOne.out.writeBytes("TYPE I\r\n");
		System.out.println("PLease write TestKitten.jpeg or store.txt");
		String fileSource = keyb.nextLine();
		FTPCOne.out.writeBytes("SIZE "+fileSource+"\r\n");
		Thread.sleep(10);
		System.out.println((FTPCOne.getEar().getLine().substring(4,FTPCOne.getEar().getLine().length())));

		fileSize =(int) 2 *Integer.parseInt(FTPCOne.getEar().getLine().substring(4,FTPCOne.getEar().getLine().length()));
		byte[] buf = new byte[fileSize];
		setupInstreamData();
		writeFileOutStream();
		FTPCOne.out.writeBytes("RETR "+fileSource+"\r\n");
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
		setupCon = false;



	}
	public void getFileSize(){
		if (checkForFileSize){
			if(FTPCOne.getEar().getLine().contains("file")){

			}
		}
	}


	public void run(){
		while(true){
		//	getPASV();
			if(setupCon){
				try {
				//	setupSocket();
					readData();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(Exception e){
					e.printStackTrace();
				}


			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void printPASV() {

		System.out.println(PASV);

	}


}
