package applis;

import java.io.IOException;
import java.net.Socket;

import serveur.Serveur;
import service.ServiceReservation;

public class AppliServeur {
	public static void main(String[] args) {
		//creation d'un serveur
		try {
			Serveur serveurReservation = new Serveur(3000);
			//lancement du serveur 
			serveurReservation.lancer();
			//Boolean de controle pour TEST un seul client
			boolean controle = true;
			//connexion des clients
			while(controle){
				//creation de la socket du client
				Socket socketClient = serveurReservation.getSocketServeur().accept();
				controle = false;
				//creation du service
				ServiceReservation serviceReservation = new ServiceReservation(socketClient);
				//lancement du service
				serviceReservation.lancer();
				serviceReservation.terminer();
				
			}
			//fermeture des threads terminer
			serveurReservation.terminer();
		} catch (IOException e) {
			System.out.println("Erreur de lancement serveur sur le port choisi");
		}
		
		
		
	}

}
