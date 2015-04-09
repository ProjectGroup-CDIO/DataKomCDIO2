package weightSim;


import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Simulator {

	static ServerSocket serverSock;
	static Scanner keyb = new Scanner(System.in);
	private static SimInput simIn = new SimInput();
	private static ArrayList<ClientInput> clientList = new ArrayList<ClientInput>();
	private static double brutto = 0;
	private static double tara = 0;
	private static String instruktionsDisplay = "";
	private static String weightDisplay = "";
	private static int portdst = 8000;
	private static Socket sock;
	private static boolean connectListener = true;

	public static void main(String[] args) throws IOException {
		System.out.println("Indtast ønsket port# eller tryk ENTER for port 8000");

		String input;
		int inputInt = 0;

		while(true){
			input = keyb.nextLine();
			if(input.equals("")){
				serverSock = new ServerSocket(portdst);
				break;
			}
			try {
				inputInt = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Error: "+e.getMessage());
			}
			//In case of NumberFormatException inputInt will be 0, and if-statement wont be entered
			if(inputInt >= 1 && inputInt <= 65536) {
				portdst = inputInt;
				try {
					serverSock = new ServerSocket(portdst);
					break;
				} catch (BindException e1) {
					System.out.println("Error: "+e1.getMessage()+".. Try again");
					//e1.printStackTrace();
				}
			} else System.out.println("Port# skal være 1 - 65536");
		}

		System.out.println("Server venter på forbindelse på port " + portdst);
		//This halts the program until someone makes a connection with it.

		simIn.start();

		while(connectListener){
			try {
				Socket sock = serverSock.accept();
				ClientInput clientIn = new ClientInput(sock);
				clientIn.start();	
				clientList.add(clientIn);
			} catch (SocketException e) {

				//e.printStackTrace();
			}
			System.out.println("# of clients connected: "+clientList.size());
		}
	}
	public static void stopLoop() {
		connectListener = false;

	}
	/**
	 * prints the menu
	 */
	public static void printmenu(){
		for (int i=0;i<25;i++) System.out.println(" ");
		System.out.println("*************************************************");
		System.out.println("Netto: " + (brutto-tara)+ " kg"                   );
		System.out.println("Weightdisplay: " + weightDisplay);
		System.out.println("Instruktionsdisplay: " +  instruktionsDisplay    );
		System.out.println("*************************************************");
		System.out.println("                                                 ");
		System.out.println("                                                 ");
		System.out.println("Debug info:                                      ");
		System.out.print("Hooked up to: ");
		try {
			for(ClientInput client : clientList) {
				System.out.println("\n"+client.getSocket().getInetAddress());
			}
		} catch (NullPointerException e) {
			System.out.println("Hooked up to n/a");
		} catch (Exception e) {
			//e.printStackTrace();
		}
		System.out.println("Brutto: " + (brutto)+ " kg"                       );
		System.out.println("                                                 ");
		System.out.println("Denne vegt simulator lytter på ordrene           ");
		System.out.println("D, RM20 8, S, T, B, Q                                ");
		System.out.println("paa kommunikationsporten.                        ");
		System.out.println("******");
		System.out.println("Tast T for tara (svarende til knaptryk på vægt)");
		System.out.println("Tast B for ny brutto (svarende til at belastningen på vægt ændres)");
		System.out.println("Tast Q for at afslutte program program");
		System.out.println("Indtast (T/B/Q for knaptryk / brutto ændring / quit)");
		System.out.print  ("Tast her: ");
	}

	public static ServerSocket getListener() {
		return serverSock;
	}

	public static Socket getSock() {
		return sock;
	}

	public static int getPortdst() {
		return portdst;
	}

	public static void setPortdst(int portdst) {
		Simulator.portdst = portdst;
	}

	public static synchronized String getInstruktionsDisplay() {
		return instruktionsDisplay;
	}

	public static synchronized void setInstruktionsDisplay(String instruktionsDisplay) {
		Simulator.instruktionsDisplay = instruktionsDisplay;
	}

	public static synchronized double getBrutto() {
		return brutto;
	}

	public static synchronized void setBrutto(double brutto) {
		Simulator.brutto = brutto;
	}

	public static synchronized double getTara() {
		return tara;
	}

	public static synchronized void setTara(double tara) {
		Simulator.tara = tara;
	}

	public static synchronized void setWeightDisplay(String input) {
		weightDisplay = input;

	}
	public static synchronized void closeSockets(){
		try {
			serverSock.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}




}
