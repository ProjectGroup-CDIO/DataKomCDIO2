package ftpClient;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class UserCommandLog {
	public static void UpdateLog(String produkt, String gamleVaegt, String nyVaegt){

		Calendar cal = Calendar.getInstance();
    	cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("LogFile.txt", true)))) {
			    out.println(sdf.format("Operatøren har ændret vaegten for "+produkt+" fra "+gamleVaegt+" til "+nyVaegt+", "+cal.getTime()));
			}catch (IOException e) {
			    System.out.println("Exception occured no log created.");
			
			}
	
		}
		
	}
