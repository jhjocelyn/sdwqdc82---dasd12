package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import Client.IChatRoomClient;

public interface IChatRoomServer extends Remote {

    boolean login(IChatRoomClient client) throws RemoteException;

    void broadcast(String message) throws RemoteException;

    Vector getConnectedClients() throws RemoteException;
}



