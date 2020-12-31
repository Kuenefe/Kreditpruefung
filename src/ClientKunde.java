import Interfaces.Kunde;

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
        int wunschsumme = 15; // Muss per Console Input eingegeben werden
        int antragsnummer = 1; // Muss von der Datenbank vorgegeben werden
        barth.erstelleAntrag(antragsnummer, wunschsumme);

        // Implementierung das KundeClient fertig ist
    }
}
