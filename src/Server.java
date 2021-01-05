import classes.KundeImpl;
import classes.MitarbeiterImpl;
import exceptions.DatenbankeintragFehlt;
import exceptions.FalscherCodeVonRemote;
import exceptions.ZuVieleVorschlaege;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.AlreadyBoundException;

public class Server 
{
        
    // ------------
    // Main Methode
    // ------------
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException, AccessException, NotBoundException, IOException, FalscherCodeVonRemote, ZuVieleVorschlaege, DatenbankeintragFehlt, Exception
    {
        // ------------
        // Registry wird erstellt und auf Port p gestartet
        // ------------
        Registry reg;
        int p = 1099; 
        reg = LocateRegistry.createRegistry(p);
        


        // ------------
        // Kundenobjekt wird durch das Auslesen aus der Datenbank erstellt
        // ------------

        KundeImpl kunde = KundeImpl.dbGetKunde(1);
        if(kunde.equals(null))
        {
            throw new DatenbankeintragFehlt("Kunde existiert nicht"); // DatenbankeintragFehlt Exception einfügen!
        }
        System.out.println("[Datenbank] --- Kunde: " + kunde.toString() + " wurde von der Datenbank geladen ...");


        // ------------
        // Kundenobjektreferenz wird auf der Registry mit dem Pfad gespeichert
        // ------------

        Naming.bind("rmi://localhost:1099/KundeBarth", kunde);
        System.out.println("[Server] --- Warte auf Kunde");
        // Bestätigung das Client fertig ist
        warteAufRueckmeldungDerClients();
        // Kreditantrag wird in die Datenbank geschrieben
        kunde.getKreditantrag().dbInsertKreditantrag(kunde.getKundennummer());


        // ------------
        // Bonität des Kunden zum Kreditantrag überprüfen
        // ------------

        if (kunde.ueberpruefeBonitaet()) // Kreditantrag direkt durchjubeln
        {
            // ------------
            // Antrag wird genehmigt und ein Kreditobjekt wird erstellt
            // ------------

            kunde.antragGenehmigen(3650);
            // Kredit wird in die Datenbank geschrieben
            kunde.dbInsertKredit();

            // Client des Kunden bekommt eine Rückmeldung
            kundenClientRückmeldungSchicken(true);

            System.out.println("[Server] --- Kreditantrag wurde angenommen");
        }
        else // Kreditantrag muss erst überprüft werden
        {
            // ------------
            // Sachbearbeiterobjekte werden durch das Auslesen aus der Datenbank erstellt
            // ------------

            MitarbeiterImpl sachbearbeiterEins = MitarbeiterImpl.dbGetMitarbeiter(3);
            if(sachbearbeiterEins.equals(null))
            {
                throw new DatenbankeintragFehlt("Sachbearbeiter eins existiert nicht"); // DatenbankeintragFehlt Exception einfügen!
            }
            System.out.println("[Datenbank] --- Sachbearbeiter eins: " + sachbearbeiterEins.toString() + " wurde von der Datenbank geladen ...");
            sachbearbeiterEins.setKreditantrag(kunde.getKreditantrag());

            MitarbeiterImpl sachbearbeiterZwei = MitarbeiterImpl.dbGetMitarbeiter(4);
            if(sachbearbeiterEins.equals(null))
            {
                throw new DatenbankeintragFehlt("Sachbearbeiter zwei existiert nicht"); // DatenbankeintragFehlt Exception einfügen!
            }
            System.out.println("[Datenbank] --- Sachbearbeiter zwei: " + sachbearbeiterZwei.toString() + " wurde von der Datenbank geladen ...");
            sachbearbeiterZwei.setKreditantrag(kunde.getKreditantrag());


            // ------------
            // Sachbearbeiterobjektreferenzen werden auf der Registry mit dem jeweiligen Pfad gespeichert
            // ------------

            Naming.bind("rmi://localhost:1099/SachbearbeiterMarius", sachbearbeiterEins);
            Naming.bind("rmi://localhost:1099/SachbearbeiterEmre", sachbearbeiterZwei);
            System.out.println("[Server] --- Warte auf Sachbearbeiter"); 
            // Bestätigung das Client fertig ist
            warteAufRueckmeldungDerClients();
            // Sachbearbeitervorschläge werden in die Datenbank geschrieben
            sachbearbeiterEins.dbInsertVorschlag();
            sachbearbeiterZwei.dbInsertVorschlag();

            String[] a = reg.list();
            for (int i = 0; i < a.length; i++) {
                System.out.println(a[i]);
              }

            // Die Sachbearbeiter werden aus der Registry entfernt
            Naming.unbind("rmi://localhost:1099/SachbearbeiterMarius");
            Naming.unbind("rmi://localhost:1099/SachbearbeiterEmre");


            // ------------
            // Überprüfung der Vorschläge durch Vorgesetzten oder und Geschäftsführer
            // ------------
            if(kunde.getKreditantrag().getKreditsumme() <= 500000) // Falls Kreditsumme unter (oder gleich) 500.000 liegt kann der Vorgesetzte alleine Entscheiden
            {
                // ------------
                // Vorgesetztenobjekte wird durch das Auslesen aus der Datenbank erstellt
                // ------------

                MitarbeiterImpl vorgesetzter = MitarbeiterImpl.dbGetMitarbeiter(2);
                if(vorgesetzter.equals(null))
                {
                    throw new DatenbankeintragFehlt("Vorgesetzter existiert nicht"); // DatenbankeintragFehlt Exception einfügen!
                }
                System.out.println("[Datenbank] --- Vorgesetzter: " + vorgesetzter.toString() + " wurde von der Datenbank geladen ...");
                vorgesetzter.setKreditantrag(kunde.getKreditantrag());

                Naming.bind("rmi://localhost:1099/Vorgesetzter", vorgesetzter);
                System.out.println("[Server] --- Warte auf Vorgesetzter");
                // Bestätigung das Client fertig ist.
                warteAufRueckmeldungDerClients();
                // Entscheidung des Vorgesetzten wird in die Datenbank geschrieben
                vorgesetzter.dbInsertEntscheidung();

                // Der Vorgesetzte wird aus der Registry entfernt
                Naming.unbind("rmi://localhost:1099/Vorgesetzter");
            }
            else // Falls Kreditsumme über 500.000 liegt muss der Geschäftsführer eine Entscheidung mit dem Vorgesetzten zusammen treffen.
            {
                // ------------
                // Geschäftsführerobjekt wird durch das Auslesen aus der Datenbank erstellt
                // ------------

                System.out.println("[Logik] --- Der Geschäftsführer setzt sich mit dem Vorgesetztem zusammen und trifft gemeinsam eine Entscheidung. Der Geschäftsführer trägt diese Entscheidung in seinem Client ein ...");
                MitarbeiterImpl geschaeftsfuehrer = MitarbeiterImpl.dbGetMitarbeiter(1);
                if(geschaeftsfuehrer.equals(null))
                {
                    throw new DatenbankeintragFehlt("Geschäftsführer existiert nicht"); // DatenbankeintragFehlt Exception einfügen!
                }
                System.out.println("[Datenbank] --- Geschäftsführer: " + geschaeftsfuehrer.toString() + " wurde von der Datenbank geladen ...");
                geschaeftsfuehrer.setKreditantrag(kunde.getKreditantrag());

                Naming.bind("rmi://localhost:1099/Geschäftsführer", geschaeftsfuehrer);
                System.out.println("[Server] --- Warte auf Geschäftsführer");
                // Bestätigung das Client fertig ist.
                warteAufRueckmeldungDerClients();
                // Entscheidung des Geschäftsführers und des Vorgesetzten wird in die Datenbank geschrieben
                geschaeftsfuehrer.dbInsertGemeinschaftlicheEntscheidung();

                // Der Geschäftsführer wird aus der Registry entfernt
                Naming.unbind("rmi://localhost:1099/Geschäftsführer");
            }

            if(kunde.getKreditantrag().getEntscheidung().getEntscheidung()) // Falls der Kreditantrag genehmigt wurde wird ein Kredit erstellt
            {
                // ------------
                // Antrag wird genehmigt und ein Kreditobjekt wird erstellt
                // ------------

                kunde.antragGenehmigen(3650);
                // Kredit wird in die Datenbank geschrieben
                kunde.dbInsertKredit();
                
                // Client des Kunden bekommt eine Rückmeldung
                kundenClientRückmeldungSchicken(true);

                System.out.println("[Server] --- Kreditantrag wurde angenommen");
            }
            else // Falls der Kreditantrag nicht genehmigt wurde
            {
                // Client des Kunden bekommt eine Rückmeldung
                kundenClientRückmeldungSchicken(false);
                
                System.out.println("[Server] --- Kreditantrag wurde abgelehnt");
            }
        }       
        
        // Der Kunde wird aus der Registry entfernt
        Naming.unbind("rmi://localhost:1099/KundeBarth");

        System.exit(0); // Hier ist das Programm fertig und kann geschlossen werden
    }

    public static void warteAufRueckmeldungDerClients() throws IOException, FalscherCodeVonRemote
    {
        ServerSocket ss = null;
        Socket cs = null;
        InputStream in = null;
        BufferedReader read = null;

        String row = "";

        try
        {
            ss = new ServerSocket(4000);
            cs = ss.accept();
            in = cs.getInputStream();
            read = new BufferedReader(new InputStreamReader(in));

            row = read.readLine();
            if(!row.equals("done"))
            {
                throw new FalscherCodeVonRemote("Falscher Code bekommen");
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
     
        System.out.println("[Client] --- Client hat Bearbeitung abgeschlossen.");
    }

    public static void kundenClientRückmeldungSchicken(boolean angenommen) throws Exception
    {
        try
        { 
            Socket clientSocket = new Socket("localhost",4001);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            if(angenommen)
            {
                out.println("akzeptiert");
            }
            else
            {
                out.println("abgelehnt");
            }

            out.close();
            clientSocket.close();
        }
        catch(Exception e)
        {
            throw e;
        }

    }
}
