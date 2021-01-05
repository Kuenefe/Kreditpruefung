package classes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

import interfaces.DatabaseConnect;
import interfaces.Kunde;

public class KundeImpl extends UnicastRemoteObject implements Kunde, DatabaseConnect
{
    // ------------
    // Klassenattribute
    // ------------

    private int kundennummer;
    private double bonitaet;
    private String nachname;
    private String vorname;
    private Kreditantrag kreditantrag;  // Falls mehrere Kreditanträge angefordert werden kann hier eine Liste erstellt werden
    private Kredit kredit;              // Falls mehrere Kredite werden kann hier eine Liste erstellt werden

    // ------------
    // Konstruktoren
    // ------------

    public KundeImpl(String nachname, String vorname, int kundennummer, double bonitaet) throws RemoteException
    {
        this.nachname = nachname;
        this.vorname = vorname;
        this.kundennummer = kundennummer;
        this.bonitaet = bonitaet;
    }

    // ------------
    // Methoden
    // ------------
    
    public void antragGenehmigen(int laufzeit)
    {
        this.kreditantrag.setGenehmigtTrue();
        Kredit tempKredit = new Kredit(this.kreditantrag.getKreditantragsnummer(), this.kreditantrag.getKreditsumme(), laufzeit);
        this.kredit = tempKredit;
    }

    public Boolean ueberpruefeBonitaet()
    {
        if(this.bonitaet > this.kreditantrag.getKreditsumme())
        {
            return true;
        }
        else
        {
            return false;
        }
        
        //return this.bonitaet > this.kreditantrag.getKreditsumme(); // Das gleiche Ergbnis
    }

    @Override
    public String toString()
    {
        return this.nachname + ", " + this.vorname;
    }

    // ------------
    // Interfaces Implementierung
    // ------------

    public void erstelleAntrag(int kreditsumme) throws RemoteException
    {
        Kreditantrag tempAntrag = new Kreditantrag(kreditsumme);
        this.kreditantrag = tempAntrag;
    }

    public void run() // TO BE IMPLEMENTED
    {
        
    }

    // ------------
    // Datenbankanbindungen
    // ------------

    public void dbInsertKredit() throws Exception
    {
        Connection verbindungZurDatenbank = null;
        
        try
        {
            Class.forName(driver);

            verbindungZurDatenbank = DriverManager.getConnection(dbURL, login, passwort);

            String sql = "Insert into Kredit(Kreditantrag_Nummer, Laufzeit, Zinssatz) VALUES(?,?,?) ";
            PreparedStatement ps = verbindungZurDatenbank.prepareStatement(sql);
            ps.setInt(1, this.kredit.getKreditantragsnummer());
            ps.setInt(2, this.kredit.getLaufzeit());
            ps.setDouble(3, this.kredit.getZinssatz());
            ps.executeQuery();
        }
        catch(Exception exception) // Hier werden alle Exceptions abgefangen, da alle gleich behandelt werden!
        {
            throw exception;
        }
    }

    public static KundeImpl dbGetKunde(int nummer) throws RemoteException, Exception
    {
        /* Verbindung zur Datenbank */
        Connection con = null;
        try
        {
            Class.forName(driver); // Überprüfe ob Treiber geladen sind
            
            con = DriverManager.getConnection(dbURL, login, passwort);

            // ab hier besteht eine Verbindung zur Datenbank
            String sql = "Select * From Kunde Where Nummer = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nummer); // Erstes '?' durch Kundennummer ersetzen

            ResultSet res = ps.executeQuery();
            try
            {
                while(res.next()) // solange noch Zeilen im Ergebnis
                {
                    // jeweils Spalten mit Namen 'kdNr' und 'name' ausgeben
                    String vorname = res.getString("Vorname");
                    String nachname = res.getString("Name");
                    double bonitaet = res.getDouble("Bonität");

                    con.close();
                    return new KundeImpl(nachname, vorname, nummer, bonitaet);
                }
            }
            catch(Exception exception) // Hier werden alle Exceptions abgefangen, da alle gleich behandelt werden!
            {
                throw exception;
            }
        }
        catch(Exception exception) // Hier werden alle Exceptions abgefangen, da alle gleich behandelt werden!
        {
            throw exception;
        }

        return null;
    }

    // ------------
    // Getter und Setter
    // ------------

    public String getName()
    { 
        return this.nachname;
    }

    public Kreditantrag getKreditantrag()
    {
        return this.kreditantrag;
    }

    public int getKundennummer()
    {
        return this.kundennummer;
    }
}
