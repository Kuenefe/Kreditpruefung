package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exceptions.ZuVieleVorschlaege;

public interface Sachbearbeiter extends Remote
{
    public abstract void erstelleVorschlag(Boolean vorschlag) throws RemoteException, ZuVieleVorschlaege;
}
