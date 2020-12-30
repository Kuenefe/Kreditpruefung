package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Vorgesetzter extends Remote
{
    public abstract boolean überprüfeVorschlag() throws RemoteException;
}
