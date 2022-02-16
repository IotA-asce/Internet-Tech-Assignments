import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ClientHandler extends Thread{

    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");

    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;
    final String USERNAME = "admin";
    final String PASSWORD = "admin";

    private boolean isUsernameCorrect = false;

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
        try{
            dataOutputStream.writeUTF(
                
                    "Type in the script...\n"+
                    "Type exit to terminate connection."
                );
        }catch (Exception e) {
            e.printStackTrace();
        }
        while (true) {
            
            try {
                
                dataOutputStream.writeUTF(
                    "Script : "
                );

                received = dataInputStream.readUTF();
                ArrayList<String> strings = new ServerUtil().stringTokens(received);

                if(received.equals("exit")){

                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;

                }

                String command = strings.get(0).toUpperCase();
                boolean length_error = false;
                switch (command) {
                  
                    case "GET" :
                        if(strings.size() != 2){
                            dataOutputStream.writeUTF("Error : get accepts 1 argument, => "+strings.size()+" given");
                            length_error = true;
                        }
                        if(length_error) break;
                        System.out.println(
                            serverMemory.GET(strings.get(1))
                        );
                        dataOutputStream.writeUTF(serverMemory.GET(strings.get(1)));
                        break;
                          
                    case "PUT" :
                        if(strings.size() != 3){
                            dataOutputStream.writeUTF("Error : put accepts 2 arguments, => "+strings.size()+" given");
                            length_error = true;
                        }
                        if(length_error) break;
                        String message = serverMemory.PUT(strings.get(1), strings.get(2));
                        
                        System.out.println(message);
                        dataOutputStream.writeUTF(message);
                        break;
    
                    case "STATUS":
                        if(strings.size() != 1){
                            dataOutputStream.writeUTF("Error : status accepts no arguments, => "+strings.size()+" given");
                            length_error = true;
                        }
                        if(length_error) break;
                        System.out.println(
                            serverMemory.MEMORY_STATUS(statusFlag)
                        ); 
                        dataOutputStream.writeUTF(
                            serverMemory.MEMORY_STATUS(statusFlag)
                        );


                        // if(statusFlag == 1){
                        //     // -----admin previledges-----

                        // }
                        
                        break;
                    case "DELETE" :
                        if(strings.size() != 2){
                            dataOutputStream.writeUTF("Error : delete accepts 1 argument, => "+strings.size()+" given");
                            length_error = true;
                        }
                        if(length_error) break;
                        if(statusFlag != 1){
                            dataOutputStream.writeUTF("Access Denied");
                        }else{
                            serverMemory.DELETE(strings.get(1));
                        }
                        break;
                    case "USERNAME":
                        if(strings.get(1).equals(USERNAME)){
                            isUsernameCorrect = true;
                        }
                        else{
                            dataOutputStream.writeUTF("Incorrect Username");
                        }

                        break;
                    case "PASSWORD":
                        if(length_error) break;
                        if(strings.get(1).equals(PASSWORD) && isUsernameCorrect){
                            statusFlag = 1;
                            dataOutputStream.writeUTF("Admin access granted...");
                        }
                        else{
                            dataOutputStream.writeUTF("Incorrect Password");
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
