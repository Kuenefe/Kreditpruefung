package exceptions;

public class FalscherCodeVonRemote extends Exception
{
    public FalscherCodeVonRemote()
    {
        super("Der Client hat einen Fehlerhaften Code verschickt.");
    }

    public FalscherCodeVonRemote(String Fehler)
    {
        super(Fehler);
    }
}
