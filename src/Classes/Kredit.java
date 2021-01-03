package classes;

import interfaces.DatabaseConnect;

public class Kredit implements DatabaseConnect
{
    // ------------
    // Klassenattribute
    // ------------
    private int kreditsumme;
    private int kreditnummer;
    private int laufzeitTage;
    private double zinssatz = 0.015;
    
    // ------------
    // Konstruktoren
    // ------------

    public Kredit(int kreditantragsnummer, int wunschsumme, int laufzeitTage)
    {
        this.kreditsumme = wunschsumme;
        this.kreditnummer = kreditantragsnummer;
        this.laufzeitTage = laufzeitTage;
    }



	// ------------
    // Methoden
    // ------------
    
	public void run() // TO BE IMPLEMENTED
    {

    }
    
    
}
