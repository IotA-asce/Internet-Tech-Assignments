package TestBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread{

    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");

    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;

    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
    
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

    }

    @Override
    public void run(){

        String received;
        String toreturn;
        
        while (true) {
            
            try {
                
                dataOutputStream.writeUTF(
                
                    "What do you want?[Date | Time]..\n"+
                    "Type Exit to terminate connection."
                
                );

                received = dataInputStream.readUTF();

                if(received.equals("Exit")){

                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;

                }

                Date date = new Date();

                switch (received) {
                  
                    case "Date" :
                        toreturn = fordate.format(date);
                        dataOutputStream.writeUTF(toreturn);
                        break;
                          
                    case "Time" :
                        toreturn = fortime.format(date);
                        dataOutputStream.writeUTF(toreturn);
                        break;
                          
                    default:
                        dataOutputStream.writeUTF("Invalid input");
                        break;
                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        try {

            this.dataInputStream.close();
            this.dataOutputStream.close();

        } catch (IOException e){
            e.printStackTrace();
        }

    }    
    
}
