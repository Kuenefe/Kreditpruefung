package classes;

import java.sql.*;
import java.util.HashMap;

import exceptions.ZuVieleVorschlaege;
import interfaces.DatabaseConnect;

public class Kreditantrag implements DatabaseConnect
{
    // ------------
    // Klassenattribute
    // ------------
    
    private int kreditantragsnummer;
    private Boolean genehmigt = false;
    private int kreditsumme;
    private HashMap<MitarbeiterImpl, Vorschlag> vorschlaege;
    private Entscheidung entscheidung;
    
    // ------------
    // Konstruktoren
    // ------------

    public Kreditantrag(int kreditsumme)
    {
        this.kreditsumme = kreditsumme;
        this.vorschlaege = new HashMap<>();
    }
    
    // ------------
    // Methoden
    // ------------

    public void vorschlagHinzufuegen(Boolean entscheidung, MitarbeiterImpl m) throws ZuVieleVorschlaege //Darf nur vom Sachbearbeiter ausgeführt werden
    {
        if(vorschlaege.size() < 2)
        {
            Vorschlag v = new Vorschlag(entscheidung);
            vorschlaege.put(m, v);
        }
        else
        {
            throw new ZuVieleVorschlaege("Zu viele Vorschläge vorhanden"); // NEUE EXCEPTION EINFÜGEN!! TBI
        }
    }

    public void entscheidungHinzufuegen(Boolean entscheidung)
    {
        this.entscheidung = new Entscheidung(entscheidung);
    }

    public void gemeinschaftlicheEntscheidungHinzufuegen(Boolean entscheidung, int vorgesetztennummer)
    {
        this.entscheidung = new Entscheidung(entscheidung, vorgesetztennummer);
    }

    // ------------
    // Interfaces Implementierung
    // ------------

    public void run() // TBI
    {

    }

    // ------------
    // Datenbankanbindungen
    // ------------

    public void dbInsertKreditantrag(int kundennummer) throws Exception
    {
        Connection verbindungZurDatenbank = null;
        
        try
        {
            Class.forName(driver);

            verbindungZurDatenbank = DriverManager.getConnection(dbURL, login, passwort);

            String sql = "Insert into Kreditantrag(Kunden_Nummer, Kreditsumme) VALUES(?,?)";
            PreparedStatement ps = verbindungZurDatenbank.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, kundennummer);
            ps.setInt(2, this.kreditsumme);

            ps.executeQuery();
            //
            ResultSet rs = ps.getGeneratedKeys(); // Speicher den erstellten Primärschlüssel ab
            if(rs.next())
            {
                this.kreditantragsnummer = rs.getInt(1);
            }
        }
        catch(Exception exception) // Hier werden alle Exceptions abgefangen, da alle gleich behandelt werden!
        {
            throw exception;
        }
    }

    // ------------
    // Getter und Setter
    // ------------

    public Entscheidung getEntscheidung()
    {
        return this.entscheidung;
    }

    public int getKreditantragsnummer(){
        return this.kreditantragsnummer;
    }

    public int getKreditsumme()
    {
        return this.kreditsumme;
    }

    public Boolean getVorschlag(MitarbeiterImpl mitarbeiter)
    {
        return vorschlaege.get(mitarbeiter).getEntscheidung();
    }
   
    public void setGenehmigtTrue() // Darf nur vom Server ausgeführt werden
    {
        this.genehmigt = true;
    }
}