package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    public static void main(String[] args) throws IOException {
        
        try (ServerSocket serverSocket = new ServerSocket( 5555 )){

            System.out.println("Established server for connectivity...");

            while( true ){
                
                Socket socket = null;

                try {
                    
                    socket = serverSocket.accept();
                    System.out.println("New client connected..." + socket);

                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    System.out.println("Assigning new thread to client...");
                    Thread thread = new ServerClientHandler(socket, dataInputStream, dataOutputStream);
                    thread.start();

                } catch (Exception e) {

                    socket.close();
                    e.printStackTrace();

                }
            }
        }
    }
}