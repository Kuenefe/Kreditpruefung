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

    public Kredit(int kreditantragsnummer, int kreditsumme, int laufzeitTage)
    {
        this.kreditsumme = kreditsumme;
        this.kreditnummer = kreditantragsnummer;
        this.laufzeitTage = laufzeitTage;
    }

	// ------------
    // Methoden
    // ------------
    
    // ------------
    // Interfaces Implementierung
    // ------------
	public void run() // TO BE IMPLEMENTED
    {
        
    }

    // ------------
    // Getter und Setter
    // ------------

    public int getKreditantragsnummer()
    {
        return this.kreditnummer;
    }

    public int getLaufzeit()
    {
        return this.laufzeitTage;
    }

    public double getZinssatz()
    {
        return this.zinssatz;
    }   
    
}
