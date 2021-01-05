package exceptions;

public class DatenbankeintragFehlt extends Exception
{
    public DatenbankeintragFehlt()
    {
        super("Der Eintrag ist fehlerhaft");
    }

    public DatenbankeintragFehlt(String Fehler)
    {
        super(Fehler);
    }
}
