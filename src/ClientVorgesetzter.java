import interfaces.Vorgesetzter;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientVorgesetzter 
{
    // ------------
    // Main Methode
    // ------------
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException
    {
        Vorgesetzter vorgesetzter = (Vorgesetzter) Naming.lookup("rmi://localhost:1099/Vorgesetzter");
        vorgesetzter.entscheidungTreffen(true); // Eingabe vom Vorgesetzten nötig!
    }
}
