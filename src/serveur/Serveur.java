package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur implements Runnable {
	
	private ServerSocket socketServeur;
	private Thread t;
	
	public Serveur( int port) throws IOException{
		this.socketServeur = new ServerSocket(port);
		this.t = new Thread(this);
		System.out.println("Serveur lancer");
	}
	

	@Override
	public void run() {
		//creation d'un service de reservation de vol
		//SERVICE A CREER
		//SERVICE A LANCER
		while(true){
			//acceptation des connexions par les clients
			try {
				Socket client = this.socketServeur.accept();
			} catch (IOException e) {
				System.out.println(e.toString());
			}
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
	

}
