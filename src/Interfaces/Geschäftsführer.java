package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Geschäftsführer extends Remote
{
    public abstract void gemeinschaftlicheEntscheidungTreffen(Boolean entscheidungDesVorgesetzten, Boolean entscheidungDesGeschaeftsfuehrer) throws RemoteException;
}
