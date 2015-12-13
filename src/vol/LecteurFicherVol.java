package vol;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LecteurFicherVol {
	
	private static final String CARACTERE_SEPARATEUR = ";";
	private static final int NB_PARAM_VOL = 6;
	private static final int INDICE_NUM_VOL = 0;
	private static final int INDICE_DESINATION = 1;
	private static final int INDICE_DATE_VOL = 2;
	private static final int INDICE_NB_PLACE = 3;
	private static final int INDICE_COMPAGNIE = 4;
	private static final int INDICE_PRIX = 5 ;

	public static ArrayList<Vol> ficherTextToList(String chemin) throws FileNotFoundException{
		//creation du fichier 
		File ficher = new File(chemin);
		//creation d'un scanner
		Scanner sc = new Scanner(ficher);
		//liste contenant les vol
		ArrayList<Vol> vols = new ArrayList<Vol>(); 
		while(sc.hasNext()){
			String ligne = sc.nextLine();
			//recuperation des parametre dans un tableau
			String parametre[] = ligne.split(CARACTERE_SEPARATEUR);
			//parametre de creation d'un vol
			int numero = Integer.parseInt( parametre[INDICE_NUM_VOL]);
			String destination = parametre[INDICE_DESINATION];
			String date =parametre[INDICE_DATE_VOL];
			int nbPlace =Integer.parseInt( parametre[INDICE_NB_PLACE]);
			String compagnie = parametre[INDICE_COMPAGNIE];
			float prix =Float.parseFloat(parametre[INDICE_PRIX]);
			
			//creation du vol 
			vols.add(new Vol(numero, destination, date, nbPlace, compagnie, prix));
		}
		//fermeture du scanner
		sc.close();
		//retour de la liste complete des vols disponibles
		return vols;
	}

}
