package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Kunde extends Remote
{
    public abstract void erstelleAntrag(int kreditsumme) throws RemoteException;
}
