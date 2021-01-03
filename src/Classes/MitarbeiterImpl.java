package classes;

import interfaces.Geschäftsführer;
import interfaces.Sachbearbeiter;
import interfaces.Vorgesetzter;
import interfaces.DatabaseConnect;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;


public class MitarbeiterImpl extends UnicastRemoteObject implements Vorgesetzter, Sachbearbeiter, Geschäftsführer, DatabaseConnect
{
    // ------------
    // Klassenattribute
    // ------------
    
    private int mitarbeiternummer;
    private String nachname;
    private String vorname;
    private Kreditantrag kreditantrag;

    // ------------
    // Konstruktoren
    // ------------

    public MitarbeiterImpl(String nachname, String vorname, int mitarbeiternummer) throws RemoteException
    {
        
        this.nachname = nachname;
        this.vorname = vorname;
        this.mitarbeiternummer = mitarbeiternummer;
    }
    

    // ------------
    // Methoden
    // ------------
    
    public static MitarbeiterImpl dbGetMitarbeiter(int mitarbeiternummer)
    {
        Connection verbindungZurDatenbank = null;
      
        try
        {
            Class.forName(driver);

            verbindungZurDatenbank = DriverManager.getConnection(dbURL, login, passwort);

            String sql = "Select Name, Vorname From Mitarbeiter Where Nummer = ?";
            PreparedStatement ps = verbindungZurDatenbank.prepareStatement(sql);
            ps.setInt(1, mitarbeiternummer);

            ResultSet res = ps.executeQuery();
            try
            {
                while(res.next())
                {
                    String name = res.getString(1);
                    String vorname = res.getString(2);

                    verbindungZurDatenbank.close();

                    return new MitarbeiterImpl(name, vorname, mitarbeiternummer);
                }
            }
            catch(Exception innereException)
            {
                innereException.printStackTrace();
            }
        }
        catch(Exception aeussereException)
        {
            aeussereException.printStackTrace();
        }

        return null;
    }

    public void dbInsertVorschlag()
    {
        Connection verbindungZurDatenbank = null;
        
        try
        {
            Class.forName(driver);

            verbindungZurDatenbank = DriverManager.getConnection(dbURL, login, passwort);

            String sql = "Insert into Vorschlag(Mitarbeiter_Nummer, Kreditantrags_Nummer, Vorschlag) VALUES(?,?,?) ";
            PreparedStatement ps = verbindungZurDatenbank.prepareStatement(sql);
            ps.setInt(1, this.mitarbeiternummer);
            ps.setInt(2, this.kreditantrag.getKreditantragsnummer());
            ps.setBoolean(3, this.kreditantrag.getVorschlag(this));
            ps.executeQuery();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // ------------
    // Interfaces Implementierung
    // ------------

    public void erstelleVorschlag(Boolean vorschlag) throws Exception, RemoteException
    {
        kreditantrag.vorschlagHinzufuegen(vorschlag, this);
    }   

    public void gemeinschaftlicheEntscheidungTreffen(Boolean edv, Boolean edg)
    {
        if(edv == true && edg == true)
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

    public void run() // TBI
    {

    }

    @Override
    public String toString()
    {
        return this.nachname + ", " + this.vorname;
    }
}
