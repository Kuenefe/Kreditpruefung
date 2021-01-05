package exceptions;

public class ZuVieleVorschlaege extends Exception
{
    public ZuVieleVorschlaege()
    {
        super("Die maximale Anzahl an Vorschlägen ist bereits erreicht");
    }

    public ZuVieleVorschlaege(String Fehler)
    {
        super(Fehler);
    }
}
