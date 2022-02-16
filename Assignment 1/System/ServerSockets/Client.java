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

            // ask if client is admin
            System.out.print("Is user admin ? [Y]es/ [N]o : ");
            String isAdmin = scanner.nextLine();
            String username = "";
            String password = "";

            if(isAdmin.equalsIgnoreCase("Y") || isAdmin.equalsIgnoreCase("yes")){
                
                System.out.print("username : ");
                username = scanner.nextLine();
                String uname = "username " + username;
                dataOutputStream.writeUTF(uname);
                String unameStatus = dataInputStream.readUTF();
                System.out.println(unameStatus);

                if(!unameStatus.equalsIgnoreCase("Incorrect Username")){
                    System.out.print("password : ");
                    password = scanner.nextLine();
                    String pword = "password " + password;
                    dataOutputStream.writeUTF(pword);
                    String pwordStatus = dataInputStream.readUTF();
                    System.out.println(pwordStatus);
                }

                // send usr name and password to server



            }

            while( true ){
                
                System.out.println(dataInputStream.readUTF());
                String tosend = scanner.nextLine();
                dataOutputStream.writeUTF(tosend);

                if(tosend.equals("exit")){
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
