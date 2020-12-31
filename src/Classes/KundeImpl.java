package Classes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Interfaces.Kunde;

public class KundeImpl extends UnicastRemoteObject implements Kunde
{
    // ------------
    // Klassenattribute
    // ------------

    private int kundennummer;
    private double bonitaet;
    private String nachname;
    private String vorname;
    private String geburtsdatum;
    private Kreditantrag kreditantrag;  // Falls mehrere Kreditanträge angefordert werden kann hier eine Liste erstellt werden
    private Kredit kredit;              // Falls mehrere Kredite werden kann hier eine Liste erstellt werden

    // ------------
    // Konstruktoren
    // ------------

    public KundeImpl(String nachname, String vorname, String geburtsdatum, int kundennummer, double bonitaet) throws RemoteException
    {
        this.nachname = nachname;
        this.vorname = vorname;
        this.geburtsdatum = geburtsdatum;
        this.kundennummer = kundennummer;
        this.bonitaet = bonitaet;
    }
    
    public KundeImpl() throws RemoteException
    {

    }

    // ------------
    // Methoden
    // ------------

    public void erstelleAntrag(int antragsnummer, int wunschsumme) throws RemoteException
    {
        Kreditantrag tempAntrag = new Kreditantrag(antragsnummer, wunschsumme);
        this.kreditantrag = tempAntrag;
    }

    public String getName()
    { 
        return this.nachname;
    }

    public Kreditantrag getKreditantrag()
    {
        return this.kreditantrag;
    }

    public void antragGenehmigen(int laufzeit)
    {
        this.kreditantrag.setGenehmigtTrue();
        Kredit tempKredit = new Kredit(this.kreditantrag.getKreditantragsnummer(), this.kreditantrag.getWunschsumme(), laufzeit);
        this.kredit = tempKredit;
    }

    public Boolean ueberpruefeBonitaet()
    {
        if(this.bonitaet > this.kreditantrag.getWunschsumme())
        {
            return true;
        }
        else
        {
            return false;
        }
        
        //return this.bonitaet > this.kreditantrag.getWunschsumme(); // Das gleiche Ergbnis
    }

    public void run() // TO BE IMPLEMENTED
    {
        
	}
}
