package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
		//variables contenant les infos (destination, date et nombre de place)
		String clientDestination;
		String clientDate;
		int clientNbPlace;
		//message d'accueil + instruction
		sout.print("Bienvenue dans notre service de réservation.\n"+"Veuillez suivre les instructions \n" );
		sout.print("Quelle est la destination ? \n");
		clientDestination = sin.readLine();
		sout.print("Vous avez choisi la destination : " +clientDestination);
		//recuperation des info necessaire à la réservation
		
		
	}
	
	

}
