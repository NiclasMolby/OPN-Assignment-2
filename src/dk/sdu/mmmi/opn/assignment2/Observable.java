package dk.sdu.mmmi.opn.assignment2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observable extends Remote {
	public void addObserver(Observer observer) throws RemoteException;
	public void deleteObserver(Observer observer) throws RemoteException;
	public void notifyObservers() throws RemoteException;
}
