package applis;

import java.io.IOException;
import java.net.UnknownHostException;

import client.ClientPrincipale;

public class AppliClient {
	public static void main(String[] args)  {
		//creation d'un client
			try {
				ClientPrincipale client = new ClientPrincipale("127.0.0.1",3000);
				client.lancer();	
			} catch (UnknownHostException e) {
				System.out.println("Erreur : probleme au niveau de la connexion client -> serveur");
			} catch (IOException e) {
				System.out.println("Erreur : probleme au niveau de la connexion client -> serveur");
			}
		
	}

}
