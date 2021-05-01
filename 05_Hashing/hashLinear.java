import java.io.*;
import java.util.Scanner;

// Hashing av tekststrenger med lineær probing
// Bruker Javas innebygde hashfunksjon for strenger
//
// Enkel og begrenset implementasjon:
//
// - Ingen rehashing ved full tabell
// - Tilbyr bare innsetting og søking
//
public class hashLinear
{
    // Hashlengde
    private int hashLengde;

    // Hashtabell
    private String hashTabell[];

    // Antall elementer lagret i tabellen
    private int n;

    // Antall probes ved innsetting
    private int antProbes;

    // Konstruktør
    // Sjekker ikke for fornuftig verdi av hashlengden
    //
    public hashLinear(int lengde)
    {
	hashLengde = lengde;
	hashTabell = new String[lengde];
	n = 0;
	antProbes = 0;
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

    // Returnerer antall probes ved innsetting
    public int antProbes()
    {
	return antProbes;
    }

    // Hashfunksjon
    int hash(String S)
    { 
	int h = Math.abs(S.hashCode());
	return h % hashLengde;
    }

    // Innsetting av tekststreng med lineær probing
    // Avbryter med feilmelding hvis ledig plass ikke finnes
    //
    void insert(String S)
    {
	// Beregner hashverdien
	int h = hash(S);

	// Lineær probing
	int neste = h;

	while (hashTabell[neste] != null)
	{
	    // Ny probe
	    antProbes++;

	    // Denne indeksen er opptatt, prøver neste
	    neste++;
	    
	    // Wrap-around
	    if (neste >= hashLengde)
		neste = 0;

	    // Hvis vi er kommet tilbake til opprinnelig hashverdi, er
	    // tabellen full og vi gir opp (her ville man normalt
	    // doblet lengden på hashtabellen og gjort en rehashing)
	    if (neste == h)
	    {
		System.err.println("\nHashtabell full, avbryter");
		System.exit(0);
	    }
	}

	// Lagrer tekststrengen på funnet indeks
	hashTabell[neste] = S;

	// Øker antall elementer som er lagret
	n++;
    }

    // Søking etter tekststreng med lineær probing
    // Returnerer true hvis strengen er lagret, false ellers
    //
    boolean search(String S)
    {
	// Beregner hashverdien
	int h = hash(S);

	// Lineær probing
	int neste = h;

	while (hashTabell[neste] != null)
	{
	    // Har vi funnet tekststrengen?
	    if (hashTabell[neste].compareTo(S) == 0)
		return true;
		
	    // Prøver neste mulige
	    neste++;
	    
	    // Wrap-around
	    if (neste >= hashLengde)
		neste = 0;

	    // Hvis vi er kommet tilbake til opprinnelig hashverdi,
	    // finnes ikke strengen i tabellen
	    if (neste == h)
		return false;
	}	

	// Finner ikke strengen, har kommet til en probe som er null
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
	hashLinear hL = new hashLinear(hashLengde);

	// Leser input og hasher alle linjer
	while (input.hasNext())
	{
	    hL.insert(input.nextLine());
	}

	// Skriver ut hashlengde, antall data lest, antall kollisjoner
	// og load factor
	System.out.println("Hashlengde  : " + hashLengde);
	System.out.println("Elementer   : " + hL.antData());
	System.out.printf( "Load factor : %5.3f\n",  hL.loadFactor());
	System.out.println("Probes      : " + hL.antProbes());

	// Et par enkle søk
	String S = "Volkswagen Karmann Ghia";
	if (hL.search(S))
	    System.out.println("\"" + S + "\"" + " finnes i hashtabellen");
	S = "Il Tempo Gigante";
	if (!hL.search(S))
	    System.out.println("\"" + S + "\"" + " finnes ikke i hashtabellen");

    }
}

