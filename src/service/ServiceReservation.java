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
	/**
	 * Méthode contenant le traitement du service 
	 * @throws IOException
	 */
	public void traitement() throws IOException{
		//Streams pour le serveur
		BufferedReader sin = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter sout = new PrintWriter(client.getOutputStream(), true);
		//sout.println("Client vous etes connecter et pres a reserver !");
		
		//String reponseAEnvoyer =""; //String pour la réception des messages du client
		//String reponseDuClient = ""; //String pour l'envoie du message au client
		
		sout.println("Nouvelle session ");
		sout.flush();
		
		String destination = "";
		String date = "";
		int nbPersonne = 0;
		List<Vol> volsTrouver = new ArrayList<Vol>();
		//envoi des vol dispo
		sout.println("vols dispo \n" + this.vols.toString());
		sout.flush();
		do {
			destination = dialogueDemandeDestination(sin, sout);
			date = dialogueDemandeDate(sin, sout);
			nbPersonne = dialogueDemandeNbPlace(sin, sout);
			volsTrouver = rechercheVol(destination, date, nbPersonne);
			
			if(!volsTrouver.isEmpty())
				sout.println(volsTrouver.toString());
			else
				sout.println("Aucun vol n'est disponible pour vos critères ");
			
			
			
		} while(dialogueDemandeNouvelleRecherche(sin, sout));
		sout.println("Stop");
		//FERMETURE DE LA SOCKET ET TERMINAISON DU THREAD
		this.client.close();
		sout.close();
		sin.close();
		this.terminer();
	}
	
	
	//A Voir si on garde
	private void envoieMessage(String message, PrintWriter sout) throws IOException{	
		sout.println(message);
		sout.flush();
		sout.println("AttenteReponse");
		sout.flush();
	}
	
	/**
	 * Methode permetant la demande de la destination au client
	 * @param sin le PrintWriter attribué à la socket 
	 * @param sout le BufferedReader attribué à la socket 
	 * @return la destination (String) une fois saisie et verifiée
	 * @throws IOException
	 */
	private String dialogueDemandeDestination(BufferedReader sin, PrintWriter sout) throws IOException{
		String destination;
		envoieMessage("Quel est votre déstination ?", sout);
		destination = sin.readLine();
		while(!verifDestination(destination)){
			envoieMessage("Veuillez entrer une destination valide", sout);
			destination = sin.readLine();
		}
		return destination;
	}
	/**
	 * Methode permetant la demande de la date de depart au client
	 * @param sin le PrintWriter attribué à la socket 
	 * @param sout le BufferedReader attribué à la socket 
	 * @return la date (String) une fois saisie et verifiée
	 * @throws IOException
	 */
	private String dialogueDemandeDate(BufferedReader sin, PrintWriter sout) throws IOException{
		String date;
		envoieMessage("Quel est la date de départ ?", sout);
		date = sin.readLine();
		while(!verifDate(date)){
			envoieMessage("Veuillez entrer une date valide", sout);
			date = sin.readLine();
		}
		return date;
	}
	/**
	 * Methode permetant la demande du nombre de place à reserver au client
	 * @param sin le PrintWriter attribué à la socket 
	 * @param sout le BufferedReader attribué à la socket 
	 * @return le nombre de place (int) une fois saisie et verifiée
	 * @throws IOException
	 */
	private int dialogueDemandeNbPlace(BufferedReader sin, PrintWriter sout) throws IOException{
		int nbPersonne ;
		envoieMessage("Combien de place(s) souhaitez vous réserver", sout);
		nbPersonne = Integer.parseInt(sin.readLine());
		
		while(!verifNbPlace(nbPersonne)){
			envoieMessage("Veuillez entrer un nombre de place valide", sout);
			nbPersonne = Integer.parseInt(sin.readLine());
		}
		return nbPersonne;
	}
	/**
	 * Methode permetant la demande d'arret au client
	 * @param sin le PrintWriter attribué à la socket 
	 * @param sout le BufferedReader attribué à la socket 
	 * @return boolean true si le client veut une nouvelle recherche false sinon
	 * @throws IOException
	 */
	private boolean dialogueDemandeNouvelleRecherche(BufferedReader sin, PrintWriter sout) throws IOException {
		String reponse;
		envoieMessage("Voulez-vous faire une nouvelle recherche (oui ou non) ?", sout);
		reponse = sin.readLine();
		sout.println(reponse);
		while((!reponse.equals("oui")) && (!reponse.equals("non"))){
			envoieMessage("Vous devez répondre par oui ou non", sout);
			reponse = sin.readLine();
		}
		if(reponse.equals("oui"))
			return true;
		return false;
		
	}
	
	
	/**
	 * Permet la verification (au niveau syntaxique) de la destination entrer par le client
	 * @param destination la chaine à verifier 
	 * @return true si la syntaxe est correcte false sinon
	 */
	private  boolean verifDestination(String destination){
		return (destination.length() != 0);
	}
	/**
	 * Permet la verification (au niveau syntaxique) avec une éxpression régulière de la date entrer par le client
	 * @param date la chaine à verifier
	 * @return true si la syntaxe est correcte false sinon
	 */
	private  boolean verifDate(String date){	
		//ancienne regex
		//"^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$"
		String dateRegex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
		return (date.length()!=0 && date.matches(dateRegex) );
	}
	/**
	 * Permet la verification (au niveau syntaxique) du nombre de place(s) entrer par le client
	 * @param nbPlace la valeur à verifier
	 * @return true si la syntaxe est correcte false sinon
	 */
	private  boolean verifNbPlace(int nbPlace){
		return nbPlace !=0;
	}
	/**
	 * méthode permetant de recherche les vols correspondant aux critères du client
	 * @param destination (String)
	 * @param date (String)
	 * @param nbPlace (int)
	 * @return une liste de vol contenant l'ensemble des vols correspondant aux critères du client
	 */
	private List<Vol> rechercheVol(String destination,String date,int nbPlace){
		List<Vol> volSelectionner = new ArrayList<Vol>(); 
		for(Vol v : this.vols){
			if(v.getDestination().equalsIgnoreCase(destination) && v.getDate().equalsIgnoreCase(date) && v.getNbPlace() >= nbPlace)
				volSelectionner.add(v);
		}
		return volSelectionner;
	}

}
