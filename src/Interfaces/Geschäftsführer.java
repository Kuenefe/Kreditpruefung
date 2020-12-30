package Interfaces;

import Classes.Vorschlag;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Geschäftsführer extends Remote
{
    public abstract boolean gemeinschaftlicheÜberprüfung(Vorschlag vorschlagDesVorgesetzten) throws RemoteException;
}
