package Interfaces;

import Classes.Vorschlag;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sachbearbeiter extends Remote
{
    public abstract void erstelleVorschlag(Boolean vorschlag) throws RemoteException, Exception;
}
