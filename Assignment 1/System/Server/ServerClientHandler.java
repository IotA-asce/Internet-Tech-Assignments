package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerClientHandler extends Thread{
    
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final Socket socket;
    private static ServerMemory serverMemory = new ServerMemory();
    
    public ServerClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream){

        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

    }

    @Override
    public void run(){

        String received;
        // String operation;       // GET or PUT
        // String toreturn;
        
        while( true ){
            
            try {
                
                dataOutputStream.writeUTF( "Script : " );
                received = dataInputStream.readUTF();

                String[] strings = new ServerUtils().tokenizer(received);
                
                if( strings[0].equalsIgnoreCase("Exit")){

                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection...");
                    this.socket.close();
                    System.out.println("Connection closed...");
                    break;

                }

                if( strings[0].equalsIgnoreCase("GET")){

                    serverMemory.GET(strings[1]);

                }else if(strings[0].equalsIgnoreCase("PUT")){
                    
    
                    serverMemory.PUT(strings[1], strings[2]);

                }else if(strings[0].equalsIgnoreCase("STAT")){
                    
                    serverMemory.MEMORY_STATUS();

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        try {
            
            this.dataInputStream.close();
            this.dataOutputStream.close();

        } catch (Exception e) {
            
            e.printStackTrace();

        }

    }
}
