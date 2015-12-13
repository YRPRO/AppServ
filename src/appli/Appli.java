package appli;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import vol.LecteurFicherVol;
import vol.Vol;

public class Appli {
	public static void main(String[] args) {
		//TEST
		//creation d'une liste de vol et affichage
		//chemin du fichier
		String chemin = "vols.txt";
		ArrayList<Vol> listeVols = new ArrayList<Vol>();
		try {
			listeVols = LecteurFicherVol.ficherTextToList(chemin);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(listeVols.toString());
	}
}
