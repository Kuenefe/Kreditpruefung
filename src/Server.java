import Classes.KundeImpl;
import Classes.MitarbeiterImpl;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.AlreadyBoundException;
import java.util.Scanner;

public class Server 
{
    // ------------
    // Main Methode
    // ------------
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException, AccessException
    {
        Registry reg;
        int p = 1099; 
        reg = LocateRegistry.createRegistry(p);

        // Kunde Objekt wird erstellt und "freigegeben"
        KundeImpl barth = new KundeImpl("Barth", "Thomas", "16-01-1991", 1, 10);
        Naming.bind("rmi://localhost:1099/KundeBarth", barth);

        // Bestätigung das Client fertig ist.
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
        // Bestätigung das Client fertig ist.
        
        System.out.println(barth.getName());
        
        KundeImpl kunde = barth; // für eventuelle spätere Generalisierung

        // Bonität wird überprüft
        if (kunde.ueberpruefeBonitaet()) // direkt durchjubeln
        {
            kunde.antragGenehmigen();
        }
        else // erst überprüfen
        {
            
        }
    }
}
