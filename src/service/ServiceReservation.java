package service;

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
		// TODO Auto-generated method stub
		
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
