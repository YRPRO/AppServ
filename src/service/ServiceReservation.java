package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sun.misc.Regexp;
import vol.LecteurFicherVol;
import vol.Vol;

public class ServiceReservation implements Runnable{
	private Socket client;
	private Thread t;
	private List vols;
	
	public  ServiceReservation(Socket s) throws FileNotFoundException {
		this.client = s;
		this.t = new Thread(this);
		this.vols = LecteurFicherVol.ficherTextToList("vols.txt");
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
		
		sout.println("Client vous etes connecter et prer à reserver !");
		String messageServeur ="";
		String destination = "";
		
		
		
		//FERMETURE DE LA SOCKET ET TERMINAISON DU THREAD
		this.client.close();
		this.terminer();
	}
	
	private boolean verifDestination(String destination){
		return (destination.length() != 0);
	}
	private boolean verifDate(String date){	
		return (date.length()!=0 && date.matches("^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$")) ;
	}
	private boolean verfNbPlace(int nbPlace){
		return nbPlace !=0;
	}
	
	private List rechercheVol(String destination,String date,int nbPlace){
		ArrayList<Vol> volSelectionner = new ArrayList<Vol>(); 
		for(Vol v : (ArrayList<Vol>) this.vols){
			if(v.getDestination().equalsIgnoreCase(destination) && v.getDate().equalsIgnoreCase(date) 
					&& v.getNbPlace() >= nbPlace)
				volSelectionner.add(v);
		}
		return volSelectionner;
	}
	
	
}
