package ftpClient;

import java.io.IOException;
import java.net.UnknownHostException;


public class Test {

	public static void main(String[] args) {
		FTPClient ftp = null;
		try {
			ftp = new FTPClient("127.0.0.1");
		} catch (UnknownHostException e1) {
			 System.out.println(e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e1) {
			 System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
		try {
			ftp.writeFile();
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
