import classes.KundeImpl;
import classes.MitarbeiterImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.rmi.Naming;
import java.rmi.NotBoundException;
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
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException, AccessException, NotBoundException, Exception
    {
        Registry reg;
        int p = 1099; 
        reg = LocateRegistry.createRegistry(p);


        // Kunde Objekt wird erstellt und "freigegeben"
        KundeImpl kunde = KundeImpl.dbGetKunde(1);


        if(kunde.equals(null))
        {
            throw new Exception("Kunde existiert nicht"); // DatenbankeintragFehlt Exception einfügen!
        }

        System.out.println("Kunde: " + kunde.toString() + " wurde von der Datenbank geladen ...");

        Naming.bind("rmi://localhost:1099/KundeBarth", kunde);
        System.out.println("Warte auf Kunde");

        // Bestätigung das Client fertig ist.
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        // Bestätigung das Client fertig ist.        

        kunde.getKreditantrag().dbInsertKreditantrag(kunde.getKundennummer());


        // Bonität wird überprüft
        if (kunde.ueberpruefeBonitaet()) // direkt durchjubeln
        {
            kunde.antragGenehmigen(3650);

            System.out.println("Angenommen");
            Naming.unbind("rmi://localhost:1099/KundeBarth");
        }
        else // erst überprüfen
        {
            MitarbeiterImpl sachbearbeiterEins = MitarbeiterImpl.dbGetMitarbeiter(3);

            if(sachbearbeiterEins.equals(null))
            {
                throw new Exception("Sachbearbeiter eins existiert nicht"); // DatenbankeintragFehlt Exception einfügen!
            }

            System.out.println("Sachbearbeiter eins: " + sachbearbeiterEins.toString() + " wurde von der Datenbank geladen ...");

            sachbearbeiterEins.setKreditantrag(kunde.getKreditantrag());
            Naming.bind("rmi://localhost:1099/SachbearbeiterMarius", sachbearbeiterEins);


            MitarbeiterImpl sachbearbeiterZwei = MitarbeiterImpl.dbGetMitarbeiter(4);

            if(sachbearbeiterEins.equals(null))
            {
                throw new Exception("Sachbearbeiter zwei existiert nicht"); // DatenbankeintragFehlt Exception einfügen!
            }

            System.out.println("Sachbearbeiter zwei: " + sachbearbeiterZwei.toString() + " wurde von der Datenbank geladen ...");
            
            sachbearbeiterZwei.setKreditantrag(kunde.getKreditantrag());
            Naming.bind("rmi://localhost:1099/SachbearbeiterEmre", sachbearbeiterZwei);

            System.out.println("Warte auf Sachbearbeiter");

            // Sachbearbeiter treffen ihre Vorschläge            
            // Bestätigung das Client fertig ist.
            scanner.nextLine();
            // Bestätigung das Client fertig ist.
            

            sachbearbeiterEins.dbInsertVorschlag();
            sachbearbeiterZwei.dbInsertVorschlag();


            if(kunde.getKreditantrag().getWunschsumme() <= 500000)
            {
                MitarbeiterImpl vorgesetzter = MitarbeiterImpl.dbGetMitarbeiter(2);

                if(vorgesetzter.equals(null))
                {
                    throw new Exception("Vorgesetzter existiert nicht"); // DatenbankeintragFehlt Exception einfügen!
                }

                System.out.println("Vorgesetzter: " + vorgesetzter.toString() + " wurde von der Datenbank geladen ...");

                vorgesetzter.setKreditantrag(kunde.getKreditantrag());
                Naming.bind("rmi://localhost:1099/Vorgesetzter", vorgesetzter);

                System.out.println("Warte auf Vorgesetzter");

                // Warten auf Vorgesetzter Antwort
                // Bestätigung das Client fertig ist.
                scanner.nextLine();
                // Bestätigung das Client fertig ist.

                Naming.unbind("rmi://localhost:1099/Vorgesetzter");
            }
            else
            {
                MitarbeiterImpl geschaeftsfuehrer = MitarbeiterImpl.dbGetMitarbeiter(1);

                if(geschaeftsfuehrer.equals(null))
                {
                    throw new Exception("Geschäftsführer existiert nicht"); // DatenbankeintragFehlt Exception einfügen!
                }

                System.out.println("Geschäftsführer: " + geschaeftsfuehrer.toString() + " wurde von der Datenbank geladen ...");


                geschaeftsfuehrer.setKreditantrag(kunde.getKreditantrag());
                Naming.bind("rmi://localhost:1099/Geschäftsführer", geschaeftsfuehrer);

                System.out.println("Warte auf Geschäftsführer");

                // Bestätigung das Client fertig ist.
                scanner.nextLine();
                // Bestätigung das Client fertig ist.

                Naming.unbind("rmi://localhost:1099/Geschäftsführer");
            }

            if(kunde.getKreditantrag().getEntscheidung() == true) // auch schreibbar: kunde.getKreditantrag().getEntscheidung()
            {
                kunde.antragGenehmigen(3650);

                System.out.println("Angenommen");
                // Kunde Rückmeldung geben

                Naming.unbind("rmi://localhost:1099/KundeBarth");
                Naming.unbind("rmi://localhost:1099/SachbearbeiterMarius");
                Naming.unbind("rmi://localhost:1099/SachbearbeiterEmre");
            }
            else
            {
                System.out.println("Der Kreditantrag wurde Abgelehnt");
                // Kunde Rückmeldung geben


                Naming.unbind("rmi://localhost:1099/KundeBarth");
                Naming.unbind("rmi://localhost:1099/SachbearbeiterMarius");
                Naming.unbind("rmi://localhost:1099/SachbearbeiterEmre");
            }
        }

        System.exit(0); // Hier ist das Programm fertig und kann geschlossen werden
    }
}
