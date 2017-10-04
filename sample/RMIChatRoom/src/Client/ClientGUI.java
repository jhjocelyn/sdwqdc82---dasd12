package Client;

import Server.IChatRoomServer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;


public class ClientGUI {

    private ChatRoomClient client;
    private IChatRoomServer server;


    JFrame frame;
    JTextArea screen;
    JTextField input,ip, userName;
    JButton logIn;
    JList list;

    public static void main(String[] args) {
        new ClientGUI();
    }

    public void connectToServer(){
        if (logIn.getText().equals("Login")){
            if (userName.getText().length() == 0){
                JOptionPane.showMessageDialog(frame,"Please enter a user name");
                return;
            }
            if (ip.getText().length() == 0){
                JOptionPane.showMessageDialog(frame,"please enter an ip address");
                return;
            }
            try{
                client = new ChatRoomClient(userName.getText());
                client.setGUI(this);
//                Registry registry = LocateRegistry.getRegistry("localhost");
                server = (IChatRoomServer)Naming.lookup("rmi://"+ip.getText()+"/chatRoom");
                server.login(client);
                updateUserList(server.getConnectedClients());
                logIn.setText("Log Out");


            }catch (RemoteException e){
                System.out.println(e.getMessage());
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }else{

        }

    }

    public void sendText(){
        if (logIn.getText().equals("login")) {
            JOptionPane.showMessageDialog(frame, "You need to login first");
        }
        if (logIn.getText()==null){
            JOptionPane.showMessageDialog(frame, "enter something");
        }else{
            String msg = input.getText();
            msg = userName.getText()+": "+msg;
            input.setText("");
            try {
                server.broadcast(msg);
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    public void writeMessage(String message){
        screen.setText(message);
    }

    public void updateUserList(Vector vector){
        DefaultListModel userList = new DefaultListModel();
        if (vector!=null){
            for (int i=0;i<vector.size();i++){
                try {
                    String name = ((IChatRoomClient)vector.get(i)).getUserName();
                    userList.addElement(name);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            list.setModel(userList);
            }
        }
    }

    public ClientGUI(){
        frame = new JFrame("Group Chat");
        JPanel main =new JPanel();
        JPanel top =new JPanel();
        JPanel center =new JPanel();
        JPanel bottom =new JPanel();
        ip=new JTextField();
        input=new JTextField();
        userName=new JTextField();
        screen=new JTextArea();
        logIn=new JButton("Login");
        JButton sendBtn=new JButton("Send");
        list=new JList();
        main.setLayout(new BorderLayout(5,5));
        top.setLayout(new GridLayout(1,0,5,5));
        center.setLayout(new BorderLayout(5,5));
        bottom.setLayout(new BorderLayout(5,5));
        top.add(new JLabel("Username: "));top.add(userName);
        top.add(new JLabel("Server Address: "));top.add(ip);
        top.add(logIn);
        center.add(new JScrollPane(screen), BorderLayout.CENTER);
        center.add(list, BorderLayout.EAST);
        bottom.add(input, BorderLayout.CENTER);
        bottom.add(sendBtn, BorderLayout.EAST);
        main.add(top, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);
        main.setBorder(new EmptyBorder(10, 10, 10, 10) );

        //Events
        logIn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                connectToServer();
            } });

        sendBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ sendText();   }  });

        input.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ sendText();   }  });

        frame.setContentPane(main);
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }

}



