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

	static int ewol[] = new int[12];// grupa która odpadnie
	static int ewolBest1[] = new int[12];// pierwsza grupa do krzy¿owania siê
	static int ewolBest2[] = new int[12];// druga grupa do krzy¿owania siê
	
	static String[] chromosomChild = new String[12];// do przechowania nowych egzemplarzy
	static String[] chromosomParent1 = new String[12];// do przechowania jednego rodzica
	static String[] chromosomParent2 = new String[12];// do przechowania drugiego rodzica
	
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
		for (int i = 0; i < LICZEBNOSC_POPULACJI; i++) {
			System.out.println("Wynik: " + chromosomWynik[0][i]
					+ "\nChromosom: " + chromosomWynik[1][i]);
			wynik_global = wynik_global
					+ Integer.parseInt(chromosomWynik[0][i]);
		}

		srednia = wynik_global / LICZEBNOSC_POPULACJI;
		System.out.println("Srednia: " + srednia);	
		
		//dokonuje selekcji 12-stu najs³abszych osobników
		selekcja();
		
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
	
	public static void selekcja() {
		int i;
		int trzecia_czesc_populacji = LICZEBNOSC_POPULACJI/3;
		
		sortowanieBabelkowe(wyniki);
		
		// pierwsza grupa do krzy¿owania
		for (i = 0; i < trzecia_czesc_populacji; i++)
			ewolBest1[i] = wyniki[i];

		// druga grupa do krzy¿owania
		for (i = trzecia_czesc_populacji; i < 2*trzecia_czesc_populacji; i++)
			ewolBest2[i - trzecia_czesc_populacji] = wyniki[i];
		
		// grupa, która odpadnie
		for (i = 2*trzecia_czesc_populacji; i < LICZEBNOSC_POPULACJI; i++) 
			ewol[i - 2*trzecia_czesc_populacji] = wyniki[i];

		
	}
	
	public static void krzyzowanie() {
		int i;
		for (i = 0; i < LICZEBNOSC_POPULACJI; i++) {
			int j = 0;
			while (j < LICZEBNOSC_POPULACJI/3) {
				if (ewolBest1[j] == Integer.parseInt(chromosomWynik[0][i])) {
					chromosomParent1[j] = chromosomWynik[1][i];

				} else if (ewolBest2[j] == Integer.parseInt(chromosomWynik[0][i])) {
					chromosomParent2[j] = chromosomWynik[1][i];
				}
				j++;
			}
		}
		
		for (i = 0; i < LICZEBNOSC_POPULACJI/3; i++) {
			int p1 = rnd.nextInt(ILOSC_ITERACJI/2);
			int p2 = ILOSC_ITERACJI - p1;
			chromosomChild[i] = chromosomParent1[i].substring(0, p1)
					+ chromosomParent2[i].substring(p1, p2)
					+ chromosomParent1[i].substring(p2,
							chromosomParent1[i].length());
		}

	}
	
	// metoda sortuje elementy tablicy przekazanej jako parametr
		public static void sortowanieBabelkowe(int[] wejscie) {
			// porównujemy pary elementów w tablicy
			for (int i = wejscie.length - 1; i > 1; i--) {
				for (int j = 0; j < i; j++) {
					// jeœli nie s¹ one odpowiednio uporz¹dkowane
					// zamieniamy je miejscami
					if (wejscie[j] > wejscie[j + 1]) {
						// zamiana elementów
						int x = wejscie[j];
						wejscie[j] = wejscie[j + 1];
						wejscie[j + 1] = x;
					}
				}
			}
		}


}