package TestBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        
        try{

            Scanner scanner = new Scanner(System.in);

            InetAddress IP = InetAddress.getByName("localhost");
            Socket socket = new Socket( IP, 5555);

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while( true ){
                
                System.out.println(dataInputStream.readUTF());
                String tosend = scanner.nextLine();
                dataOutputStream.writeUTF(tosend);

                if(tosend.equals("Exit")){
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                String received = dataInputStream.readUTF();
                System.out.println(received);
            }

            scanner.close();
            dataInputStream.close();
            dataOutputStream.close();

        }catch (Exception e){

            e.printStackTrace();

        }

    }
}
