package Classes;

import java.util.HashMap;

public class Kreditantrag 
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

    public Kreditantrag(int kreditantragsnummer,int wunschsumme)
    {
        this.kreditantragsnummer = kreditantragsnummer;
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
}
