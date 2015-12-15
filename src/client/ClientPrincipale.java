package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientPrincipale implements Runnable{
	
	private Socket socketClient;
	private Thread t;
	
	public ClientPrincipale(String adr,int port) throws UnknownHostException, IOException{
		this.socketClient = new Socket(adr,port);
		this.t = new Thread(this);
	}

	@Override
	public void run() {
		try {
			this.traitement();
		} catch (IOException e) {
			System.out.println("Erreur : probleme dans le stream du client");
		}
	}
	
	public void lancer(){
		this.t.start();
	}
	
	public void terminer(){
		this.t.interrupt();
	}
	
	public void traitement() throws IOException{
		//creation de stream d'acces au service
		BufferedReader sin = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		PrintWriter sout = new PrintWriter(socketClient.getOutputStream(), true);
		
		
		//REPONSE VERS LE SERVEUR
		while(true){
			String message = sin.readLine();
			System.out.println("Serveur : " +message);
			Scanner sc = new Scanner(System.in);
			String reponse = sc.nextLine();
			sout.println(reponse);
			sout.flush();
		}
	}
}
