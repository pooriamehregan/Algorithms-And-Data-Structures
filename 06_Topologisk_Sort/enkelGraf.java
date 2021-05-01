import java.io.*;
import java.util.Scanner;

// Enkel implementasjon av uvektet graf med nabomatrise
// Nodene har bare en tegnstreng som data
//
public class enkelGraf
{
    int n;              // Antall noder i grafen
    boolean nabo[][];   // Nabomatrise
    String data[];      // Data i hver node

    // Konstruktør, leser inn grafdata fra en fil
    //
    public enkelGraf(String filNavn)
    {
	les(filNavn);
    }

    // Metode som returnerer antall noder
    //
    public int antallNoder()
    {
	return n;
    }

    // Innlesning av en graf fra fil
    // Nodene må nummereres fra 0 til n-1
    // Sjekker ikke for feil i data
    //
    public void les(String filNavn)
    {
		// Format for grafdataene:
		//
		// <antall noder>
		// <nodenr.> <dataverdi> <ant. naboer> <nabonr.> <nabonr.> ...
		// <nodenr.> <dataverdi> <ant. naboer> <nabonr.> <nabonr.> ...
		//             .
		//             .
		//             .
		// <nodenr.> <dataverdi> <ant. naboer> <nabonr.> <nabonr.> ...

		try
		{
			Scanner in = new Scanner(new File(filNavn));

			// Leser antall noder i grafen
			n = in.nextInt();

			// Oppretter arrayene som lagrer grafen
			nabo = new boolean[n][n];
			data = new String[n];

			// Initierer hele nabomatrisen til false, men med true på
			// hoveddiagonalen (nodene er "nabo med seg selv")
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					nabo[i][j] = i == j;


			// Leser en linje med data for hver grafnode
			for (int i = 0; i < n; i++)
			{
				// Leser nodenummeret og data i noden
				int nodeNr = in.nextInt();
				data[nodeNr] = in.next();

				// Leser antall naboer for denne noden
				int antNaboer = in.nextInt();

				// Leser og legger inn alle naboene i nabomatrisen
				for (int j = 0; j < antNaboer; j++)
				{
					int naboNr = in.nextInt();
					nabo[nodeNr][naboNr] = true;
				}
			}
		}
			catch (Exception e)
		{
			 System.err.println(e);
			 System.exit(1);
		}
    }

    // Utskrift av grafen
    //
    public void skriv()
    {
		for (int i = 0; i < n; i++)
		{
			System.out.print(data[i] + ": ");
			for (int j = 0; j < n; j++)
				if (nabo[i][j] && i!=j)
					System.out.print(data[j] + " ");
			System.out.println();
		}
    }

    // Testprogram
    //
    public static void main(String args[])
    {
		// Leser navnet på en fil med grafdata som input fra
		// kommandolinjen
		String filNavn = " ";
		try
		{
			 if (args.length != 1)
			 throw new IOException("Mangler filnavn");
			 filNavn = args[0];
		}
		catch (Exception e)
		{
			 System.err.println(e);
			 System.exit(1);
		}

//		// Oppretter ny graf
//		enkelGraf G = new enkelGraf(filNavn);
//
//		// Skriver ut innholdet av grafen
//		G.skriv();

		topSort ts = new topSort(filNavn);
		ts.findAndPrint();
    }
}
