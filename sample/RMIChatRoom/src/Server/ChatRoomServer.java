package Server;


import Client.IChatRoomClient;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ChatRoomServer extends UnicastRemoteObject implements IChatRoomServer{

    private Vector vector = new Vector();

    protected ChatRoomServer() throws RemoteException {

    }

    public static void main(String[] args){
        System.setSecurityManager(new SecurityManager());
        try {

            IChatRoomServer server = new ChatRoomServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("rmi://127.0.0.1:1099/chatRoom",server);
            System.out.println("server is ready!");

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public boolean login(IChatRoomClient client) throws RemoteException {
        System.out.println(client.getUserName()+" is connected to the server");
        broadcast(client.getUserName() + "entered this chat room");
        vector.add(client);
        return true;
    }

    @Override
    public void broadcast(String message) throws RemoteException {
        int i = 0;
        while (i< vector.size()){
            try{
                IChatRoomClient eachClient = (IChatRoomClient)vector.get(i);
                eachClient.sendChat(message);
            }catch (Exception e){

            }

        }
    }

    @Override
    public Vector getConnectedClients() throws RemoteException {
        return vector;
    }


}
