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
		} catch (IOException e) {
			System.out.println("Erreur de lancement serveur sur le port choisi");
		}
		
		
		
	}

}
