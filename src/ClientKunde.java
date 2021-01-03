import interfaces.Kunde;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientKunde {
    // ------------
    // Main Methode
    // ------------
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException
    {
        Kunde barth = (Kunde) Naming.lookup("rmi://localhost:1099/KundeBarth");
        int wunschsumme = 1500000; // Muss per Console Input eingegeben werden
        barth.erstelleAntrag(wunschsumme);

        // Implementierung das KundeClient fertig ist
    }
}
