package classes;

public class Vorschlag 
{
    // ------------
    // Klassenattribute
    // ------------
    
    private boolean entscheidung;

    // ------------
    // Konstruktoren
    // ------------

    public Vorschlag(boolean entscheidung)
    {
        this.entscheidung = entscheidung;
    }

    // ------------
    // Getter und Setter
    // ------------

    public Boolean getEntscheidung()
    {
        return this.entscheidung;
    }
}
