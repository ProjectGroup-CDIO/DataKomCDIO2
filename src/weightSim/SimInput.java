package weightSim;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SimInput extends Thread {

	private volatile static boolean stop = false;

	public static void stopGracefully (){
		stop = true;
	}

	private Scanner keyb = new Scanner(System.in);


	@Override
	public void run() {
		while(!stop) {
			String input = " ";
			try {
				input = keyb.nextLine();
			} catch (NoSuchElementException e1) {
				System.out.println("Error: "+e1.getMessage());
				//e1.printStackTrace();
			}

			if(!(input.isEmpty())) {
				if(input.toUpperCase().startsWith("T")) {
					Simulator.setTara(Simulator.getBrutto());
					if(String.valueOf(Simulator.getTara()).length() < 7 ){
						System.out.println("T S      " + Simulator.getTara() + "kg"+"\r\n");
					}else{
						System.out.println("You done goofed in the tara!");
					}
					Simulator.printmenu();	
				} else if(input.toUpperCase().startsWith("B")) {
					if(input.length() >= 3) { //tjekker om input er over 3 cifre (uden "B_")
						String temp = input.substring(1, input.length()).trim();
						if(temp.length() < 7) { //tjekker om det trimmede input er under 7 cifre;
							if(temp.contains(".")){
								try {
									Simulator.setBrutto((double) Integer.parseInt(temp));	
								} catch (NumberFormatException e) {
									System.out.println("S");
									//e.printStackTrace();
								}			
							} else {
								try {
									Simulator.setBrutto(Double.parseDouble(temp));	
								} catch (NumberFormatException e) {
									System.out.println("S");
									//e.printStackTrace();
								}
							}

							Simulator.printmenu();
						} 
					} 
				} else if(input.toUpperCase().startsWith("Q")) {
					System.out.println("");
					System.out.println("Program stoppet Q modtaget paa com port");
					try {
						System.in.close();
					} catch (IOException e) {
						System.out.println("Fejl ved System.in.close()");
						//e.printStackTrace();
					}
					SimInput.stopGracefully();
					System.out.close();
					System.exit(0);
				} else if(input.equals("")) {
					continue;
				}
			}	
		}
	}

}
