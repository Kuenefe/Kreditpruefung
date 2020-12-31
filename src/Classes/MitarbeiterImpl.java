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
    private Kreditantrag kreditantrag;

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

    public void erstelleVorschlag(Boolean vorschlag) throws Exception, RemoteException
    {
        kreditantrag.vorschlagHinzufuegen(vorschlag, this);
    }   

    public void gemeinschaftlicheEntscheidungTreffen(Boolean edv, Boolean edg) // TO BE IMPLEMENTED
    {
        if(edv && edg)
        {
            this.kreditantrag.entscheidungHinzufuegen(true);
        }
        else
        {
            this.kreditantrag.entscheidungHinzufuegen(false);
        }
    }

    public void entscheidungTreffen(Boolean entscheidung)
    {
        this.kreditantrag.entscheidungHinzufuegen(entscheidung);
    }

    public void setKreditantrag(Kreditantrag kreditantrag)
    {
        this.kreditantrag = kreditantrag;
    }
}
