package ftpClient;

import java.net.Socket;


public class DataConnectionListener extends Thread {

	private int portNumber;
	private Socket dataSocket = null;
//	private ServerSocket serverSocket = new ServerSocket(portNumber);
	FTPClient FTPCOne = null;
	private String PASV = "";
	
	
	
	public DataConnectionListener(FTPClient fTPCOne) {
		FTPCOne = fTPCOne;
	}
	
	public void getPASV(){
		if(FTPCOne.getEar().getLine().contains("227 Entering")){
		PASV = FTPCOne.getEar().getLine().substring(FTPCOne.getEar().getLine().indexOf('(')+1, FTPCOne.getEar().getLine().indexOf(')'));
		System.out.println(PASV);
		for(int i = 0; i< PASV.length(); i++){
			int nrOfdots =0;
			PASV = PASV.substring(i, PASV.length());
			System.out.println(PASV);
				//portNumber = 256*(Integer.parseInt(PASV.substring(0, PASV.lastIndexOf(',')-1)));
						//+Integer.parseInt(PASV.substring(PASV.lastIndexOf(',')+1, PASV.length()));
			
			i++;
		}
		
		System.out.println(PASV);
		//System.out.println(portNumber);
		}
		
		}


	public void run(){
		
		
		while(true){
			
			getPASV();
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
