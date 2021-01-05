import interfaces.Sachbearbeiter;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSachbearbeiter 
{
    // ------------
    // Main Methode
    // ------------
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, Exception
    {
        // TBI:
        // Consoleneingabe um zu ermitteln, welcher Mitarbeiter am Rechner ist

        
        // ------------
        // Beziehe Mitarbeiterreferenz aus dem Server
        // ------------
        
        //Beziehe MitarbeiterImpl-Referenz aus dem Server und caste es zum Sachbearbeiter-Interface
        Sachbearbeiter bearbeiterEins = (Sachbearbeiter) Naming.lookup("rmi://localhost:1099/SachbearbeiterMarius");
        bearbeiterEins.erstelleVorschlag(true); // Eingabe vom Sachbearbeiter nötig!

        //Beziehe MitarbeiterImpl-Referenz aus dem Server und caste es zum Sachbearbeiter-Interface
        Sachbearbeiter bearbeiterZwei = (Sachbearbeiter) Naming.lookup("rmi://localhost:1099/SachbearbeiterEmre");
        bearbeiterZwei.erstelleVorschlag(true); // Eingabe vom Sachbearbeiter nötig!

        
        // ------------
        // Client schickt an Server Antwort dass dieser fertig ist
        // ------------
        
        try
        { 
            Socket clientSocket = new Socket("localhost",4000);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            out.println("done");

            out.close();
            clientSocket.close();

        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
