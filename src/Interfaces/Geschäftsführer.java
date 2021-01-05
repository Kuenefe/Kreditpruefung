package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Geschäftsführer extends Remote
{
    public abstract void gemeinschaftlicheEntscheidungTreffen(Boolean entscheidungDesVorgesetzten, int vorgesetztennummer, Boolean entscheidungDesGeschaeftsfuehrer) throws RemoteException;
}
