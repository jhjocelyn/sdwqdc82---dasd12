package Client;
import java.rmi.*;

public interface IChatRoomClient extends Remote{

    //income
    String getUserName() throws RemoteException;

    void sendChat(String message) throws  RemoteException;

}
