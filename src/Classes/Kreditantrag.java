package Classes;


public class Kreditantrag 
{
    // ------------
    // Klassenattribute
    // ------------
    
    private int kreditantragsnummer;
    private Boolean genehmigt = false;
    private int wunschsumme;
    
    // ------------
    // Konstruktoren
    // ------------

    public Kreditantrag(int kreditantragsnummer,int wunschsumme)
    {
        this.kreditantragsnummer = kreditantragsnummer;
        this.wunschsumme = wunschsumme;
    }
    

    // ------------
    // Methoden
    // ------------

    // Wenn genehmigt dann erstelle Kredit
    public void setGenehmigtTrue() // Darf nur vom Server ausgeführt werden
    {
        this.genehmigt = true;
    }

    public int getWunschsumme()
    {
        return this.wunschsumme;
    }
}
