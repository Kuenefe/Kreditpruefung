
import interfaces.Geschäftsführer;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientGeschaeftsfuehrer
{
    // ------------
    // Main Methode
    // ------------
    public static void main(String[] args)throws MalformedURLException, RemoteException, NotBoundException
    {
        Geschäftsführer geschäftsführer = (Geschäftsführer) Naming.lookup("rmi://localhost:1099/Geschäftsführer");
        geschäftsführer.gemeinschaftlicheEntscheidungTreffen(false, true); // Eingabe vom Geschäftsführer nötig!
    }
}
