package classes;

import interfaces.Geschäftsführer;
import interfaces.Sachbearbeiter;
import interfaces.Vorgesetzter;
import interfaces.DatabaseConnect;
import exceptions.ZuVieleVorschlaege;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

import javax.swing.tree.ExpandVetoException;


public class MitarbeiterImpl extends UnicastRemoteObject implements Vorgesetzter, Sachbearbeiter, Geschäftsführer, DatabaseConnect
{
    // ------------
    // Klassenattribute
    // ------------
    
    private int mitarbeiternummer;
    private String nachname;
    private String vorname;
    private Kreditantrag kreditantrag;
    private int vorgesetztennummer;

    // ------------
    // Konstruktoren
    // ------------

    public MitarbeiterImpl(String nachname, String vorname, int mitarbeiternummer, int vorgesetztennummer) throws RemoteException
    {
        
        this.nachname = nachname;
        this.vorname = vorname;
        this.mitarbeiternummer = mitarbeiternummer;
        this.vorgesetztennummer = vorgesetztennummer;
    }
    

    // ------------
    // Methoden
    // ------------

    @Override
    public String toString()
    {
        return this.nachname + ", " + this.vorname;
    }

    // ------------
    // Datenbankanbindungen
    // ------------

    public static MitarbeiterImpl dbGetMitarbeiter(int mitarbeiternummer) throws Exception
    {
        Connection verbindungZurDatenbank = null;
      
        try
        {
            Class.forName(driver);

            verbindungZurDatenbank = DriverManager.getConnection(dbURL, login, passwort);

            String sql = "Select Name, Vorname, Vorgesetzter_Nummer From Mitarbeiter Where Nummer = ?";
            PreparedStatement ps = verbindungZurDatenbank.prepareStatement(sql);
            ps.setInt(1, mitarbeiternummer);

            ResultSet res = ps.executeQuery();
            try
            {
                while(res.next())
                {
                    String name = res.getString(1);
                    String vorname = res.getString(2);
                    int vorgesetztennummer = res.getInt(3);

                    verbindungZurDatenbank.close();

                    return new MitarbeiterImpl(name, vorname, mitarbeiternummer, vorgesetztennummer);
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

    public void dbInsertVorschlag() throws Exception
    {
        Connection verbindungZurDatenbank = null;
        
        try
        {
            Class.forName(driver);

            verbindungZurDatenbank = DriverManager.getConnection(dbURL, login, passwort);

            String sql = "Insert into Vorschlag(Mitarbeiter_Nummer, Kreditantrag_Nummer, Vorschlag) VALUES(?,?,?) ";
            PreparedStatement ps = verbindungZurDatenbank.prepareStatement(sql);
            ps.setInt(1, this.mitarbeiternummer);
            ps.setInt(2, this.kreditantrag.getKreditantragsnummer());
            ps.setBoolean(3, this.kreditantrag.getVorschlag(this));
            ps.executeQuery();
        }
        catch(Exception exception) // Hier werden alle Exceptions abgefangen, da alle gleich behandelt werden!
        {
            throw exception;
        }
    }

    public void dbInsertEntscheidung() throws Exception
    {
        Connection verbindungZurDatenbank = null;
        
        try
        {
            Class.forName(driver);

            verbindungZurDatenbank = DriverManager.getConnection(dbURL, login, passwort);

            String sql = "Insert into Entscheidung(Vorgesetzter_Nummer, Kreditantrag_Nummer, Entscheidung) VALUES(?,?,?) ";

            PreparedStatement ps = verbindungZurDatenbank.prepareStatement(sql);
            ps.setInt(1, this.mitarbeiternummer);
            ps.setInt(2, this.kreditantrag.getKreditantragsnummer());
            ps.setBoolean(3, this.kreditantrag.getEntscheidung().getEntscheidung());
            ps.executeQuery();
        }
        catch(Exception exception) // Hier werden alle Exceptions abgefangen, da alle gleich behandelt werden!
        {
            throw exception;
        }
    }

    public void dbInsertGemeinschaftlicheEntscheidung() throws Exception
    {
        Connection verbindungZurDatenbank = null;
        
        try
        {
            Class.forName(driver);

            verbindungZurDatenbank = DriverManager.getConnection(dbURL, login, passwort);

            String sql = "Insert into Entscheidung(Vorgesetzter_Nummer, Kreditantrag_Nummer, Entscheidung, Geschäftsführer_Nummer) VALUES(?,?,?,?) ";

            PreparedStatement ps = verbindungZurDatenbank.prepareStatement(sql);
            ps.setInt(1, this.kreditantrag.getEntscheidung().getVorgesetztennummer());
            ps.setInt(2, this.kreditantrag.getKreditantragsnummer());
            ps.setBoolean(3, this.kreditantrag.getEntscheidung().getEntscheidung());
            ps.setInt(4, this.mitarbeiternummer);
            ps.executeQuery();
        }
        catch(Exception exception) // Hier werden alle Exceptions abgefangen, da alle gleich behandelt werden!
        {
            throw exception;
        }
    }

    // ------------
    // Interfaces Implementierung
    // ------------

    public void erstelleVorschlag(Boolean vorschlag) throws ZuVieleVorschlaege, RemoteException
    {
        kreditantrag.vorschlagHinzufuegen(vorschlag, this);
    }   

    public void gemeinschaftlicheEntscheidungTreffen(Boolean edv, int vorgesetztennummer, Boolean edg)
    {
        if(edv == true && edg == true)
        {
            this.kreditantrag.gemeinschaftlicheEntscheidungHinzufuegen(true, vorgesetztennummer);
        }
        else
        {
            this.kreditantrag.gemeinschaftlicheEntscheidungHinzufuegen(false, vorgesetztennummer);
        }
    }

    public void entscheidungTreffen(Boolean entscheidung)
    {
        this.kreditantrag.entscheidungHinzufuegen(entscheidung);
    }

    public void run() // TBI
    {

    }

    // ------------
    // Getter und Setter
    // ------------

    public void setKreditantrag(Kreditantrag kreditantrag)
    {
        this.kreditantrag = kreditantrag;
    }

}
