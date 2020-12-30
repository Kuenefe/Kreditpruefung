package Interfaces;

import Classes.Vorschlag;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sachbearbeiter extends Remote
{
    public abstract Vorschlag erstelleVorschlag() throws RemoteException;
}
