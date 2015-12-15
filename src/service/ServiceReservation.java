package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServiceReservation implements Runnable{
	private Socket client;
	private Thread t;
	
	public  ServiceReservation(Socket s) {
		this.client = s;
		this.t = new Thread(this);
	}
	

	@Override
	public void run() {
		try {
			this.traitement();
		} catch (IOException e) {
			System.out.println("Erreur : Probleme au niveau des stream dans le service reservation");
		}
	}
	/**
	 * methode de lancement du thread
	 */
	public void lancer(){
		this.t.start();
	}
	/**
	 * methode d'interruption du thread
	 */
	public void terminer(){
		this.t.interrupt();
	}
	
	public void traitement() throws IOException{
		//Streams pour le serveur
		BufferedReader sin = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter sout = new PrintWriter(client.getOutputStream(), true);
		//sout.println("Client vous etes connecter et pres a reserver !");
		String reponse ="";
		String reponseClient = "";
		sout.println("Nouvelle session ");
		sout.flush();
		while(!reponseClient.equals("stop")){
			reponseClient = sin.readLine();
			System.out.println("Client : " + reponseClient);
			Scanner sc = new Scanner(System.in);
			reponse = sc.nextLine();
			sout.println(reponse);
			sout.flush();
		}
		//FERMETURE DE LA SOCKET ET TERMINAISON DU THREAD
		this.client.close();
		this.terminer();
		
		
	}
	
	

}
