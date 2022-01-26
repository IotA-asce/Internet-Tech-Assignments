import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ClientHandler extends Thread{

    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");

    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;

    Integer statusFlag = 0;         /* Indicates type of client 
                Available Flags :
                0   =>  normal 
                1   =>  admin 
    */

    public static ServerMemory serverMemory = new ServerMemory();

    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
    
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

    }

    @Override
    public void run(){

        String received;
        
        while (true) {
            
            try {
                
                dataOutputStream.writeUTF(
                
                    "Type in the script...\n"+
                    "Type Exit to terminate connection." + 
                    "Script : "
                
                );

                received = dataInputStream.readUTF();
                String[] strings = new ServerUtil().stringTokens(received);

                if(received.equals("Exit")){

                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;

                }

                String command = strings[0].toUpperCase();
                switch (command) {
                  
                    case "GET" :
                        System.out.println(
                            serverMemory.GET(strings[1])
                        );
                        dataOutputStream.writeUTF(serverMemory.GET(strings[1]));
                        break;
                          
                    case "PUT" :
   
                        String message = serverMemory.PUT(strings[1], strings[2]);
                        
                        System.out.println(message);
                        dataOutputStream.writeUTF(message);
                        break;
    
                    case "STATUS":

                        System.out.println(
                            serverMemory.MEMORY_STATUS()
                        ); 
                        dataOutputStream.writeUTF(
                            serverMemory.MEMORY_STATUS()
                        );
                        
                        break;
                    case "DELETE" :
                        if(statusFlag != 1){
                            dataOutputStream.writeUTF("Access Denied");
                        }else{
                            //Do something
                        }
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
