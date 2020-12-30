package Classes;

import Interfaces.Geschäftsführer;
import Interfaces.Sachbearbeiter;
import Interfaces.Vorgesetzter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MitarbeiterImpl extends UnicastRemoteObject implements Vorgesetzter, Sachbearbeiter, Geschäftsführer
{
    // ------------
    // Klassenattribute
    // ------------
    
    private int mitarbeiternummer;
    private String nachname;
    private String vorname;
    private String geburtsdatum;

    // ------------
    // Konstruktoren
    // ------------

    public MitarbeiterImpl(String nachname, String vorname, String geburtsdatum, int mitarbeiternummer) throws RemoteException
    {
        this.nachname = nachname;
        this.vorname = vorname;
        this.geburtsdatum = geburtsdatum;
        this.mitarbeiternummer = mitarbeiternummer;
    }
    

    // ------------
    // Methoden
    // ------------

    public void run() // TO BE IMPLEMENTED
    {
        
	}


    // ------------
    // Interfaces Implementierung
    // ------------

    public Vorschlag erstelleVorschlag() // TO BE IMPLEMENTED
    {
        return null;
    }

    public boolean überprüfeVorschlag() // TO BE IMPLEMENTED
    {
        return false;
    }        

    public boolean gemeinschaftlicheÜberprüfung(Vorschlag vorschlagDesVorgesetzten) // TO BE IMPLEMENTED
    {
        return false;
    }
}
