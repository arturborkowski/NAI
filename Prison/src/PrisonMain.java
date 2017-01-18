import java.util.Random;

/*
 Projekt NAI
 Artur Borkowski
*/

public class PrisonMain {
	
	static final int EPOKI = 30; // liczba epok
	static final int LICZEBNOSC_POPULACJI = 36;
	static final int ILOSC_ITERACJI = 100;
	
		
	static String[] chromosom = new String[LICZEBNOSC_POPULACJI];
	
	static String[][] stan = new String[2][LICZEBNOSC_POPULACJI/2];// do przechowywania stanów

	static int[] populacja = new int[LICZEBNOSC_POPULACJI]; // liczebnoœæ populacji
	static int[] wyniki = new int[LICZEBNOSC_POPULACJI]; // tablica przechowyjaca wyniki do posortowania
	static int[][] wynikiEpoki = new int[LICZEBNOSC_POPULACJI][EPOKI];// przechowuej wyniki zaleznie od epoki w których siê zapodzia³y

	static String[][] chromosomWynik = new String[2][LICZEBNOSC_POPULACJI];// tablica przechowujaca kombinacje chromosom-wynik

	static Random rnd = new Random(); // 0 zdradzaj, 1 kooperuj
	
	
/******************** FUNKCJA MAIN ***********************/
	public static void main(String args[]) {

		int wynik_global = 0;
		double srednia = 0;
		incjalizacjaChromosomów();
		przypiszWartosciLosowe();
		System.out.println("Incjalne wartosci chromosomow i wynikow: ");
		wyswietlWynikChromosomu();

		// rozgrywa incjalny turniej
		turniej();
		System.out.println("Wartosci chromosomow i wynikow po turnieju: ");
		for (int j = 0; j < LICZEBNOSC_POPULACJI; j++) {
			System.out.println("Wynik: " + chromosomWynik[0][j]
					+ "\nChromosom: " + chromosomWynik[1][j]);
			wynik_global = wynik_global
					+ Integer.parseInt(chromosomWynik[0][j]);
			srednia = wynik_global / LICZEBNOSC_POPULACJI;
			System.out.println("Wynik: " + chromosomWynik[0][j]
					+ "\nChromosom: " + chromosomWynik[1][j]);
		}

		srednia = wynik_global / LICZEBNOSC_POPULACJI;
		System.out.println("Srednia: " + srednia);		
	}
/******************** KONIEC FUNKCJI MAIN ***********************/


	public static void incjalizacjaChromosomów() {
		for (int i = 0; i < LICZEBNOSC_POPULACJI; i++) {
			chromosom[i] = "";
		}
	}

	 
	/*funkcja incjalna, przypisuje populacji losowe wartosci na stany wejsciowe dla wszystkich osobników*/
	public static void przypiszWartosciLosowe() {
		for (int i = 0; i < LICZEBNOSC_POPULACJI; i++) {
			populacja[i] = rnd.nextInt(2);

		}
	}
	
	public static void wyswietlWynikChromosomu() {
		for (int j = 0; j < LICZEBNOSC_POPULACJI; j++)
			System.out.println("Wynik: " + chromosomWynik[0][j]
					+ "\nChromosom: " + chromosomWynik[1][j]);
	}
	
	public static void zbierajWyniki(int epoka) {
		for (int i = 0; i < LICZEBNOSC_POPULACJI; i++) {
			wynikiEpoki[i][epoka] = wyniki[i];
		}
	}


	public static void turniej() {
		int polowaPopulacji = LICZEBNOSC_POPULACJI/2;
		
		for (int j = 0; j < polowaPopulacji; j++) {
			for (int i = 0; i < ILOSC_ITERACJI; i++) {
				
				populacja[j + polowaPopulacji] = rnd.nextInt(2);// przypisuje wartosc losowa dla drugiego osobnika
				
				if ((populacja[j] == populacja[j + polowaPopulacji]) && populacja[j] == 1) {
					wyniki[j] += 3;
					wyniki[j + polowaPopulacji] += 3;
					chromosom[j] += populacja[j];
					chromosom[j + polowaPopulacji] += populacja[j + polowaPopulacji];
					stan[0][j] = "" + populacja[j];// aktualna odpowiedz jako aktualna decyzja
					stan[1][j] = "" + populacja[j + polowaPopulacji];// aktualna odpowiedz jako aktualna decyzja

				} else if ((populacja[j] == populacja[j + polowaPopulacji])
						&& populacja[j] == 0) {
					wyniki[j] += 1;
					wyniki[j + polowaPopulacji] += 1;
					chromosom[j] += populacja[j];
					chromosom[j + polowaPopulacji] += populacja[j + polowaPopulacji];
					stan[0][j] = "" + populacja[j];// aktualna odpowiedz jako Aktualna decyzja
					stan[1][j] = "" + populacja[j + polowaPopulacji];// aktualna odpowiedz jako aktualna decyzja

				} else if ((populacja[j] != populacja[j + polowaPopulacji])
						&& populacja[j] == 1) {
					wyniki[j] += 5;
					wyniki[j + polowaPopulacji] = wyniki[j + polowaPopulacji];
					chromosom[j] += populacja[j];
					chromosom[j + polowaPopulacji] += populacja[j + polowaPopulacji];
					stan[0][j] = "" + populacja[j];// aktualna odpowiedz jako aktualna decyzja
					stan[1][j] = "" + populacja[j + polowaPopulacji];// aktualna odpowiedz jako aktualna decyzja

				} else if ((populacja[j] != populacja[j + polowaPopulacji])
						&& populacja[j] == 0) {
					wyniki[j] = wyniki[j];
					wyniki[j + polowaPopulacji] += 5;
					chromosom[j] += populacja[j];
					chromosom[j + polowaPopulacji] += populacja[j + polowaPopulacji];
					stan[0][j] = "" + populacja[j];// aktualna odpowiedz jako aktualna decyzja
					stan[1][j] = "" + populacja[j + polowaPopulacji];// aktualna odpowiedz jako aktualna decyzja
				}

				/*przypisuje odpowiedz wyjsciowa jako wejsciowa populacja[j+1]=rnd.nextInt(2);*/
				populacja[j + 1] = Integer.parseInt(stan[1][j]);

			}

			for (int i = 0; i < LICZEBNOSC_POPULACJI; i++) {
				chromosomWynik[0][i] = Integer.toString(wyniki[i]);
				chromosomWynik[1][i] = chromosom[i];
			}

			zbierajWyniki(0);

		}

	}


}