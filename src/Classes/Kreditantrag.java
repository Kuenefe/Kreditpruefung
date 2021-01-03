package classes;

import java.sql.*;
import java.util.HashMap;
import interfaces.DatabaseConnect;

public class Kreditantrag implements DatabaseConnect
{
    // ------------
    // Klassenattribute
    // ------------
    
    private int kreditantragsnummer;
    private Boolean genehmigt = false;
    private int wunschsumme;
    private HashMap<MitarbeiterImpl, Vorschlag> vorschlaege;
    private Entscheidung entscheidung;
    
    // ------------
    // Konstruktoren
    // ------------

    public Kreditantrag(int wunschsumme)
    {
        this.wunschsumme = wunschsumme;
        this.vorschlaege = new HashMap<>();
    }
    
    // ------------
    // Methoden
    // ------------

    // Wenn genehmigt dann erstelle Kredit
    public void setGenehmigtTrue() // Darf nur vom Server ausgeführt werden
    {
        this.genehmigt = true;
    }

    public void vorschlagHinzufuegen(Boolean entscheidung, MitarbeiterImpl m) throws Exception //Darf nur vom Sachbearbeiter ausgeführt werden
    {
        if(vorschlaege.size() < 2)
        {
            Vorschlag v = new Vorschlag(entscheidung);
            vorschlaege.put(m, v);
        }
        else
        {
            throw new Exception("Zu viele Vorschläge vorhanden"); // NEUE EXCEPTION EINFÜGEN!! TBI
        }
    }

    public void entscheidungHinzufuegen(Boolean entscheidung)
    {
        this.entscheidung = new Entscheidung(entscheidung);
    }

    public void dbInsertKreditantrag(int kundennummer)
    {
        Connection verbindungZurDatenbank = null;
        
        try
        {
            Class.forName(driver);

            verbindungZurDatenbank = DriverManager.getConnection(dbURL, login, passwort);

            String sql = "Insert into Kreditantrag(Kunden_Nummer, Kreditsumme) VALUES(?,?)";
            PreparedStatement ps = verbindungZurDatenbank.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, kundennummer);
            ps.setInt(2, this.wunschsumme);

            ps.executeQuery();
            //
            ResultSet rs = ps.getGeneratedKeys(); // Speicher den erstellten Primärschlüssel ab
            if(rs.next())
            {
                this.kreditantragsnummer = rs.getInt(1);
            }
        }
        catch(Exception aeussereException)
        {
            aeussereException.printStackTrace();
        }
    }

    public Boolean getEntscheidung()
    {
        return this.entscheidung.getEntscheidung();
    }

    public int getKreditantragsnummer(){
        return this.kreditantragsnummer;
    }

    public int getWunschsumme()
    {
        return this.wunschsumme;
    }

    public Boolean getVorschlag(MitarbeiterImpl mitarbeiter)
    {
        return vorschlaege.get(mitarbeiter).getEntscheidung();
    }

    public void run() // TBI
    {

    }
}
