package Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ChatRoomClient extends UnicastRemoteObject implements IChatRoomClient{

    private String userName;
    private ClientGUI clientGUI;

    protected ChatRoomClient(String userName) throws RemoteException {
        this.userName = userName;
    }

    @Override
    public String getUserName() throws RemoteException {
        return userName;
    }

    @Override
    public void sendChat(String message) throws RemoteException {
        clientGUI.writeMessage(message);
    }

    public void setGUI(ClientGUI GUI){
         clientGUI = GUI;
    }
}
