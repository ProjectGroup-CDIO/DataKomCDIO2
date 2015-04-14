package ftpClient;

public class ServerListener extends Thread {
	public	String line = "";
	public String finalLine = "";
	
	public void run(){
		try {
			//	System.out.println(!(line = in.readLine()).equals(""));
			
			while((line = FTPClient.in.readLine()) != null){
				finalLine += line + "\n";
				System.out.println(line);
				//Thread.sleep(200);
			}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public String getLine() {
		// TODO Auto-generated method stub
		return line;
	}
}
