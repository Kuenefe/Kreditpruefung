package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Vorgesetzter extends Remote
{
    public abstract void entscheidungTreffen(Boolean entscheidung) throws RemoteException;
}
