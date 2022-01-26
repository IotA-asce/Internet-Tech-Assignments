import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException{

        ServerSocket serverSocket = new ServerSocket(5555);
        System.out.println("Server established");
        while (true) {
            Socket socket = null;

            try {

                socket = serverSocket.accept();
                System.out.println("A new Client is connected : " + socket);

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread to client");
                
                Thread thread = new ClientHandler(socket, dataInputStream, dataOutputStream);
                thread.start();

            } catch (Exception error) {

                socket.close();
                error.printStackTrace();

            }
        }
    }
}
