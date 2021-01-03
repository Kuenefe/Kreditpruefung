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

    public void erstelleAntrag(int wunschsumme) throws RemoteException
    {
        Kreditantrag tempAntrag = new Kreditantrag(wunschsumme);
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

    public int getKundennummer()
    {
        return this.kundennummer;
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

    public static KundeImpl dbGetKunde(int nummer) throws RemoteException
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
            catch(Exception e) // TO BE IMPLEMENTED
            {
                e.printStackTrace();
            }
        }
        catch (SQLException e) // TO BE IMPLEMENTED
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e) // TO BE IMPLEMENTED
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String toString()
    {
        return this.nachname + ", " + this.vorname;
    }

    public void run() // TO BE IMPLEMENTED
    {
        
	}
}
