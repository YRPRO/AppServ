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
	
	public void traitement() throws IOException{
		//creation de stream d'acces au service
		//Streams pour le serveur
		BufferedReader sin = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		PrintWriter sout = new PrintWriter(socketClient.getOutputStream(), true);
		while(!socketClient.isClosed()){
			String rep = sin.readLine();
			System.out.println(rep);
		}
		/*Scanner sc = new Scanner(System.in);
		String reponseClient;
		//attente de la reponse
		System.out.println(sin.readLine());
		reponseClient = sc.next();
		//envoi de la reponse
		sout.print(reponseClient);
		
		sc.close();*/
	}
}
