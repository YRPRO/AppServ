package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import vol.LecteurFicherVol;
import vol.Vol;

public class ServiceReservation implements Runnable{
	private Socket client;
	private Thread t;
	private List<Vol> vols;
	
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
		//sout.println("Client vous etes connecter et pres a reserver !");
		
		String reponseAEnvoyer =""; //String pour la réception des messages du client
		String reponseDuClient = ""; //String pour l'envoie du message au client
		
		sout.println("Nouvelle session ");
		sout.flush();
		
		String destination = "";
		String date = "";
		int nbPersonne = 0;
		List<Vol> vols = new ArrayList<Vol>();
		
		while(!reponseDuClient.equals("stop")){
	
			dialogueRechercheVol(sin, sout, destination, date, nbPersonne);
			vols = rechercheVol(destination, date, nbPersonne);
			sout.println(vols);
			
		}
		sout.println("stop");
		//FERMETURE DE LA SOCKET ET TERMINAISON DU THREAD
		this.client.close();
		this.terminer();
	}
	
	
	//A Voir si on garde
	private static void envoieMessage(String message, PrintWriter sout) throws IOException{	
		sout.println(message);
		sout.flush();
		sout.println("AttenteReponse");
		sout.flush();
	}
	
	
	private static void dialogueRechercheVol(BufferedReader sin, PrintWriter sout, String destination, String date, int nbPersonne) throws IOException{
		
		envoieMessage("Quel est votre déstination ?", sout);
		destination = sin.readLine();
		while(!verifDestination(destination)){
			envoieMessage("Veuillez entrer une destination valide", sout);
			destination = sin.readLine();
		}
		
		envoieMessage("Quel est la date de départ ?", sout);
		date = sin.readLine();
		while(!verifDate(date)){
			envoieMessage("Veuillez entrer une date valide", sout);
			date = sin.readLine();
		}
		
		envoieMessage("Combien de place sougaitait vous réserver", sout);
		nbPersonne = Integer.parseInt(sin.readLine());
		
		while(!verifNbPlace(nbPersonne)){
			envoieMessage("Veuillez entrer un nombre de place valide", sout);
			nbPersonne = Integer.parseInt(sin.readLine());
		}
	}
	
	
	private static boolean verifDestination(String destination){
		return (destination.length() != 0);
	}
	private static boolean verifDate(String date){	
		return (date.length()!=0 && date.matches("^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$")) ;
	}
	private static boolean verifNbPlace(int nbPlace){
		return nbPlace !=0;
	}
	
	private List<Vol> rechercheVol(String destination,String date,int nbPlace){
		List<Vol> volSelectionner = new ArrayList<Vol>(); 
		for(Vol v : this.vols){
			if(v.getDestination().equalsIgnoreCase(destination) && v.getDate().equalsIgnoreCase(date) 
					&& v.getNbPlace() >= nbPlace)
				volSelectionner.add(v);
		}
		return volSelectionner;
	}

}
