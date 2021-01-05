import interfaces.Kunde;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import exceptions.FalscherCodeVonRemote;

public class ClientKunde 
{
    // ------------
    // Main Methode
    // ------------
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, Exception
    {   
        // ------------
        // Beziehe Kundenreferenz aus dem Server
        // ------------
        
        //Beziehe KundenImpl-Referenz aus dem Server und caste es zum Kunde-Interface
        Kunde barth = (Kunde) Naming.lookup("rmi://localhost:1099/KundeBarth");
        int kreditsumme = 1500000; // Muss per Console Input eingegeben werden
        barth.erstelleAntrag(kreditsumme);


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


        // ------------
        // Client wartet auf die Antwort des Servers um zu ermitteln, ob der Antrag abgelehnt oder angenommen worden ist
        // ------------

        ServerSocket ss = null;
        Socket cs = null;
        InputStream in = null;
        BufferedReader read = null;

        String row = "";

        try
        {
            ss = new ServerSocket(4001);
            cs = ss.accept();
            in = cs.getInputStream();
            read = new BufferedReader(new InputStreamReader(in));

            row = read.readLine();
            if(row.equals("akzeptiert"))
            {
                System.out.println("Der Kreditantrag wurde genehmigt!");
            }
            else if(row.equals("abgelehnt"))
            {
                System.out.println("Der Kreditantrag wurde abgelehnt!");
            }
            else
            {
                throw new FalscherCodeVonRemote();
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            ss.close();
            cs.close();
            in.close();
            read.close(); 
        }
    }
}
