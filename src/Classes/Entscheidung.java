package classes;

public class Entscheidung 
{
    // ------------
    // Klassenattribute
    // ------------
    
    private boolean entscheidung;
    private int vorgesetztennummer;

    // ------------
    // Konstruktoren
    // ------------

    public Entscheidung(boolean entscheidung)
    {
        this.entscheidung = entscheidung;
    }
    
    public Entscheidung(boolean entscheidung, int vorgesetztennummer)
    {
        this.entscheidung = entscheidung;
        this.vorgesetztennummer = vorgesetztennummer;
    }

    // ------------
    // Getter und Setter
    // ------------

    public Boolean getEntscheidung()
    {
        return this.entscheidung;
    }

    public int getVorgesetztennummer()
    {
        return this.vorgesetztennummer;
    }
}
