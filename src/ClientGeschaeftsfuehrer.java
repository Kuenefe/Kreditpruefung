
import interfaces.Geschäftsführer;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientGeschaeftsfuehrer
{
    // ------------
    // Main Methode
    // ------------
    public static void main(String[] args)throws MalformedURLException, RemoteException, NotBoundException, Exception
    {
        // ------------
        // Beziehe Mitarbeiterreferenz aus dem Server
        // ------------
        
        //Beziehe MitarbeiterImpl-Referenz aus dem Server und caste es zum Geschäftsführer-Interface
        Geschäftsführer geschäftsführer = (Geschäftsführer) Naming.lookup("rmi://localhost:1099/Geschäftsführer");
        geschäftsführer.gemeinschaftlicheEntscheidungTreffen(true, 2, true); // Eingabe vom Geschäftsführer nötig!


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
