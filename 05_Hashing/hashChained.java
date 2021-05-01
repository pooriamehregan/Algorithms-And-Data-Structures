import java.io.*;
import java.util.Scanner;

// Hashing av tekststrenger med kjeding i lenket liste
// Bruker Javas innebygde hashfunksjon for strenger
//
// Enkel og begrenset implementasjon:
//
// - Ingen rehashing ved full tabell/lange lister
// - Tilbyr bare innsetting og søking
//
public class hashChained
{
    // Indre klasse:
    // Node med data, kjedes sammen i lenkede lister
    //
    private class hashNode
    {
		// Data, en tekststreng
		String data;
		// Neste node i listen
		hashNode neste;

		// Konstruktør for listenoder
		public hashNode(String S, hashNode hN)
		{
			data = S;
			neste = hN;
		}
    }

    // Hashlengde
    private int hashLengde;

    // Hashtabell, pekere til lister
    private hashNode hashTabell[];

    // Antall elementer lagret i tabellen
    private int n;

    // Antall kollisjoner ved innsetting
    private int antKollisjoner;

    // Konstruktør
    // Sjekker ikke for fornuftig verdi av hashlengden
    //
    public hashChained(int lengde)
    {
	hashLengde = lengde;
	hashTabell = new hashNode[lengde];
	n = 0;
	antKollisjoner = 0;
    }

    // Returnerer load factor
    public float loadFactor()
    {
	return ((float) n)/hashLengde;
    }

    // Returnerer antall data i tabellen
    public int antData()
    {
	return n;
    }

    // Returnerer antall kollisjoner ved innsetting
    public int antKollisjoner()
    {
	return antKollisjoner;
    }

    // Hashfunksjon
    int hash(String S)
    { 
		int h = Math.abs(S.hashCode());
		return h % hashLengde;
    }

    // Innsetting av tekststreng med kjeding
    //
    void insert(String S)
    {
		// Beregner hashverdien
		int h = hash(S);

		// Øker antall elementer som er lagret
		n++;

		// Sjekker om kollisjon
		if (hashTabell[h] != null)
			antKollisjoner++;

		// Setter inn ny node først i listen
		hashTabell[h] = new hashNode(S, hashTabell[h]);
    }

    // Søking etter tekststreng i hashtabell med kjeding
    // Returnerer true hvis strengen er lagret, false ellers
    //
    boolean search(String S)
    {
		// Finner listen som S skal ligge i
		hashNode hN = hashTabell[hash(S)];

		// Leter gjennom listen
		while (hN != null)
		{
			// Har vi funnet tekststrengen?
			if (hN.data.compareTo(S) == 0)
			return true;
			// Prøver neste
			hN = hN.neste;
		}
		// Finner ikke strengen, har kommet til slutten av listen
		return false;
    }

    /**
	 * Fjerner S fra hashTabellen hvis tabellen inneholder S.
	 * @param S verdien som skal fjernes.
	 * @return returnerer en boolean true: hvis elementet ble funnet, returnerer false ellers.
	 */
    boolean remove(String S){
    	if (n == 0) return false;

    	int hS = hash(S);
    	hashNode neste = hashTabell[hS];

    	// spesial case: rot er det elementet som skal fjernes
    	if (neste.data.equals(S)){
    		hashTabell[hS] = neste.neste;
    		neste.neste = null;
    		n--;
    		return true;
		}

    	while (neste.neste != null){
    		// hvis neste elementets data er lik S fjern alle pekere fra og til denne hashNoden
    		if (neste.neste.data.equals(S)){
    			hashNode temp = neste.neste;
    			neste.neste = neste.neste.neste;
    			temp.neste = null;
    			n--;
    			return true;
			}
    		neste = neste.neste;
		}

    	return false;
	}

    // Enkelt testprogram:
    // 
    // * Hashlengde gis som input på kommandolinjen
    //
    // * Leser tekststrenger linje for linje fra standard input
    //   og lagrer dem i hashtabellen
    //
    // * Skriver ut litt statistikk etter innsetting
    //
    // * Tester om søk fungerer for et par konstante verdier
    //
    public static void main(String argv[])
    {
		// Hashlengde leses fra kommandolinjen
		int hashLengde = 0;
		Scanner input = new Scanner(System.in);
		try
		{
			 if (argv.length != 1)
			 throw new IOException("Feil: Hashlengde må angis");
			 hashLengde = Integer.parseInt(argv[0]);
			 if (hashLengde < 1 )
			 throw new IOException("Feil: Hashlengde må være større enn 0");
			}
			catch (Exception e)
		{
			 System.err.println(e);
			 System.exit(1);
		}

		// Lager ny hashTabell
		hashChained hC = new hashChained(hashLengde);

		// Leser input og hasher alle linjer
		while (input.hasNext())
		{
			hC.insert(input.nextLine());
		}

		// Skriver ut hashlengde, antall data lest, antall kollisjoner
		// og load factor
		System.out.println("Hashlengde  : " + hashLengde);
		System.out.println("Elementer   : " + hC.antData());
		System.out.printf( "Load factor : %5.3f\n",  hC.loadFactor());
		System.out.println("Kollisjoner : " + hC.antKollisjoner());

		// Et par enkle søk
		String S = "Volkswagen Karmann Ghia";
		if (hC.search(S))
			System.out.println("\n\"" + S + "\"" + " finnes i hashtabellen");
		S = "Il Tempo Gigante";
		if (!hC.search(S))
			System.out.println("\"" + S + "\"" + " finnes ikke i hashtabellen");


		// Et par enkle test for fjerning av elemnter
		S = "Volkswagen Karmann Ghia";
		System.out.println("\nAntall elementer i hashtabellen (foer sletting): " + hC.n);
		if (hC.remove(S)) {
			System.out.println("\"" + S + "\"" + " ble fjernet!");
			System.out.println("Antall elementer i hashtabellen:                 " + hC.n);
		}
		S = "Il Tempo Gigante";
		if (!hC.remove(S)) {
			System.out.println("\"" + S + "\"" + " finnes ikke i hashtabellen");
			System.out.println("Antall elementer i hashtabellen:                 " + hC.n);
		}
    }

}

