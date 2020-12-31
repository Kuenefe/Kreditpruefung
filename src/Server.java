import Classes.KundeImpl;
import Classes.MitarbeiterImpl;
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
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException, AccessException, NotBoundException
    {
        Registry reg;
        int p = 1099; 
        reg = LocateRegistry.createRegistry(p);

        // Kunde Objekt wird erstellt und "freigegeben"
        KundeImpl kunde = new KundeImpl("Barth", "Thomas", "16-01-1991", 1, 10);
        Naming.bind("rmi://localhost:1099/KundeBarth", kunde);
       
        System.out.println("Warte auf Kunde");

        // Bestätigung das Client fertig ist.
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        // Bestätigung das Client fertig ist.        

        // Bonität wird überprüft
        if (kunde.ueberpruefeBonitaet()) // direkt durchjubeln
        {
            kunde.antragGenehmigen(3650);

            System.out.println("Angenommen");
            Naming.unbind("rmi://localhost:1099/KundeBarth");
        }
        else // erst überprüfen
        {
            System.out.println("Warte auf Sachbearbeiter");

            MitarbeiterImpl sachbearbeiterEins = new MitarbeiterImpl("Marius", "Bützler", "12-05-1942", 1);
            sachbearbeiterEins.setKreditantrag(kunde.getKreditantrag());
            Naming.bind("rmi://localhost:1099/SachbearbeiterMarius", sachbearbeiterEins);

            MitarbeiterImpl sachbearbeiterZwei = new MitarbeiterImpl("Emre", "Guney", "05-12-1924", 2);
            sachbearbeiterZwei.setKreditantrag(kunde.getKreditantrag());
            Naming.bind("rmi://localhost:1099/SachbearbeiterEmre", sachbearbeiterZwei);

            // Bestätigung das Client fertig ist.
            scanner.nextLine();
            // Bestätigung das Client fertig ist.
            
            if(kunde.getKreditantrag().getWunschsumme() < 500000)
            {
                System.out.println("Warte auf Vorgesetzter");

                MitarbeiterImpl vorgesetzter = new MitarbeiterImpl("Florian", "Eyring", "12-12-2001", 3);
                vorgesetzter.setKreditantrag(kunde.getKreditantrag());
                Naming.bind("rmi://localhost:1099/Vorgesetzter", vorgesetzter);

                // Warten auf Vorgesetzter Antwort
                // Bestätigung das Client fertig ist.
                scanner.nextLine();
                // Bestätigung das Client fertig ist.

                Naming.unbind("rmi://localhost:1099/Vorgesetzter");
            }
            else
            {
                System.out.println("Warte auf Geschäftsführer");

                MitarbeiterImpl geschaeftsfuehrer = new MitarbeiterImpl("Stefan", "Selmeczi", "02-02-2222", 4);
                geschaeftsfuehrer.setKreditantrag(kunde.getKreditantrag());
                Naming.bind("rmi://localhost:1099/Geschäftsführer", geschaeftsfuehrer);

                // Bestätigung das Client fertig ist.
                scanner.nextLine();
                // Bestätigung das Client fertig ist.

                Naming.unbind("rmi://localhost:1099/Geschäftsführer");
            }


            if(kunde.getKreditantrag().getEntscheidung())
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
                System.out.println("Abgelehnt");
                // Kunde Rückmeldung geben


                Naming.unbind("rmi://localhost:1099/KundeBarth");
                Naming.unbind("rmi://localhost:1099/SachbearbeiterMarius");
                Naming.unbind("rmi://localhost:1099/SachbearbeiterEmre");
            }
        }

        System.exit(0);
    }
}
