import Interfaces.Sachbearbeiter;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientSachbearbeiter 
{
    // ------------
    // Main Methode
    // ------------
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, Exception
    {
        // TBI:
        // Consoleneingabe um zu ermitteln, welcher Mitarbeiter am Rechner ist

        // Sachbearbeiter 1
        Sachbearbeiter bearbeiterEins = (Sachbearbeiter) Naming.lookup("rmi://localhost:1099/SachbearbeiterMarius");
        bearbeiterEins.erstelleVorschlag(true); // Eingabe vom Sachbearbeiter nötig!

        // Sachbearbeiter 2
        Sachbearbeiter bearbeiterZwei = (Sachbearbeiter) Naming.lookup("rmi://localhost:1099/SachbearbeiterEmre");
        bearbeiterZwei.erstelleVorschlag(true); // Eingabe vom Sachbearbeiter nötig!
    }
}
